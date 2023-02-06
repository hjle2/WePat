package com.wepat.finance.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.schedule.CalendarDto;
import com.wepat.schedule.CalendarEntity;
import com.wepat.exception.finance.AlreadyDeleteFinanceException;
import com.wepat.finance.FinanceDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class FinanceRepositoryImpl implements FinanceRepository {

    public enum ReturnType {
        SUCCESS, AlreadyDeleteFinanceException
    }
    private static final String CALENDAR_COLLECTION = "calendar";

    @Override
    public List<FinanceDto> getAllFinance(String calendarId) throws ExecutionException, InterruptedException {

        CollectionReference calCollection = FirestoreClient.getFirestore().collection(CALENDAR_COLLECTION);

        return calCollection.document(calendarId).get().get().toObject(CalendarEntity.class).getFinanceList();
    }

    @Override
    public void addFinanceById(String calendarId, FinanceDto financeDto) throws ExecutionException, InterruptedException {
        CollectionReference calCollection = FirestoreClient.getFirestore().collection(CALENDAR_COLLECTION);

        DocumentReference calDocRef = calCollection.document(calendarId);
        DocumentReference random = calCollection.document();
        financeDto.setFinanceId(random.getId());
        List<FinanceDto> financeList = calDocRef.get().get().toObject(CalendarDto.class).getFinanceList();
        financeList.add(financeDto);
        calDocRef.update("financeList", financeList);
    }

    @Override
    public FinanceDto getFinanceById(String calendarId, String financeId) throws ExecutionException, InterruptedException {
        CollectionReference calCollection = FirestoreClient.getFirestore().collection(CALENDAR_COLLECTION);

        List<FinanceDto> financeList = calCollection.document(calendarId).get().get().toObject(CalendarEntity.class).getFinanceList();
        for (FinanceDto financeDto : financeList) {
            String dtoFinanceId = financeDto.getFinanceId();
            if (dtoFinanceId != null && dtoFinanceId.equals(financeId)) {
                return financeDto;
            }
        }
        throw new AlreadyDeleteFinanceException();
    }

    @Override
    public void modifyFinanceById(String calendarId, String financeId, FinanceDto financeDto) throws ExecutionException, InterruptedException {
        CollectionReference calCollection = FirestoreClient.getFirestore().collection(CALENDAR_COLLECTION);

        DocumentReference calDocRef = calCollection.document(calendarId);
        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot calSnapshot = transaction.get(calDocRef).get();
            List<FinanceDto> financeList = calSnapshot.toObject(CalendarEntity.class).getFinanceList();
            for (FinanceDto finance : financeList) {
                String dtoFinanceId = financeDto.getFinanceId();
                if (dtoFinanceId != null && dtoFinanceId.equals(financeId)) {
                    finance.setCategory(financeDto.getCategory());
                    finance.setMemo(financeDto.getMemo());
                    finance.setMoney(financeDto.getMoney());
                    finance.setIncome(financeDto.isIncome());
                    transaction.update(calDocRef, "financeList", financeList);
                    return ReturnType.SUCCESS;
                }
            }
            return ReturnType.AlreadyDeleteFinanceException;
        });
        if (future.get() == ReturnType.AlreadyDeleteFinanceException) {
            throw new AlreadyDeleteFinanceException();
        }
    }

    @Override
    public void deleteFinanceById(String calendarId, String financeId) throws ExecutionException, InterruptedException {
        CollectionReference calCollection = FirestoreClient.getFirestore().collection(CALENDAR_COLLECTION);

        DocumentReference calDocRef = calCollection.document(calendarId);
        ApiFuture<?> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot calSnapshot = transaction.get(calDocRef).get();
            List<FinanceDto> financeList = calSnapshot.toObject(CalendarEntity.class).getFinanceList();
            for (FinanceDto financeDto : financeList) {
                String financeIdCompare = financeDto.getFinanceId();
                if (financeIdCompare != null && financeIdCompare.equals(financeId)) {
                    financeList.remove(financeDto);
                    transaction.update(calDocRef, "financeList", financeList);
                    return ReturnType.SUCCESS;
                }
            }
            return ReturnType.AlreadyDeleteFinanceException;
        });
        if (future.get() == ReturnType.AlreadyDeleteFinanceException) {
            throw new AlreadyDeleteFinanceException();
        }
    }
}
