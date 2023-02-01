package com.wepat.schedule.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.exception.DataNotExitsException;
import com.wepat.schedule.ScheduleDto;
import com.wepat.schedule.ScheduleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleRepositoryImpl.class);
    private static final String SCHEDULE_COLLECTION = "schedule";

//    private static Firestore firestore1 = FirestoreClient.getFirestore();
//    private final Firestore firestore2 = FirestoreClient.getFirestore();
//    private static final Firestore firestore3 = FirestoreClient.getFirestore();

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
    public void addSchedule(ScheduleDto scheduleDto) {
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        DocumentReference docRef = scheduleCollection.document();
        scheduleDto.setScheduleId(docRef.getId());
        docRef.set(scheduleDto);
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
    public ScheduleDto getScheduleDetailByDate(String calendarId, String scheduleId) throws ExecutionException, InterruptedException {
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        return scheduleCollection.whereEqualTo("calendarId", calendarId)
                .whereEqualTo("calendarId", calendarId)
                .get().get().toObjects(ScheduleDto.class).get(0);
    }
}
