package com.wepat.sns.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.exception.sns.AlreadyReportImage;
import com.wepat.exception.sns.NotExistImage;
import com.wepat.member.repository.MemberRepository;
import com.wepat.photo.PhotoDto;
import com.wepat.photo.PhotoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class SNSRepositoryImpl implements SNSRepository {

    public enum ReturnType {
        SUCCESS, AlreadyReportImage, NotExistImage
    }

    private final static Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private final static String PHOTO_COLLECTION = "photo";
    private final Firestore db = FirestoreClient.getFirestore();
    private final CollectionReference photoCollection = db.collection(PHOTO_COLLECTION);
    private final int warnCount = 3;

    @Override
    public List<PhotoDto> getSNS(String date) throws ExecutionException, InterruptedException {
//        return photoCollection.whereEqualTo("sns", true)
//                .whereEqualTo("block", false).get().get().toObjects(PhotoDto.class);

        logger.info(date);
        List<PhotoDto> photoDtoList = photoCollection
                .whereEqualTo("block", false)
                .whereEqualTo("sns", true)
                .orderBy("date", Query.Direction.DESCENDING)
                .whereGreaterThan("date", date)
                .orderBy("like", Query.Direction.DESCENDING).get().get().toObjects(PhotoDto.class);

        for (PhotoDto dto : photoDtoList) {
            logger.info(dto.toString());
        }

        return photoDtoList;

//        List<QueryDocumentSnapshot> photoDocSnapshotList = photoCollection.get().get().getDocuments();
//        List<PhotoDto> photoList = new ArrayList<>();
//        for (QueryDocumentSnapshot snapshot : photoDocSnapshotList) {
//            if (snapshot.toObject(PhotoEntity.class).isSns() && !snapshot.toObject(PhotoEntity.class).isBlock()) {
//                photoList.add(snapshot.toObject(PhotoDto.class));
//            }
//        }
//        Collections.sort(photoList, new Comparator<PhotoDto>() {
//            @Override
//            public int compare(PhotoDto o1, PhotoDto o2) {
//                if(o2.getLike() - o1.getLike() == 0) {
//                    if (Integer.parseInt(o2.getDate().substring(0, 4)) == Integer.parseInt(o1.getDate().substring(0, 4))) {
//                        if (Integer.parseInt(o2.getDate().substring(4, 6)) == Integer.parseInt(o1.getDate().substring(4, 6))) {
//                            return Integer.parseInt(o2.getDate().substring(6, 8)) - Integer.parseInt(o1.getDate().substring(6, 8));
//                        }
//                        return Integer.parseInt(o2.getDate().substring(4, 6)) - Integer.parseInt(o1.getDate().substring(4, 6));
//                    }
//                    return Integer.parseInt(o2.getDate().substring(0, 4)) - Integer.parseInt(o1.getDate().substring(0, 4));
//                }
//                return o2.getLike()-o1.getLike();
//            }
//        });
//        return photoList;
    }

    @Override
    public PhotoDto getSNSByPhotoId(String photoId) throws ExecutionException, InterruptedException {
        DocumentSnapshot photoSnapshot = photoCollection.document(photoId).get().get();
        if (photoSnapshot.exists()) {
            return photoSnapshot.toObject(PhotoDto.class);
        } else {
            throw new NotExistImage();
        }
    }

    @Override
    public void updateSNSLike(String photoId) throws ExecutionException, InterruptedException {
        DocumentReference photoDocRef = photoCollection.document(photoId);
        ApiFuture<ReturnType> returnTypeApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                int like = photoSnapshot.toObject(PhotoEntity.class).getLike();
                transaction.update(photoDocRef, "like", like + 1);
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

    @Override
    public void reportSNS(String photoId, String memberId) throws ExecutionException, InterruptedException {
        DocumentReference photoDocRef = photoCollection.document(photoId);

        ApiFuture<ReturnType> returnTypeApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                List<String> reportIdList = photoSnapshot.toObject(PhotoEntity.class).getReportIdList();
                if (reportIdList.contains(memberId)) {
                    return ReturnType.AlreadyReportImage;
                } else {
                    reportIdList.add(memberId);
                    transaction.update(photoDocRef, "reportIdList", reportIdList);
                    return ReturnType.SUCCESS;
                }
            } else {
                return ReturnType.NotExistImage;
            }
        });
        if (returnTypeApiFuture.get() == ReturnType.AlreadyReportImage) {
            throw new AlreadyReportImage();
        } else if (returnTypeApiFuture.get() == ReturnType.NotExistImage) {
            throw new NotExistImage();
        } else {
            returnTypeApiFuture.get();
        }
    }

    @Override
    public List<PhotoDto> reportList() throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> documentsList = photoCollection.get().get().getDocuments();
        List<PhotoDto> photoDtoList = new ArrayList<>();
        for (QueryDocumentSnapshot snapshot : documentsList) {
            if (snapshot.toObject(PhotoEntity.class).getReportIdList().size() >= warnCount) {
                photoDtoList.add(snapshot.toObject(PhotoDto.class));
            }
        }
        return photoDtoList;
    }

    @Override
    public void blockSNSByPhoto(String photoId) throws ExecutionException, InterruptedException {
        DocumentReference photoDocRef = photoCollection.document(photoId);
        ApiFuture<ReturnType> returnTypeApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                transaction.update(photoDocRef, "block", true);
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
}
