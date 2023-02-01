package com.wepat.pet.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.calendar.CalendarEntity;
import com.wepat.exception.pet.AlreadyDeletePet;
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

    public enum ReturnType {
        SUCCESS, NotExistCalendarException, NotExistPet, AlreadyDeletePet
    }
    private static final Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private static final String PET_COLLECTION = "pet";
    private static final String CALENDAR_COLLECTION = "calendar";
    private static final String SCHEDULE_COLLECTION = "schedule";
    private final Firestore db = FirestoreClient.getFirestore();

    @Override
    public void addPet(PetDto petDto) throws ExecutionException, InterruptedException {
        CollectionReference petCollection = db.collection(PET_COLLECTION);
        CollectionReference calCollection = db.collection(CALENDAR_COLLECTION);
        final DocumentReference petDocRef = petCollection.document(); //pet 랜덤 doc생성
        final DocumentReference calDocRef = calCollection.document(petDto.getCalendarId()); //받아온 캘린더 pk

        ApiFuture<ReturnType> future = db.runTransaction(transaction -> {
            DocumentSnapshot calSnapshot = transaction.get(calDocRef).get();
            if (calSnapshot.exists()) { //달력 존재
                List<String> petIdList = calDocRef.get().get().toObject(CalendarEntity.class).getPetId();
                petIdList.add(petDocRef.getId());
                transaction.update(calDocRef, "petId", petIdList);

                PetEntity petEntity = new PetEntity(petDto);
                petEntity.setCalendarId(calDocRef.getId());
                petEntity.setPetId(petDocRef.getId());
                transaction.create(petDocRef, petEntity);
                return ReturnType.SUCCESS;
            } else { //달력 없음
                return ReturnType.NotExistCalendarException;
            }
        });
        if (future.get() == ReturnType.NotExistCalendarException) {
            throw new NotExistCalendarException();
        }
    }

    @Override
    public List<PetDto> getAllPet(String calendarId) throws ExecutionException, InterruptedException {
        CollectionReference petCollection = db.collection(PET_COLLECTION);
        return petCollection.whereEqualTo("calendarId", calendarId).get().get().toObjects(PetDto.class);
    }

    @Override
    public PetDto getPetById(String petId) throws ExecutionException, InterruptedException {
        CollectionReference petCollection = db.collection(PET_COLLECTION);
        return petCollection.document(petId).get().get().toObject(PetDto.class);
    }

    @Override
    public void modifyPetById(String petId, PetDto petDto) throws ExecutionException, InterruptedException {
        CollectionReference petCollection = db.collection(PET_COLLECTION);
        final DocumentReference petDocRef = petCollection.document(petId);

        ApiFuture<ReturnType> future = db.runTransaction(transaction -> {
            DocumentSnapshot petSnapshot = transaction.get(petDocRef).get();
            if (petSnapshot.exists()) {
                PetEntity petEntity = new PetEntity(petDto);
                petEntity.setPetId(petId);
                petEntity.setCalendarId(petSnapshot.toObject(PetEntity.class).getCalendarId());
                petEntity.setSchedule(petSnapshot.toObject(PetEntity.class).getSchedule());
                petEntity.setWeightList(petSnapshot.toObject(PetEntity.class).getWeightList());
                petCollection.document(petId).set(petEntity);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistPet;
            }
        });
        if (future.get() == ReturnType.NotExistPet) {
            throw new NotExistPet();
        }
    }

    @Override
    public void addPetWeightById(String petId, WeightDto weightDto) throws ExecutionException, InterruptedException {
        CollectionReference petCollection = db.collection(PET_COLLECTION);
        DocumentReference petDocRef = petCollection.document(petId);
        ApiFuture<ReturnType> future = db.runTransaction(transaction -> {
            DocumentSnapshot petSnapshot = transaction.get(petDocRef).get();
            if (petSnapshot.exists()) {
                List<WeightDto> weightList = petDocRef.get().get().toObject(PetEntity.class).getWeightList();
                weightList.add(weightDto);
                transaction.update(petDocRef, "weightList", weightList);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistPet;
            }
        });
        if (future.get() == ReturnType.NotExistPet) {
            throw new NotExistPet();
        }
    }

    @Override
    public void modifyPetWeight(String petId, WeightDto weightDto) throws ExecutionException, InterruptedException {
        CollectionReference petCollection = db.collection(PET_COLLECTION);
        DocumentReference petDocRef = petCollection.document(petId);
        ApiFuture<ReturnType> future = db.runTransaction(transaction -> {
            DocumentSnapshot petSnapshot = transaction.get(petDocRef).get();
            if (petSnapshot.exists()) {
                List<WeightDto> weightList = petDocRef.get().get().toObject(PetEntity.class).getWeightList();
                for (WeightDto weight : weightList) {
                    if (weight.getDate().equals(weightDto.getDate())) {
                        weight.setWeight(weightDto.getWeight());
                        break;
                    }
                }
                transaction.update(petDocRef, "weightList", weightList);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistPet;
            }
        });
        if (future.get() == ReturnType.NotExistPet) {
            throw new NotExistPet();
        }
    }

    @Override
    public void deletePetById(String petId) throws ExecutionException, InterruptedException {
        CollectionReference petCollection = db.collection(PET_COLLECTION);
        CollectionReference scheduleCollection = db.collection(SCHEDULE_COLLECTION);
        DocumentReference petDocRef = petCollection.document(petId);

        ApiFuture<ReturnType> future = db.runTransaction(transaction -> {
            DocumentSnapshot petSnapshot = transaction.get(petDocRef).get();
            if (petSnapshot.exists()) {
                List<String> scheduleList = petDocRef.get().get().toObject(PetEntity.class).getSchedule();
                for (String schedule : scheduleList) {
                    scheduleCollection.document(schedule).delete();
                }
                transaction.delete(petDocRef);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.AlreadyDeletePet;
            }
        });
        if (future.get() == ReturnType.AlreadyDeletePet) {
            throw new AlreadyDeletePet();
        }
    }
}
