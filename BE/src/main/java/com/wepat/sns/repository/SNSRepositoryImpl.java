package com.wepat.sns.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.exception.sns.AlreadyReportImageException;
import com.wepat.exception.sns.NotExistImageException;
import com.wepat.member.repository.MemberRepository;
import com.wepat.photo.PhotoDto;
import com.wepat.photo.PhotoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class SNSRepositoryImpl implements SNSRepository {

    public enum ReturnType {
        SUCCESS, AlreadyReportImageException, NotExistImageException
    }

    private final static Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private final static String PHOTO_COLLECTION = "photo";
    private final int WARN_LIMIT = 3;

    @Override
    public List<PhotoDto> getSNS(String date) throws ExecutionException, InterruptedException {
        CollectionReference photoCollection = FirestoreClient.getFirestore().collection(PHOTO_COLLECTION);
        List<PhotoDto> photoDtoList = photoCollection
                .whereEqualTo("block", false)
                .whereEqualTo("sns", true)
                .orderBy("date", Query.Direction.DESCENDING)
                .whereGreaterThan("date", date).get().get().toObjects(PhotoDto.class);
        // front 에서 LIKE 순서로 SORT 해서 보여주기
        return photoDtoList;

    }

    @Override
    public PhotoDto getSNSByPhotoId(String photoId) throws ExecutionException, InterruptedException {
        CollectionReference photoCollection = FirestoreClient.getFirestore().collection(PHOTO_COLLECTION);
        return photoCollection.document(photoId).get().get().toObject(PhotoDto.class);
    }

    @Override
    public void updateSNSLikeByPhotoId(String photoId) throws ExecutionException, InterruptedException {
        CollectionReference photoCollection = FirestoreClient.getFirestore().collection(PHOTO_COLLECTION);
        DocumentReference photoDocRef = photoCollection.document(photoId);
        ApiFuture<ReturnType> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                int like = photoSnapshot.toObject(PhotoEntity.class).getLike();
                transaction.update(photoDocRef, "like", like + 1);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistImageException;
            }
        });
        if (future.get() == ReturnType.NotExistImageException) {
            throw new NotExistImageException();
        }
    }

    @Override
    public void reportSNSByPhotoId(String photoId, String memberId) throws ExecutionException, InterruptedException {
        CollectionReference photoCollection = FirestoreClient.getFirestore().collection(PHOTO_COLLECTION);
        DocumentReference photoDocRef = photoCollection.document(photoId);

        ApiFuture<ReturnType> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                List<String> reportIdList = photoSnapshot.toObject(PhotoEntity.class).getReportIdList();
                if (reportIdList.contains(memberId)) {
                    return ReturnType.AlreadyReportImageException;
                } else {
                    reportIdList.add(memberId);
                    transaction.update(photoDocRef, "reportIdList", reportIdList);
                    return ReturnType.SUCCESS;
                }
            } else {
                return ReturnType.NotExistImageException;
            }
        });
        if (future.get() == ReturnType.AlreadyReportImageException) {
            throw new AlreadyReportImageException();
        } else if (future.get() == ReturnType.NotExistImageException) {
            throw new NotExistImageException();
        }
    }

    @Override
    public List<PhotoDto> getReportedList() throws ExecutionException, InterruptedException {
        CollectionReference photoCollection = FirestoreClient.getFirestore().collection(PHOTO_COLLECTION);
        List<QueryDocumentSnapshot> documentsList = photoCollection.get().get().getDocuments();
        List<PhotoDto> photoDtoList = new ArrayList<>();
        for (QueryDocumentSnapshot snapshot : documentsList) {
            if (snapshot.toObject(PhotoEntity.class).getReportIdList().size() >= WARN_LIMIT) {
                photoDtoList.add(snapshot.toObject(PhotoDto.class));
            }
        }
        return photoDtoList;
    }

    @Override
    public void blockSNSByPhotoId(String photoId) throws ExecutionException, InterruptedException {
        CollectionReference photoCollection = FirestoreClient.getFirestore().collection(PHOTO_COLLECTION);
        DocumentReference photoDocRef = photoCollection.document(photoId);
        ApiFuture<ReturnType> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                transaction.update(photoDocRef, "block", true);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistImageException;
            }
        });
        if (future.get() == ReturnType.NotExistImageException) {
            throw new NotExistImageException();
        }
    }
}
