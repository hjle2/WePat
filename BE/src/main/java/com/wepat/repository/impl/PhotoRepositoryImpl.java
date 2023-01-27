package com.wepat.repository.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.dto.CommentDto;
import com.wepat.dto.PhotoDto;
import com.wepat.entity.PhotoEntity;
import com.wepat.exception.photo.NotExistImage;
import com.wepat.repository.MemberRepository;
import com.wepat.repository.PhotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class PhotoRepositoryImpl implements PhotoRepository {
    private final static Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private final static String CALENDAR_COLLECTION = "calendar";
    private final static String MEMBER_COLLECTION = "member";
    private final static String PHOTO_COLLECTION = "photo";
    private final Firestore db = FirestoreClient.getFirestore();
    private final CollectionReference calCollection = db.collection(CALENDAR_COLLECTION);
    private final CollectionReference memCollection = db.collection(MEMBER_COLLECTION);
    private final CollectionReference photoCollection = db.collection(PHOTO_COLLECTION);

    @Override
    public List<PhotoDto> getAllPhoto(String calendarId) throws ExecutionException, InterruptedException {
        List<PhotoDto> photoDtoList = new ArrayList<>();
        List<QueryDocumentSnapshot> photoDocumentsList = photoCollection.get().get().getDocuments();

        ApiFuture<List<PhotoDto>> listApiFuture = db.runTransaction(transaction -> {
            for (QueryDocumentSnapshot photoList : photoDocumentsList) {
                System.out.println("photoList " + photoList.getId());

//                if (photoList.exists() && (photoList.toObject(PhotoEntity.class).getCalendarId()).equals(calendarId)) {
//                    System.out.println("dh! " + photoList.getId());
                    photoDtoList.add(photoCollection.document(photoList.getId()).get().get().toObject(PhotoDto.class));
//                }
            }
            return photoDtoList;
        });
        return listApiFuture.get();
    }

    @Override
    public PhotoEntity getPhotoById(String calendarId, String photoId) throws ExecutionException, InterruptedException {
        // date로 받으면 같은시간 같은초라면?
        DocumentReference photoDocRef = photoCollection.document(photoId);
        ApiFuture<PhotoEntity> photoEntityApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                return photoDocRef.get().get().toObject(PhotoEntity.class);
            } else {
                return null;
            }
        });
        if (photoEntityApiFuture.get()==null) {
            throw new NotExistImage("존재하지 않는 사진입니다.");
        } else {
            return photoEntityApiFuture.get();
        }
    }

    @Override
    public PhotoEntity addCommentByPhoto(String calendarId, String photoId, CommentDto commentDto) throws ExecutionException, InterruptedException {
        DocumentReference photoDocRef = photoCollection.document(photoId);

        ApiFuture<String> stringApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                List<CommentDto> commentList = photoDocRef.get().get().toObject(PhotoEntity.class).getCommentList();
                commentList.add(commentDto);
                transaction.update(photoDocRef, "commentList", commentList);
                System.out.println("호출~~");
                return "success";
            } else {
                System.out.println(">>>");
                return "NotExistImage";
            }
        });
        if ((stringApiFuture.get()).equals("success")) {
            return photoDocRef.get().get().toObject(PhotoEntity.class);
        } else if ((stringApiFuture.get()).equals("NotExistImage")){
            System.out.println("aaaaaa");
            throw new NotExistImage("존재하지 않는 사진입니다.");
        } else {
            System.out.println("bbb");
            throw new NotExistImage("pp");
        }
    }

    @Override
    public ResponseEntity<?> updateSNSByPhoto(String calendarId, String photoId) throws ExecutionException, InterruptedException {
        DocumentReference photoDocRef = photoCollection.document(photoId);
        ApiFuture<ResponseEntity<String>> responseEntityApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                boolean sns = photoCollection.document(photoId).get().get().toObject(PhotoEntity.class).isSns();
                transaction.update(photoDocRef, "sns", !sns);
                if (sns) {
                    return new ResponseEntity<>("업로드 취소!", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("업로드 성공!", HttpStatus.OK);
                }
            } else {
                return null;
            }
        });
        if (responseEntityApiFuture.get()==null) {
            throw new NotExistImage("이미지없음!>>>>");
        } else {
            return responseEntityApiFuture.get();
        }
    }

    @Override
    public ResponseEntity<?> deletePhoto(String calendarId, String photoId) throws ExecutionException, InterruptedException {
        DocumentReference photoDocRef = photoCollection.document(photoId);
        ApiFuture<ResponseEntity<String>> responseEntityApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                transaction.delete(photoDocRef);
                return new ResponseEntity<>("이미지 삭제 성공!", HttpStatus.OK);
            } else {
                throw new NotExistImage("이미지 없음!");
            }
        });
        return responseEntityApiFuture.get();
    }

    @Override
    public PhotoDto addLike(String photoId) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public PhotoDto addReport(String photoId) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public ResponseEntity<?> addPhoto(String PhotoURL) {
        System.out.println(PhotoURL);
        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setPhotoId(PhotoURL);
        System.out.println(photoEntity);
        photoCollection.add(photoEntity);
        return new ResponseEntity<>("등록 성공!", HttpStatus.OK);
    }
}
