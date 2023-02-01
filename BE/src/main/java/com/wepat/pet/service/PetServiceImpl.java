package com.wepat.pet.service;

import com.wepat.pet.PetDto;
import com.wepat.pet.PetEntity;
import com.wepat.pet.WeightDto;
import com.wepat.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final PetRepository petRepo;

    @Override
    public void addPet(PetDto petDto) throws ExecutionException, InterruptedException {
        petRepo.addPet(petDto);
    }

    @Override
    public List<PetDto> getAllPet(String calendarId) throws ExecutionException, InterruptedException {
        return petRepo.getAllPet(calendarId);
    }

    @Override
    public PetDto getPetById(String petId) throws ExecutionException, InterruptedException {
        return petRepo.getPetById(petId);
    }

    @Override
    public void modifyPetById(String petId, PetDto petDto) throws ExecutionException, InterruptedException {
        petRepo.modifyPetById(petId, petDto);
    }

    @Override
    public void addPetWeightById(String petId, WeightDto weightDto) throws ExecutionException, InterruptedException {
        petRepo.addPetWeightById(petId, weightDto);
    }

    @Override
    public void modifyPetWeight(String petId, WeightDto weightDto) throws ExecutionException, InterruptedException {
        petRepo.modifyPetWeight(petId, weightDto);
    }

    @Override
    public void deletePetById(String petId) throws ExecutionException, InterruptedException {
        petRepo.deletePetById(petId);
    }

}
