package com.wepat.repository.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.dto.CalendarDto;
import com.wepat.dto.PetDto;
import com.wepat.entity.CalendarEntity;
import com.wepat.entity.PetEntity;
import com.wepat.exception.member.NotExistCalendarException;
import com.wepat.repository.MemberRepository;
import com.wepat.repository.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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
    public PetDto addPet(String calendarId, PetDto pet) throws ExecutionException, InterruptedException {
        System.out.println(">>>>>>>>>> 레포 호출!!!");

        final DocumentReference petDocRef = petCollection.document(); //pet 랜덤 doc생성
        final DocumentReference calDocRef = calCollection.document(calendarId); //받아온 캘린더 pk

        ApiFuture<String> stringApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot calSnapshot = transaction.get(calDocRef).get();
            if (calSnapshot.exists()) { //달력 존재
                List<String> petId = calDocRef.get().get().toObject(CalendarEntity.class).getPetId();
                petId.add(petDocRef.getId());
                transaction.update(calDocRef, "petId", petId);
                transaction.create(petDocRef, new PetEntity(pet));
                return "success";
            } else { //달력 없음
                return "NotExistCalendarException";
            }
        });
        if ((stringApiFuture.get()).equals("success")) {
            System.out.println(petCollection.document(petDocRef.getId()).get().get().exists());
            System.out.println(petCollection.document(petDocRef.getId()).get().get().toObject(PetEntity.class));
            return pet;
        } else {
            throw new NotExistCalendarException("캘린더 코드 오류!");
        }
    }

    @Override
    public List<PetDto> getAllPets(String calendarId) throws ExecutionException, InterruptedException {
        Query petList = petCollection.whereEqualTo("calendarid", calendarId);

        ApiFuture<QuerySnapshot> future = petList.get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (DocumentSnapshot document: documents) {
        }
        return null;
    }

    @Override
    public PetDto getPet(String petId) throws ExecutionException, InterruptedException {
        return petCollection.document(petId).get().get().toObject(PetDto.class);
    }

    @Override
    public PetDto modifyPet(String petId, PetDto pet) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = petCollection.document(petId).set(pet);
        return pet;
    }

    @Override
    public PetDto addPetWeight(String petId, double weight) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public PetDto deletePet(String CalendarId, String petId) throws ExecutionException, InterruptedException {
        return null;
    }
}
