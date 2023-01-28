package com.wepat.repository.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.dto.CommentDto;
import com.wepat.dto.PhotoDto;
import com.wepat.entity.PhotoEntity;
import com.wepat.exception.photo.AlreadyDeleteImage;
import com.wepat.exception.photo.NotExistImage;
import com.wepat.exception.photo.UpdateSNSCancel;
import com.wepat.repository.MemberRepository;
import com.wepat.repository.PhotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class PhotoRepositoryImpl implements PhotoRepository {

    public enum ReturnType {
        SUCCESS, NotExistImage, AlreadyDeleteImage, UpdateSNSCancel
    }
    private final static Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private final static String PHOTO_COLLECTION = "photo";
    private final Firestore db = FirestoreClient.getFirestore();
    private final CollectionReference photoCollection = db.collection(PHOTO_COLLECTION);

    // 가족 앨범 전체 조회
    @Override
    public List<PhotoDto> getAllPhoto(String calendarId) throws ExecutionException, InterruptedException {
        List<PhotoDto> photoDtoList = new ArrayList<>();
        List<QueryDocumentSnapshot> photoDocumentsList = photoCollection.get().get().getDocuments();
        for (QueryDocumentSnapshot photoList : photoDocumentsList) {
            if ((photoList.toObject(PhotoEntity.class).getCalendarId()).equals(calendarId)) {
                photoDtoList.add(photoCollection.document(photoList.getId()).get().get().toObject(PhotoDto.class));
            }
        }
        return photoDtoList;
    }
    
    // 특정 이미지
    @Override
    public PhotoDto getPhotoById(String calendarId, String photoId) throws ExecutionException, InterruptedException {
        DocumentSnapshot photoSnaphot = photoCollection.document(photoId).get().get();
        if (photoSnaphot.exists()) {
            return photoSnaphot.toObject(PhotoDto.class);
        } else {
            throw new NotExistImage();
        }
    }

    // 이미지 추가
    @Override
    public void addPhoto(String calendarId, PhotoDto photoDto) {
        DocumentReference photoDocRef = photoCollection.document();
        PhotoEntity photoEntity = new PhotoEntity(photoDto);
        photoEntity.setCalendarId(calendarId);
        photoEntity.setPhotoId(photoDocRef.getId());
        photoEntity.setReportIdList(new ArrayList<>());
        photoCollection.document(photoDocRef.getId()).set(photoEntity);
    }

    // 이미지 삭제
    @Override
    public void deletePhoto(String calendarId, String photoId) throws ExecutionException, InterruptedException {
        DocumentReference photoDocRef = photoCollection.document(photoId);
        ApiFuture<?> responseEntityApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                transaction.delete(photoDocRef);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.AlreadyDeleteImage;
            }
        });
        if (responseEntityApiFuture.get() == ReturnType.SUCCESS) {
            responseEntityApiFuture.get();
        } else {
            throw new AlreadyDeleteImage();
        }
    }

    // SNS 업로드
    @Override
    public void updateSNSByPhoto(String calendarId, String photoId) throws ExecutionException, InterruptedException {
        DocumentReference photoDocRef = photoCollection.document(photoId);
        ApiFuture<?> responseEntityApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                boolean sns = photoCollection.document(photoId).get().get().toObject(PhotoEntity.class).isSns();
                transaction.update(photoDocRef, "sns", !sns);
                if (sns) { // 취소
                    return ReturnType.UpdateSNSCancel;
                } else { // 성공
                    return ReturnType.SUCCESS;
                }
            } else {
                return ReturnType.NotExistImage;
            }
        });
        if (responseEntityApiFuture.get()==ReturnType.NotExistImage) {
            throw new NotExistImage();
        } else if (responseEntityApiFuture.get()==ReturnType.UpdateSNSCancel) {
            throw new UpdateSNSCancel();
        } else {
            responseEntityApiFuture.get();
        }
    }

    // 댓글 추가
    @Override
    public void addCommentByPhoto(String calendarId, String photoId, CommentDto commentDto) throws ExecutionException, InterruptedException {
        DocumentReference photoDocRef = photoCollection.document(photoId);
        DocumentReference random = photoCollection.document();

        ApiFuture<?> responseEntityApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                List<CommentDto> commentList = photoDocRef.get().get().toObject(PhotoEntity.class).getCommentList();
                commentDto.setCommentId(random.getId());
                commentList.add(commentDto);
                transaction.update(photoDocRef, "commentList", commentList);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistImage;
            }
        });
        if (responseEntityApiFuture.get()==ReturnType.SUCCESS) {
            responseEntityApiFuture.get();
        } else {
            throw new NotExistImage();
        }
    }
    // 댓글 삭제
    @Override
    public void deleteCommentByPhoto(String calendarId, String photoId, String commentId) throws ExecutionException, InterruptedException {
        DocumentReference photoDocRef = photoCollection.document(photoId);

        ApiFuture<ReturnType> returnTypeApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                List<CommentDto> commentList = photoDocRef.get().get().toObject(PhotoEntity.class).getCommentList();
                for (CommentDto commentDto : commentList) {
                    if (commentDto.getCommentId().equals(commentId)) {
                        commentList.remove(commentDto);
                    }
                }
                transaction.update(photoDocRef, "commentList", commentList);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistImage;
            }
        });
        if (returnTypeApiFuture.get()==ReturnType.SUCCESS) {
            returnTypeApiFuture.get();
        } else {
            throw new NotExistImage();
        }
    }

    // 댓글 수정
    @Override
    public void updateCommentByPhoto(String calendarId, String photoId, String commentId, CommentDto commentDto) throws ExecutionException, InterruptedException {
        DocumentReference photoDocRef = photoCollection.document(photoId);
        ApiFuture<?> responseEntityApiFuture = db.runTransaction(transaction -> {
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
        if (responseEntityApiFuture.get()==ReturnType.SUCCESS) {
            responseEntityApiFuture.get();
        } else {
            throw new NotExistImage();
        }
    }

}
