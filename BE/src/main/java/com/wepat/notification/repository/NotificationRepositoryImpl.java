package com.wepat.notification.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.notification.NotificationDto;
import com.wepat.sse.SseEmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
    private static final String NOTIFICATION_COLLECTION = "notification";
    private final SseEmitterRepository sseEmitterRepository;

    @Override
    public String addNotification(NotificationDto notificationDto) {
        CollectionReference notificationCollection = FirestoreClient.getFirestore().collection(NOTIFICATION_COLLECTION);
        DocumentReference notificationDocRef = notificationCollection.document();
        notificationDto.setNotificationId(notificationDocRef.getId());
        notificationDocRef.set(notificationDto);


        sseEmitterRepository.send(notificationDto);
        return notificationDto.getNotificationId();
    }

    @Override
    public void readNotification(String notificationId) {
        CollectionReference notificationCollection = FirestoreClient.getFirestore().collection(NOTIFICATION_COLLECTION);
        final DocumentReference notificationDocRef = notificationCollection.document(notificationId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            NotificationDto notificationDto = transaction.get(notificationDocRef).get().toObject(NotificationDto.class);
            if (notificationDto != null) {
                transaction.update(notificationDocRef, "read", false);
            }
           return null;
        });
    }

    @Override
    public void deleteNotification(String notificationId) {
        CollectionReference notificationCollection = FirestoreClient.getFirestore().collection(NOTIFICATION_COLLECTION);
        final DocumentReference notificationDocRef = notificationCollection.document(notificationId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            NotificationDto notificationDto = transaction.get(notificationDocRef).get().toObject(NotificationDto.class);
            if (notificationDto != null) {
                transaction.delete(notificationDocRef);
            }
            return null;
        });
    }

    @Override
    public void deleteAll(String memberId) {
        CollectionReference notificationCollection = FirestoreClient.getFirestore().collection(NOTIFICATION_COLLECTION);

        final Query query = notificationCollection.whereEqualTo("memberId", memberId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            List<QueryDocumentSnapshot> documentSnapshots = transaction.get(query).get().getDocuments();
            for (QueryDocumentSnapshot documentSnapshot : documentSnapshots) {
                transaction.delete(documentSnapshot.getReference());
            }
            return null;
        });
    }

    @Override
    public void readlAll(String memberId) {
        CollectionReference notificationCollection = FirestoreClient.getFirestore().collection(NOTIFICATION_COLLECTION);

        final Query query = notificationCollection.whereEqualTo("memberId", memberId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            List<QueryDocumentSnapshot> documentSnapshots = transaction.get(query).get().getDocuments();
            for (QueryDocumentSnapshot documentSnapshot : documentSnapshots) {
                transaction.update(documentSnapshot.getReference(), "read", true);
            }
            return null;
        });
    }

    @Override
    public String getScheduleIdByNotificationId(String notificationId) throws ExecutionException, InterruptedException {
        CollectionReference notificationCollection = FirestoreClient.getFirestore().collection(NOTIFICATION_COLLECTION);
        DocumentReference notificationDocRef = notificationCollection.document(notificationId);

        String scheduleId = notificationDocRef.get().get().toObject(NotificationDto.class).getScheduleId();
        return scheduleId;
    }
}
