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

    private final static Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private final static String PHOTO_COLLECTION = "photo";
    private final Firestore db = FirestoreClient.getFirestore();
    private final CollectionReference photoCollection = db.collection(PHOTO_COLLECTION);

    @Override
    public List<PhotoDto> getSNS() throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> photoDocSnapshotList = photoCollection.get().get().getDocuments();
        List<PhotoDto> photoList = new ArrayList<>();
        for (QueryDocumentSnapshot snapshot : photoDocSnapshotList) {
            if (snapshot.toObject(PhotoEntity.class).isSns()) {
                photoList.add(snapshot.toObject(PhotoDto.class));
            }
        }
        Collections.sort(photoList, new Comparator<PhotoDto>() {
            @Override
            public int compare(PhotoDto o1, PhotoDto o2) {
                if(o2.getLike() - o1.getLike() == 0) {
                    int a = Integer.parseInt(o2.getDate());
                    int b = Integer.parseInt(o1.getDate());
                    return a-b;
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
        ApiFuture<ResponseEntity<?>> responseEntityApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot photoSnapshot = transaction.get(photoDocRef).get();
            if (photoSnapshot.exists()) {
                List<String> reportIdList = photoSnapshot.toObject(PhotoEntity.class).getReportIdList();
                if (reportIdList.contains(memberId)) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                } else {
                    reportIdList.add(memberId);
                    transaction.update(photoDocRef, "reportIdList", reportIdList);
                    return new ResponseEntity<>("신고 성공", HttpStatus.OK);
                }
            } else {
                return null;
            }
        });
        if (responseEntityApiFuture.get() == new ResponseEntity<>(HttpStatus.BAD_REQUEST)) {
            throw new AlreadyReportImage();
        } else if (responseEntityApiFuture.get()==null) {
            throw new NotExistImage();
        } else {
            return responseEntityApiFuture.get();
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
}
