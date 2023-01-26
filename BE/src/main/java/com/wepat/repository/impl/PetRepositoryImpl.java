package com.wepat.repository.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.dto.PetDto;
import com.wepat.dto.WeightDto;
import com.wepat.entity.CalendarEntity;
import com.wepat.entity.PetEntity;
import com.wepat.exception.NotExistCalendarException;
import com.wepat.exception.NotExistPet;
import com.wepat.repository.MemberRepository;
import com.wepat.repository.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public PetDto addPet(PetDto pet) throws ExecutionException, InterruptedException {
        System.out.println(">>>>>>>>>> 레포 호출!!!");

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
            return petCollection.document(petDocRef.getId()).get().get().toObject(PetDto.class);
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
        final PetDto[] petDto = {null};
        ApiFuture<PetDto> stringApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot petSnapshot = transaction.get(petDocRef).get();
            if (petSnapshot.exists()) {
                return transaction.get(petDocRef).get().toObject(PetDto.class);
            } else {
                return null;
            }
        });
        if ((stringApiFuture.get()).equals(null)) {
            throw new NotExistPet("등록되지 않은 반려동물입니다!");
        } else {
            return stringApiFuture.get();
        }
    }

    @Override
    public PetDto modifyPet(String petId, PetDto pet) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = petCollection.document(petId).set(pet);
        return pet;
    }

    @Override
    public PetEntity addPetWeight(String petId, WeightDto weightDto) throws ExecutionException, InterruptedException {
        DocumentReference petDocRef = petCollection.document(petId);
        ApiFuture<String> stringApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot petSnapshot = transaction.get(petDocRef).get();
            if (petSnapshot.exists()) {
                List<WeightDto> weightList = transaction.get(petDocRef).get().toObject(PetEntity.class).getWeightList();
                weightList.add(weightDto);
                transaction.update(petDocRef, "weightList", weightList);
                return "success";
            } else {
                return "NotExistPet";
            }
        });
        if ((stringApiFuture.get()).equals("success")) {
            return petDocRef.get().get().toObject(PetEntity.class);
        } else {
            throw new NotExistPet("등록되지 않은 반려동물입니다!");
        }
    }

    @Override
    public ResponseEntity<?> deletePet(String CalendarId, String petId) throws ExecutionException, InterruptedException {
        // 달력은 회원이 없지않는 이상 사라지지 않음
        // 반려동물만 존재 확인
        DocumentReference petDocRef = petCollection.document(petId);
        DocumentReference calDocRef = calCollection.document(CalendarId);

        ApiFuture<String> stringApiFuture = db.runTransaction(transaction -> {
            DocumentSnapshot petSnapshot = transaction.get(petDocRef).get();
            if (petSnapshot.exists()) {
                transaction.delete(petDocRef);
                List<String> petIdList = calDocRef.get().get().toObject(CalendarEntity.class).getPetId();
                petIdList.remove(petId);
                transaction.update(calDocRef, "petId", petIdList);
                return "success";
            } else {
                return "NotExistPet";
            }
        });

        if ((stringApiFuture.get()).equals("success")) {
            return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
        } else {
            throw new NotExistPet("등록되지 않은 반려동물입니다!");
        }
    }

}
