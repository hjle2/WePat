package com.wepat.domain.pet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

import java.util.concurrent.ExecutionException;
import java.util.List;

@RestController
@RequestMapping("/pet")
@CrossOrigin(origins = "*")
public class PetController {
    private final Logger logger = LoggerFactory.getLogger(PetController.class);
    private PetService petService;
    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/{calendarid}")
    @ApiOperation(value = "반려동물 상세페이지")
    public ResponseEntity<?> getAllPet(@PathVariable("calendarid") String calendarId) {
        try {
            List<PetDto> petList = petService.getAllPet(calendarId);
        } catch (ExecutionException e) {
        } catch (InterruptedException e) {
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{petid}")
    @ApiOperation(value = "반려동물 상세페이지")
    public ResponseEntity<?> getPet(@PathVariable("petid") String petId) {
        try {
            PetDto pet = petService.getPet(petId);
        } catch (ExecutionException e) {
        } catch (InterruptedException e) {
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{petid}/{weight}")
    @ApiOperation(value = "몸무게 추가")
    public ResponseEntity<?> addPetWeight(@PathVariable("petid") String petId, @PathVariable double weight) {
        try {
            PetDto pet = petService.addPetWeight(petId, weight);
        } catch (ExecutionException e) {
        } catch (InterruptedException e) {
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/add")
    @ApiOperation(value = "반려동물 추가")
    public ResponseEntity<?> addPet(String calendarId, PetDto petDto) {
        try {
            PetDto pet = petService.addPet(calendarId, petDto);
        } catch (ExecutionException e) {
        } catch (InterruptedException e) {
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{calendarid}/{petid}")
    @ApiOperation(value = "반려동물 삭제")
    public ResponseEntity deletePet(@PathVariable("calendarid") String calendarId, @PathVariable("petid") String petId) {
        try {
            PetDto pet = petService.deletePet(calendarId, petId);
        } catch (ExecutionException e) {
        } catch (InterruptedException e) {
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
