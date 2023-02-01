package com.wepat.finance.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.calendar.CalendarDto;
import com.wepat.calendar.CalendarEntity;
import com.wepat.exception.finance.AlreadyDeleteFinance;
import com.wepat.finance.FinanceDto;
import com.wepat.member.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class FinanceRepositoryImpl implements FinanceRepository {

    public enum ReturnType {
        SUCCESS, AlreadyDeleteFinance
    }
    private static Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private static final String CALENDAR_COLLECTION = "calendar";
    private final Firestore db = FirestoreClient.getFirestore();

    @Override
    public List<FinanceDto> getAllFinance(String calendarId) throws ExecutionException, InterruptedException {

        CollectionReference calCollection = db.collection(CALENDAR_COLLECTION);

        return calCollection.document(calendarId).get().get().toObject(CalendarEntity.class).getFinanceList();
    }

    @Override
    public void addFinance(String calendarId, FinanceDto financeDto) throws ExecutionException, InterruptedException {
        CollectionReference calCollection = db.collection(CALENDAR_COLLECTION);

        DocumentReference calDocRef = calCollection.document(calendarId);
        DocumentReference random = calCollection.document();
        financeDto.setFinanceId(random.getId());
        List<FinanceDto> financeList = calDocRef.get().get().toObject(CalendarDto.class).getFinanceList();
        financeList.add(financeDto);
        calDocRef.update("financeList", financeList);
    }

    @Override
    public FinanceDto getFinanceById(String calendarId, String financeId) throws ExecutionException, InterruptedException {
        CollectionReference calCollection = db.collection(CALENDAR_COLLECTION);

        List<FinanceDto> financeList = calCollection.document(calendarId).get().get().toObject(CalendarEntity.class).getFinanceList();
        for (FinanceDto financeDto : financeList) {
            if (financeDto.getFinanceId().equals(financeId)) {
                return financeDto;
            }
        }
        throw new AlreadyDeleteFinance();
//        ApiFuture<?> objectApiFuture = db.runTransaction(transaction -> {
//            DocumentSnapshot calSnapshot = transaction.get(calDocRef).get();
//            List<FinanceDto> financeList = calSnapshot.toObject(CalendarEntity.class).getFinanceList();
//            for (FinanceDto finance : financeList) {
//                if (finance.getFinanceId().equals(financeId)) {
//                    return finance;
//                }
//            }
//            return ReturnType.AlreadyDeleteFinance;
//        });
//        if (objectApiFuture.get() == ReturnType.AlreadyDeleteFinance) {
//            throw new AlreadyDeleteFinance();
//        } else {
//            return (FinanceDto) objectApiFuture.get();
//        }
    }

    @Override
    public void modifyFinanceById(String calendarId, String financeId, FinanceDto financeDto) throws ExecutionException, InterruptedException {
        CollectionReference calCollection = db.collection(CALENDAR_COLLECTION);

        DocumentReference calDocRef = calCollection.document(calendarId);
        ApiFuture<?> returnTypeApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot calSnapshot = transaction.get(calDocRef).get();
            List<FinanceDto> financeList = calSnapshot.toObject(CalendarEntity.class).getFinanceList();
            for (FinanceDto finance : financeList) {
                if (finance.getFinanceId().equals(financeId)) {
                    finance.setCategory(financeDto.getCategory());
                    finance.setMemo(financeDto.getMemo());
                    finance.setMoney(financeDto.getMoney());
                    finance.setIncome(financeDto.isIncome());
                    transaction.update(calDocRef, "financeList", financeList);
                    return ReturnType.SUCCESS;
                }
            }
            return ReturnType.AlreadyDeleteFinance;
        });
        if (returnTypeApiFuture.get() == ReturnType.AlreadyDeleteFinance) {
            throw new AlreadyDeleteFinance();
        }
    }

    @Override
    public void deleteFinanceById(String calendarId, String financeId) throws ExecutionException, InterruptedException {
        CollectionReference calCollection = db.collection(CALENDAR_COLLECTION);

        DocumentReference calDocRef = calCollection.document(calendarId);
        ApiFuture<?> returnTypeApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot calSnapshot = transaction.get(calDocRef).get();
            List<FinanceDto> financeList = calSnapshot.toObject(CalendarEntity.class).getFinanceList();
            for (FinanceDto financeDto : financeList) {
                if (financeDto.getFinanceId().equals(financeId)) {
                    financeList.remove(financeDto);
                    transaction.update(calDocRef, "financeList", financeList);
                    return ReturnType.SUCCESS;
                }
            }
            return ReturnType.AlreadyDeleteFinance;
        });
        if (returnTypeApiFuture.get() == ReturnType.AlreadyDeleteFinance) {
            throw new AlreadyDeleteFinance();
        }
    }
}
