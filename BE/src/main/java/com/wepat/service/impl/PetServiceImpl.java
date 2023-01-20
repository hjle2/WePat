package com.wepat.service.impl;

import com.wepat.dto.PetDto;
import com.wepat.repository.CalendarRepository;
import com.wepat.repository.PetRepository;
import com.wepat.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepo;
    @Autowired
    public PetServiceImpl(PetRepository petRepo) {
        this.petRepo = petRepo;
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
    public PetDto addPet(String calendarId, PetDto pet) throws ExecutionException, InterruptedException {
        return petRepo.addPet(calendarId, pet);
    }

    @Override
    public PetDto modifyPet(String petId, PetDto pet) throws ExecutionException, InterruptedException {
        return petRepo.modifyPet(petId, pet);
    }

    @Override
    public PetDto addPetWeight(String petId, double weight) throws ExecutionException, InterruptedException {
        return petRepo.addPetWeight(petId, weight);
    }

    @Override
    public PetDto deletePet(String CalendarId, String petId) throws ExecutionException, InterruptedException {
        return petRepo.deletePet(CalendarId, petId);
    }
}
