package com.wepat.repository.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.dto.PhotoDto;
import com.wepat.entity.PhotoEntity;
import com.wepat.exception.sns.AlreadyReportImage;
import com.wepat.exception.sns.NotExistImage;
import com.wepat.repository.MemberRepository;
import com.wepat.repository.SNSRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class SNSRepositoryImpl implements SNSRepository {

    public enum ErrorType {
        AlreadyReportImage, NotExistImage
    }

    private final static Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private final static String PHOTO_COLLECTION = "photo";
    private final Firestore db = FirestoreClient.getFirestore();
    private final CollectionReference photoCollection = db.collection(PHOTO_COLLECTION);

    @Override
    public List<PhotoDto> getSNS() throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> photoDocSnapshotList = photoCollection.get().get().getDocuments();
        List<PhotoDto> photoList = new ArrayList<>();
        for (QueryDocumentSnapshot snapshot : photoDocSnapshotList) {
            if (snapshot.toObject(PhotoEntity.class).isSns() && !snapshot.toObject(PhotoEntity.class).isBlock()) {
                photoList.add(snapshot.toObject(PhotoDto.class));
            }
        }
        Collections.sort(photoList, new Comparator<PhotoDto>() {
            @Override
            public int compare(PhotoDto o1, PhotoDto o2) {
                if(o2.getLike() - o1.getLike() == 0) {
                    if (Integer.parseInt(o2.getDate().substring(0, 4)) == Integer.parseInt(o1.getDate().substring(0, 4))) {
                        if (Integer.parseInt(o2.getDate().substring(4, 6)) == Integer.parseInt(o1.getDate().substring(4, 6))) {
                            return Integer.parseInt(o2.getDate().substring(6, 8)) - Integer.parseInt(o1.getDate().substring(6, 8));
                        }
                        return Integer.parseInt(o2.getDate().substring(4, 6)) - Integer.parseInt(o1.getDate().substring(4, 6));
                    }
                    return Integer.parseInt(o2.getDate().substring(0, 4)) - Integer.parseInt(o1.getDate().substring(0, 4));
                }
                return o2.getLike()-o1.getLike();
            }
        });
        return photoList;
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
    public ResponseEntity<?> updateSNSLike(String photoId) throws ExecutionException, InterruptedException {
        DocumentReference photoDocRef = photoCollection.document(photoId);
        ApiFuture<ResponseEntity<String>> responseEntityApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                int like = photoSnapshot.toObject(PhotoEntity.class).getLike();
                transaction.update(photoDocRef, "like", like + 1);
                return new ResponseEntity<>("좋아요 클릭", HttpStatus.OK);
            } else {
                return null;
            }
        });
        if (responseEntityApiFuture.get()!=null) {
            return responseEntityApiFuture.get();
        } else {
            throw new NotExistImage();
        }
    }

    @Override
    public ResponseEntity<?> reportSNS(String photoId, String memberId) throws ExecutionException, InterruptedException {
        DocumentReference photoDocRef = photoCollection.document(photoId);
        System.out.println("ghcnf!");
        ApiFuture<?> responseEntityApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                List<String> reportIdList = photoSnapshot.toObject(PhotoEntity.class).getReportIdList();
                if (reportIdList.contains(memberId)) {
                    return ErrorType.AlreadyReportImage;
                } else {
                    reportIdList.add(memberId);
                    transaction.update(photoDocRef, "reportIdList", reportIdList);
                    return new ResponseEntity<>("신고 성공", HttpStatus.OK);
                }
            } else {
                return ErrorType.NotExistImage;
            }
        });
        System.out.println(responseEntityApiFuture.get());
        if ((responseEntityApiFuture.get()) == ErrorType.AlreadyReportImage) {
            throw new AlreadyReportImage();
        } else if (responseEntityApiFuture.get() == ErrorType.NotExistImage) {
            throw new NotExistImage();
        } else {
            return (ResponseEntity<?>) responseEntityApiFuture.get();
        }
    }

    @Override
    public List<PhotoDto> reportList() throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> documentsList = photoCollection.get().get().getDocuments();
        List<PhotoDto> photoDtoList = new ArrayList<>();
        for (QueryDocumentSnapshot snapshot : documentsList) {
            if (snapshot.toObject(PhotoEntity.class).getReportIdList().size() >= 3) {
                photoDtoList.add(snapshot.toObject(PhotoDto.class));
            }
        }
        return photoDtoList;
    }

    @Override
    public ResponseEntity<?> blockSNSByPhoto(String photoId) throws ExecutionException, InterruptedException {
        DocumentReference photoDocRef = photoCollection.document(photoId);
        ApiFuture<ResponseEntity<String>> responseEntityApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                transaction.update(photoDocRef, "block", true);
                return new ResponseEntity<>("차단 성공", HttpStatus.OK);
            } else {
                return null;
            }
        });
        if (responseEntityApiFuture.get()!=null) {
            return responseEntityApiFuture.get();
        } else {
            throw new NotExistImage();
        }
    }
}
