package com.wepat.pet.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.calendar.CalendarEntity;
import com.wepat.exception.pet.NotExistCalendarException;
import com.wepat.exception.pet.NotExistPet;
import com.wepat.member.repository.MemberRepository;
import com.wepat.pet.PetDto;
import com.wepat.pet.PetEntity;
import com.wepat.pet.WeightDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class PetRepositoryImpl implements PetRepository {
    private final static Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private final static String PET_COLLECTION = "pet";
    private final static String CALENDAR_COLLECTION = "calendar";
    private final Firestore db = FirestoreClient.getFirestore();
    private final CollectionReference petCollection = db.collection(PET_COLLECTION);
    private final CollectionReference calCollection = db.collection(CALENDAR_COLLECTION);

    @Override
    public void addPet(PetDto pet) throws ExecutionException, InterruptedException {

        final DocumentReference petDocRef = petCollection.document(); //pet 랜덤 doc생성
        final DocumentReference calDocRef = calCollection.document(pet.getCalendarId()); //받아온 캘린더 pk
//        petCollection.document(petDocRef.getId()).create(new PetEntity(pet));
//        return
        ApiFuture<String> stringApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot calSnapshot = transaction.get(calDocRef).get();
            if (calSnapshot.exists()) { //달력 존재
                List<String> petIdList = calDocRef.get().get().toObject(CalendarEntity.class).getPetId();
                petIdList.add(petDocRef.getId());
                transaction.update(calDocRef, "petId", petIdList);
                PetEntity petEntity = new PetEntity(pet);
                petEntity.setPetId(petDocRef.getId());
                transaction.create(petDocRef, petEntity);
                return "success";
            } else { //달력 없음
                return "NotExistCalendarException";
            }
        });
        if ((stringApiFuture.get()).equals("success")) {
        } else {
            throw new NotExistCalendarException("캘린더 코드 오류!");
        }
    }

    @Override
    public List<PetDto> getAllPets(String calendarId) throws ExecutionException, InterruptedException {
        List<PetDto> petDtoList = new ArrayList<>();
        List<QueryDocumentSnapshot> petDocuments = petCollection.get().get().getDocuments();

        ApiFuture<String> stringApiFuture = db.runTransaction(transaction -> {
            for (QueryDocumentSnapshot petDocs : petDocuments) {
                if (petDocs.exists() && (petDocs.toObject(PetDto.class).getCalendarId()).equals(calendarId)) {
                    String petId = petDocs.getId();
                    petDtoList.add(petCollection.document(petId).get().get().toObject(PetDto.class));
                }
            }
            return "success";
        });
        if ((stringApiFuture.get()).equals("success")) {
            return petDtoList;
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public PetDto getPet(String petId) throws ExecutionException, InterruptedException {
        //가족구성원 중 다른이가 삭제시 반려동물 존재여부 확인 필요?
        DocumentReference petDocRef = petCollection.document(petId);
//        final PetDto[] petDto = {null};
        ApiFuture<PetDto> stringApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot petSnapshot = transaction.get(petDocRef).get();
            if (petSnapshot.exists()) {
                return transaction.get(petDocRef).get().toObject(PetDto.class);
            } else {
                return null;
            }
        });
        if ((stringApiFuture.get())==null) {
            throw new NotExistPet("등록되지 않은 반려동물입니다!");
        } else {
            return stringApiFuture.get();
        }
    }

    @Override
    public void modifyPet(String petId, PetDto pet) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = petCollection.document(petId).set(pet);
    }

    @Override
    public void addPetWeight(String petId, WeightDto weight) throws ExecutionException, InterruptedException {
    }

    @Override
    public void deletePet(String CalendarId, String petId) throws ExecutionException, InterruptedException {
    }
}
