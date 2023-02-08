package com.wepat.pet.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.schedule.CalendarEntity;
import com.wepat.exception.pet.AlreadyDeletePetException;
import com.wepat.exception.pet.NotExistCalendarException;
import com.wepat.exception.pet.NotExistPetException;
import com.wepat.pet.PetDto;
import com.wepat.pet.PetEntity;
import com.wepat.pet.WeightDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class PetRepositoryImpl implements PetRepository {

    public enum ReturnType {
        SUCCESS, NotExistCalendarException, NotExistPetException, AlreadyDeletePetException
    }
    private static final String PET_COLLECTION = "pet";
    private static final String CALENDAR_COLLECTION = "calendar";
    private static final String SCHEDULE_COLLECTION = "schedule";

    @Override
    public void addPet(PetDto petDto) throws ExecutionException, InterruptedException {
        CollectionReference petCollection = FirestoreClient.getFirestore().collection(PET_COLLECTION);
        CollectionReference calCollection = FirestoreClient.getFirestore().collection(CALENDAR_COLLECTION);
        final DocumentReference petDocRef = petCollection.document(); //pet 랜덤 doc생성
        final DocumentReference calDocRef = calCollection.document(petDto.getCalendarId()); //받아온 캘린더 pk

        ApiFuture<ReturnType> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
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
        CollectionReference petCollection = FirestoreClient.getFirestore().collection(PET_COLLECTION);
        return petCollection.whereEqualTo("calendarId", calendarId).get().get().toObjects(PetDto.class);
    }

    @Override
    public PetDto getPetById(String petId) throws ExecutionException, InterruptedException {
        CollectionReference petCollection = FirestoreClient.getFirestore().collection(PET_COLLECTION);
        return petCollection.document(petId).get().get().toObject(PetDto.class);
    }

    @Override
    public void modifyPetById(String petId, PetDto petDto) throws ExecutionException, InterruptedException {
        CollectionReference petCollection = FirestoreClient.getFirestore().collection(PET_COLLECTION);
        final DocumentReference petDocRef = petCollection.document(petId);

        ApiFuture<ReturnType> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
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
                return ReturnType.NotExistPetException;
            }
        });
        if (future.get() == ReturnType.NotExistPetException) {
            throw new NotExistPetException();
        }
    }

    @Override
    public void addPetWeightById(String petId, WeightDto weightDto) throws ExecutionException, InterruptedException {
        CollectionReference petCollection = FirestoreClient.getFirestore().collection(PET_COLLECTION);
        DocumentReference petDocRef = petCollection.document(petId);
        ApiFuture<ReturnType> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot petSnapshot = transaction.get(petDocRef).get();
            if (petSnapshot.exists()) {
                List<WeightDto> weightList = petDocRef.get().get().toObject(PetEntity.class).getWeightList();
                if (weightList.size() == 0) {
                    weightList.add(weightDto);
                } else if (weightList.get(weightList.size()-1).getDate().equals(weightDto.getDate())) {
                    weightList.get(weightList.size()-1).setWeight(weightDto.getWeight());
                } else {
                    weightList.add(weightDto);
                }
                transaction.update(petDocRef, "weightList", weightList);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistPetException;
            }
        });
        if (future.get() == ReturnType.NotExistPetException) {
            throw new NotExistPetException();
        }
    }

    @Override
    public void modifyPetWeight(String petId, WeightDto weightDto) throws ExecutionException, InterruptedException {
        CollectionReference petCollection = FirestoreClient.getFirestore().collection(PET_COLLECTION);
        DocumentReference petDocRef = petCollection.document(petId);
        ApiFuture<ReturnType> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot petSnapshot = transaction.get(petDocRef).get();
            if (petSnapshot.exists()) {
                List<WeightDto> weightList = petDocRef.get().get().toObject(PetEntity.class).getWeightList();
                for (WeightDto weight : weightList) {
                    String weightDate = weight.getDate();
                    if (weightDate != null && weightDate.equals(weightDto.getDate())) {
                        weight.setWeight(weightDto.getWeight());
                        break;
                    }
                }
                transaction.update(petDocRef, "weightList", weightList);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.NotExistPetException;
            }
        });
        if (future.get() == ReturnType.NotExistPetException) {
            throw new NotExistPetException();
        }
    }

    @Override
    public void deletePetById(String petId) throws ExecutionException, InterruptedException {
        CollectionReference petCollection = FirestoreClient.getFirestore().collection(PET_COLLECTION);
        CollectionReference scheduleCollection = FirestoreClient.getFirestore().collection(SCHEDULE_COLLECTION);
        DocumentReference petDocRef = petCollection.document(petId);

        ApiFuture<ReturnType> future = FirestoreClient.getFirestore().runTransaction(transaction -> {
            DocumentSnapshot petSnapshot = transaction.get(petDocRef).get();
            if (petSnapshot.exists()) {
                List<String> scheduleList = petDocRef.get().get().toObject(PetEntity.class).getSchedule();
                for (String schedule : scheduleList) {
                    scheduleCollection.document(schedule).delete();
                }
                transaction.delete(petDocRef);
                return ReturnType.SUCCESS;
            } else {
                return ReturnType.AlreadyDeletePetException;
            }
        });
        if (future.get() == ReturnType.AlreadyDeletePetException) {
            throw new AlreadyDeletePetException();
        }
    }
}
