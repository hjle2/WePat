package com.wepat.pet.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.wepat.member.repository.MemberRepository;
import com.wepat.pet.PetDto;
import com.wepat.pet.PetEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class PetRepositoryImpl implements PetRepository {
    private final Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private final String COLLECTION_NAME = "pet";
    private final CollectionReference collection = FirestoreClient.getFirestore().collection(COLLECTION_NAME);
    @Override
    public List<PetDto> getAllPets(String calendarId) throws ExecutionException, InterruptedException {
        Query petList = collection.whereEqualTo("calendarid", calendarId);

        ApiFuture<QuerySnapshot> future = petList.get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (DocumentSnapshot document: documents) {
        }
        return null;
    }

    @Override
    public PetDto getPet(String petId) throws ExecutionException, InterruptedException {
        return collection.document(petId).get().get().toObject(PetDto.class);
    }

    @Override
    public PetDto addPet(String calendarId, PetDto pet) throws ExecutionException, InterruptedException {
        DocumentReference docRef = null;
        if (calendarId == null) {
            docRef = collection.document();
        } else {
            docRef = collection.document(calendarId);
        }
        ApiFuture<WriteResult> future = docRef.set(new PetEntity(pet));

        return pet;
    }

    @Override
    public PetDto modifyPet(String petId, PetDto pet) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = collection.document(petId).set(pet);
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
