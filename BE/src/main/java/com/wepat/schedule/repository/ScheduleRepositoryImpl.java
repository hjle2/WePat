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
    @Override
    public Map<String, List<String>> getScheduleByMonth(String calendarId, String startDate, String endDate) {
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        try {
            Map<String, List<String>> datePetMap = new HashMap<>();

            List<QueryDocumentSnapshot> documents = scheduleCollection
                    .whereEqualTo("calendarId", calendarId)
                    .whereGreaterThanOrEqualTo("date", startDate)
                    .whereLessThanOrEqualTo("date", startDate)
                    .get().get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                ScheduleEntity entity = doc.toObject(ScheduleEntity.class);
                if (datePetMap.containsKey(entity.getDate())) {
                    datePetMap.get(entity.getDate()).add(entity.getPetId());
                } else {
                    List<String> list = new ArrayList<>();
                    list.add(entity.getPetId());
                    datePetMap.put(entity.getDate(), list);
                }
            }
            return datePetMap;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ScheduleDto> getScheduleListByDate(String calendarId, String date) throws ExecutionException, InterruptedException {
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        List<ScheduleDto> scheduleDtoList = scheduleCollection
                .whereEqualTo("calendarId", calendarId)
                .whereEqualTo("date", date)
                .get().get().toObjects(ScheduleDto.class);
        return scheduleDtoList;
    }

    @Override
    public void addSchedule(ScheduleDto scheduleDto) {
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        DocumentReference docRef = scheduleCollection.document();
        docRef.set(scheduleDto);
    }

    @Override
    public void modifySchedule(String calendarId, ScheduleDto scheduleDto, String date) throws ExecutionException, InterruptedException {
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
