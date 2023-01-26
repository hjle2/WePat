package com.wepat.service.impl;

import com.wepat.dto.PetDto;
import com.wepat.dto.WeightDto;
import com.wepat.entity.PetEntity;
import com.wepat.exception.NotExistPet;
import com.wepat.repository.PetRepository;
import com.wepat.service.PetService;
import lombok.RequiredArgsConstructor;
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
    @Transactional
    public PetDto addPet(PetDto pet) throws ExecutionException, InterruptedException {
        return petRepo.addPet(pet);
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
    public PetDto modifyPet(String petId, PetDto pet) throws ExecutionException, InterruptedException {
        return petRepo.modifyPet(petId, pet);
    }

    @Override
    public PetEntity addPetWeight(String petId, WeightDto weightDto) throws ExecutionException, InterruptedException {
        return petRepo.addPetWeight(petId, weightDto);
    }

    @Override
    public ResponseEntity<?> deletePet(String CalendarId, String petId) throws ExecutionException, InterruptedException {
        return petRepo.deletePet(CalendarId, petId);
    }

}
