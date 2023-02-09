package com.wepat.schedule.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.exception.DataNotExitsException;
import com.wepat.exception.schedule.NotExistScheduleException;
import com.wepat.member.MemberDto;
import com.wepat.notification.NotifiacationType;
import com.wepat.notification.NotificationDto;
import com.wepat.photo.CommentDto;
import com.wepat.schedule.CalendarEntity;
import com.wepat.schedule.ScheduleDto;
import com.wepat.schedule.ScheduleEntity;
import org.checkerframework.checker.units.qual.N;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {
    private static final String SCHEDULE_COLLECTION = "schedule";
    private static final String NOTIFICATION_COLLECTION = "notification";
    private static final String CALENDAR_COLLECTION = "calendar";
    private static final String MEMBER_COLLECTION = "member";

//    private static Firestore firestore1 = FirestoreClient.getFirestore();
//    private final Firestore firestore2 = FirestoreClient.getFirestore();
//    private static final Firestore firestore3 = FirestoreClient.getFirestore();
    public enum ReturnType {
        SUCCESS, NotExistScheduleException
    }
    @Override
    public List<ScheduleDto> getScheduleByCalendarId(String calendarId) {
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        try {
            List<ScheduleDto> scheduleDtoList = scheduleCollection
                    .whereEqualTo("calendarId", calendarId)
                    .orderBy("startDate")
                    .get().get().toObjects(ScheduleDto.class);

            return scheduleDtoList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ScheduleDto> getScheduleListByDate(String calendarId, String startDate, String endDate) throws ExecutionException, InterruptedException {
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        List<ScheduleDto> scheduleDtoList = scheduleCollection
                .whereEqualTo("calendarId", calendarId)
                .whereGreaterThanOrEqualTo("startDate", startDate)
                .whereLessThan("endDate", endDate)
                .get().get().toObjects(ScheduleDto.class);
        return scheduleDtoList;
    }

    @Override
    public String addSchedule(ScheduleDto scheduleDto) {
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        CollectionReference notificationCollection = FirestoreClient.getFirestore().collection(NOTIFICATION_COLLECTION);
        final DocumentReference scheduleDocRef = scheduleCollection.document();
        final DocumentReference notificationDocRef = notificationCollection.document();

        scheduleDto.setScheduleId(scheduleDocRef.getId());

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction-> {
            // add schedule
            scheduleDto.setPhotoUrl("");
            scheduleDto.setReviewList(new ArrayList<>());
            transaction.set(scheduleDocRef, scheduleDto);

            // add notification
            return null;
        });
        return scheduleDocRef.getId();
    }

    @Override
    public void modifySchedule(String calendarId, String scheduleId,  ScheduleDto scheduleDto) throws ExecutionException, InterruptedException {

        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        Query query = scheduleCollection.whereEqualTo("calendarId", calendarId)
                .whereEqualTo("scheduleId", scheduleDto.getScheduleId());

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            ApiFuture<QuerySnapshot> scheduleEntityList = transaction.get(query);
            List<ScheduleEntity> entityList = scheduleEntityList.get().toObjects(ScheduleEntity.class);

            if (entityList != null && entityList.size() > 0) {
                transaction.set(scheduleEntityList.get().getDocuments().get(0).getReference(), scheduleDto);
                return true;
            }
           return null;
        });

        if (future.get() == null) {
            throw new DataNotExitsException();
        }
    }

    @Override
    public void deleteSchedule(String calendarId, String scheduleId) {
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        Query query = scheduleCollection.whereEqualTo("calendarId", calendarId)
                .whereEqualTo("scheduleId", scheduleId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            List<QueryDocumentSnapshot> scheduleEntityList = transaction.get(query).get().getDocuments();

                for (QueryDocumentSnapshot docRef : scheduleEntityList) {
                    transaction.delete(docRef.getReference());
                }
                if (scheduleEntityList != null && scheduleEntityList.size() > 0)
                    return true;
                return null;
        });
        if (future == null) {
            throw new DataNotExitsException();
        }
    }

    @Override
    public void completeSchedule(String calendarId, String scheduleId, String whoCompleted, boolean completed) throws ExecutionException, InterruptedException {
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);

        scheduleCollection.document(scheduleId).update("completed", completed);
        scheduleCollection.document(scheduleId).update("whoCompleted", whoCompleted);
    }

    @Override
    public ScheduleDto getScheduleByScheduleId(String calendarId, String scheduleId) throws ExecutionException, InterruptedException {
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        return scheduleCollection.whereEqualTo("calendarId", calendarId)
                .whereEqualTo("scheduleId", scheduleId)
                .get().get().toObjects(ScheduleDto.class).get(0);
    }

    @Override
    public HashMap<String, String> getMemberListByCalendarId(String calendarId) throws ExecutionException, InterruptedException {
        CollectionReference calendarCollection = FirestoreClient.getFirestore().collection(CALENDAR_COLLECTION);
        CollectionReference memberCollection = FirestoreClient.getFirestore().collection(MEMBER_COLLECTION);

        HashMap<String, String> memberMap = new HashMap<>();
        List<String> memberList = calendarCollection.document(calendarId).get().get().toObject(CalendarEntity.class).getMemberId();
        for (String memberId: memberList) {
            String nickName = memberCollection.document(memberId).get().get().toObject(MemberDto.class).getNickName();
            memberMap.put(memberId, nickName);
        }
        return memberMap;
    }
    @Override
    public void addCommentByScheduleId(String calendarId, String scheduleId, CommentDto commentDto) throws ExecutionException, InterruptedException {
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        DocumentReference scheduleDocRef = scheduleCollection.document(scheduleId);
        DocumentReference randomDocRef = scheduleCollection.document();

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot scheduleSnapshot = transaction.get(scheduleDocRef).get();
            if (scheduleSnapshot.exists()) {
                List<CommentDto> reviewList = scheduleSnapshot.toObject(ScheduleDto.class).getReviewList();
                commentDto.setCommentId(randomDocRef.getId());
                reviewList.add(commentDto);
                transaction.update(scheduleDocRef, "reviewList", reviewList);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistScheduleException;
            }
        });
        if (future.get() == ReturnType.NotExistScheduleException) {
            throw new NotExistScheduleException();
        }
    }

    @Override
    public void modifyCommentByScheduleId(String calendarId, String scheduleId, String commentId, CommentDto commentDto) throws ExecutionException, InterruptedException {
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        DocumentReference scheduleDocRef = scheduleCollection.document(scheduleId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot scheduleSnapshot = transaction.get(scheduleDocRef).get();
            if (scheduleSnapshot.exists()) {
                List<CommentDto> reviewList = scheduleSnapshot.toObject(ScheduleDto.class).getReviewList();
                for (CommentDto review : reviewList) {
                    if (review.getCommentId().equals(commentId)) {
                        review.setDate(commentDto.getDate());
                        review.setContent(commentDto.getContent());
                        break;
                    }
                }
                transaction.update(scheduleDocRef, "reviewList", reviewList);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistScheduleException;
            }
        });
        if (future.get() == ReturnType.NotExistScheduleException) {
            throw new NotExistScheduleException();
        }
    }

    @Override
    public void deleteCommentByScheduleId(String calendarId, String scheduleId, String commentId) throws ExecutionException, InterruptedException {
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        DocumentReference scheduleDocRef = scheduleCollection.document(scheduleId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot scheduleSnapshot = transaction.get(scheduleDocRef).get();
            if (scheduleSnapshot.exists()) {
                List<CommentDto> reviewList = scheduleSnapshot.toObject(ScheduleDto.class).getReviewList();
                for (CommentDto review : reviewList) {
                    if (review.getCommentId().equals(commentId)) {
                        reviewList.remove(review);
                        break;
                    }
                }
                transaction.update(scheduleDocRef, "reviewList", reviewList);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistScheduleException;
            }
        });
        if (future.get() == ReturnType.NotExistScheduleException) {
            throw new NotExistScheduleException();
        }
    }

    @Override
    public void modifyPhotoUrlByScheduleId(String calendarId, String scheduleId, String photoUrl) throws ExecutionException, InterruptedException {
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        DocumentReference scheduleDocRef = scheduleCollection.document(scheduleId);

        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot scheduleSnapshot = transaction.get(scheduleDocRef).get();
            if (scheduleSnapshot.exists()) {
                transaction.update(scheduleDocRef, "photoUrl", photoUrl);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistScheduleException;
            }
        });
        if (future.get() == ReturnType.NotExistScheduleException) {
            throw new NotExistScheduleException();
        }
    }
}
