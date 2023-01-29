package com.wepat.pet.controller;

import com.wepat.exception.pet.NotExistCalendarException;
import com.wepat.exception.pet.NotExistPet;
import com.wepat.pet.PetDto;
import com.wepat.pet.WeightDto;
import com.wepat.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PetController {
    private final static Logger logger = LoggerFactory.getLogger(PetController.class);
    private final PetService petService;

    @PostMapping("/add")
    @ApiOperation(value = "반려동물 추가")
    public ResponseEntity<?> addPet(PetDto petDto) {
        try {
            petService.addPet(petDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotExistCalendarException e) {
            throw new NotExistCalendarException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @GetMapping("/all/{calendarid}")
    @ApiOperation(value = "등록된 모든 반려동물")
    public ResponseEntity<?> getAllPet(@PathVariable("calendarid") String calendarId) {
        try {
            return new ResponseEntity<>(petService.getAllPet(calendarId), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @GetMapping("/{petid}")
    @ApiOperation(value = "반려동물 상세페이지")
    public ResponseEntity<?> getPet(@PathVariable("petid") String petId) {
        try {
            return new ResponseEntity<>(petService.getPet(petId), HttpStatus.OK);
        } catch (NotExistPet e) {
            throw new NotExistPet(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @PostMapping("/modify/{petid}")
    @ApiOperation(value = "반려동물 상세페이지")
    public ResponseEntity<?> modifyPet(@PathVariable("petid") String petId,
                                       @RequestBody PetDto petDto) {
        try {
            petService.modifyPet(petId, petDto);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (NotExistPet e) {
            throw new NotExistPet(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @PutMapping("/add/weight")
    @ApiOperation(value = "몸무게 추가")
    public ResponseEntity<?> addPetWeight(String petId, WeightDto weightDto) {
        try {
            petService.addPetWeight(petId, weightDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotExistPet e) {
            throw new NotExistPet(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @DeleteMapping("/{calendarid}/{petid}")
    @ApiOperation(value = "반려동물 삭제")
    public ResponseEntity<?> deletePet(@PathVariable("calendarid") String calendarId, @PathVariable("petid") String petId) {
        try {
            petService.deletePet(calendarId, petId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotExistPet e) {
            throw new NotExistPet(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
