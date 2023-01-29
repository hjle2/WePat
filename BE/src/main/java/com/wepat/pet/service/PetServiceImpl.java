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
    public void addPet(PetDto pet) throws ExecutionException, InterruptedException {
        petRepo.addPet(pet);
    }

    @Override
    public List<PetDto> getAllPet(String calendarId) throws ExecutionException, InterruptedException {
        return petRepo.getAllPets(calendarId);
    }

    @Override
    public PetDto getPet(String calendarId) throws ExecutionException, InterruptedException {
        return petRepo.getPet(calendarId);
    }

    @Override
    public void modifyPet(String petId, PetDto pet) throws ExecutionException, InterruptedException {
        petRepo.modifyPet(petId, pet);
    }

    @Override
    public void addPetWeight(String petId, WeightDto weightDto) throws ExecutionException, InterruptedException {
        petRepo.addPetWeight(petId, weightDto);
    }

    @Override
    public void modifyPetWeight(String petId, String date, WeightDto weightDto) throws ExecutionException, InterruptedException {
        petRepo.modifyPetWeight(petId, date, weightDto);
    }

    @Override
    public void deletePet(String petId) throws ExecutionException, InterruptedException {
        petRepo.deletePet(petId);
    }

}
