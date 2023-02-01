package com.wepat.photo.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.exception.DataNotExitsException;
import com.wepat.exception.photo.AlreadyDeleteImage;
import com.wepat.exception.photo.NotExistImage;
import com.wepat.member.repository.MemberRepository;
import com.wepat.photo.CommentDto;
import com.wepat.photo.PhotoDto;
import com.wepat.photo.PhotoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class PhotoRepositoryImpl implements PhotoRepository {

    public enum ReturnType {
        SUCCESS, NotExistImage, AlreadyDeleteImage
    }
    private static final Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private static final String PHOTO_COLLECTION = "photo";
    private final String firstAddDate = "0";
    private final int reset = 0;

    // 가족 앨범 전체 조회
    @Override
    public List<PhotoDto> getAllPhotoById(String calendarId) throws ExecutionException, InterruptedException {
        CollectionReference photoCollection = FirestoreClient.getFirestore().collection(PHOTO_COLLECTION);
        return photoCollection.whereEqualTo("calendarId", calendarId).orderBy("date", Query.Direction.DESCENDING).get().get().toObjects(PhotoDto.class);
    }

    // 특정 이미지
    @Override
    public PhotoDto getPhotoById(String photoId) throws ExecutionException, InterruptedException {
        CollectionReference photoCollection = FirestoreClient.getFirestore().collection(PHOTO_COLLECTION);
        DocumentSnapshot photoSnapshot = photoCollection.document(photoId).get().get();
        if (photoSnapshot.exists()) {
            return photoSnapshot.toObject(PhotoDto.class);
        } else {
            throw new NotExistImage();
        }
    }

    // 이미지 추가
    @Override
    public void addPhoto(String calendarId, PhotoDto photoDto) {
        CollectionReference photoCollection = FirestoreClient.getFirestore().collection(PHOTO_COLLECTION);
        DocumentReference photoDocRef = photoCollection.document();
        PhotoEntity photoEntity = new PhotoEntity(photoDto);
        photoEntity.setCalendarId(calendarId);
        photoEntity.setPhotoId(photoDocRef.getId());
        photoEntity.setSnsDate(firstAddDate);
        photoCollection.document(photoDocRef.getId()).set(photoEntity);
    }

    // 이미지 삭제
    @Override
    public void deletePhoto(String photoId) throws ExecutionException, InterruptedException {
        CollectionReference photoCollection = FirestoreClient.getFirestore().collection(PHOTO_COLLECTION);
        DocumentReference photoDocRef = photoCollection.document(photoId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                transaction.delete(photoDocRef);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.AlreadyDeleteImage;
            }
        });
        if (future.get() == ReturnType.AlreadyDeleteImage) {
            throw new AlreadyDeleteImage();
        }
    }

    // SNS 업로드
    @Override
    public void uploadSNSByPhotoId(String photoId, String snsDate) throws ExecutionException, InterruptedException {
        CollectionReference photoCollection = FirestoreClient.getFirestore().collection(PHOTO_COLLECTION);
        DocumentReference photoDocRef = photoCollection.document(photoId);
        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                boolean sns = photoCollection.document(photoId).get().get().toObject(PhotoEntity.class).isSns();
                transaction.update(photoDocRef, "sns", !sns);
                transaction.update(photoDocRef, "like", reset);
                transaction.update(photoDocRef, "snsDate", snsDate);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistImage;
            }
        });
        if (future.get() == ReturnType.NotExistImage) {
            throw new NotExistImage();
        }
    }

    // 댓글 추가
    @Override
    public void addCommentByPhotoId(String photoId, CommentDto commentDto) throws ExecutionException, InterruptedException {
        CollectionReference photoCollection = FirestoreClient.getFirestore().collection(PHOTO_COLLECTION);
        DocumentReference photoDocRef = photoCollection.document(photoId);
        DocumentReference randomDocRef = photoCollection.document();

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                List<CommentDto> commentList = photoDocRef.get().get().toObject(PhotoEntity.class).getCommentList();
                commentDto.setCommentId(randomDocRef.getId());
                commentList.add(commentDto);
                transaction.update(photoDocRef, "commentList", commentList);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistImage;
            }
        });
        if (future.get() == ReturnType.NotExistImage) {
            throw new NotExistImage();
        }
    }
    // 댓글 삭제
    @Override
    public void deleteCommentByPhotoId(String photoId, String commentId) throws ExecutionException, InterruptedException {
        CollectionReference photoCollection = FirestoreClient.getFirestore().collection(PHOTO_COLLECTION);
        DocumentReference photoDocRef = photoCollection.document(photoId);

        ApiFuture<ReturnType> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                List<CommentDto> commentList = photoDocRef.get().get().toObject(PhotoEntity.class).getCommentList();
                for (CommentDto commentDto : commentList) {
                    if (commentDto.getCommentId().equals(commentId)) {
                        commentList.remove(commentDto);
                        break;
                    }
                }
                transaction.update(photoDocRef, "commentList", commentList);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistImage;
            }
        });
        if (future.get() == ReturnType.NotExistImage) {
            throw new NotExistImage();
        }
    }

    // 댓글 수정
    @Override
    public void updateCommentByPhotoId(String photoId, String commentId, CommentDto commentDto) throws ExecutionException, InterruptedException {
        CollectionReference photoCollection = FirestoreClient.getFirestore().collection(PHOTO_COLLECTION);
        DocumentReference photoDocRef = photoCollection.document(photoId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {

                List<CommentDto> commentList = photoDocRef.get().get().toObject(PhotoEntity.class).getCommentList();

                for (CommentDto comment : commentList) {
                    if ((comment.getCommentId()).equals(commentId)) {
                        comment.setDate(commentDto.getDate());
                        comment.setContent(commentDto.getContent());
                        break;
                    }
                }
                transaction.update(photoDocRef, "commentList", commentList);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistImage;
            }
        });
        if (future.get() == ReturnType.NotExistImage) {
            throw new DataNotExitsException();
        }
    }

}
