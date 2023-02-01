package com.wepat.pet.controller;

import com.wepat.exception.pet.AlreadyDeletePet;
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

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PetController {
    private static final Logger logger = LoggerFactory.getLogger(PetController.class);
    private final PetService petService;

    @PostMapping("/add")
    @ApiOperation(value = "반려동물 추가")
    public ResponseEntity<?> addPet(PetDto petDto) {
        try {
            petService.addPet(petDto);
            return new ResponseEntity<>("추가 성공", HttpStatus.OK);
        } catch (NotExistCalendarException e) {
            throw new NotExistCalendarException();
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
    public ResponseEntity<?> getPetById(@PathVariable("petid") String petId) {
        try {
            return new ResponseEntity<>(petService.getPetById(petId), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @PutMapping("/modify/{petid}")
    @ApiOperation(value = "반려동물 정보 수정")
    public ResponseEntity<?> modifyPetById(@PathVariable("petid") String petId,
                                           @RequestBody PetDto petDto) {

        try {
            petService.modifyPetById(petId, petDto);
            return new ResponseEntity<>("수정 성공", HttpStatus.ACCEPTED);
        } catch (NotExistPet e) {
            throw new NotExistPet();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @PutMapping("/add/weight")
    @ApiOperation(value = "몸무게 추가")
    public ResponseEntity<?> addPetWeightById(String petId, @RequestBody WeightDto weightDto) {
        try {
            petService.addPetWeightById(petId, weightDto);
            return new ResponseEntity<>("몸무게 추가 성공", HttpStatus.OK);
        } catch (NotExistPet e) {
            throw new NotExistPet();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @PutMapping("/modify/weight")
    @ApiOperation(value = "몸무게 수정")
    public ResponseEntity<?> modifyPetWeightById(String petId, @RequestBody WeightDto weightDto) {
        try {
            petService.modifyPetWeight(petId, weightDto);
            return new ResponseEntity<>("수정 성공", HttpStatus.ACCEPTED);
        } catch (NotExistPet e) {
            throw new NotExistPet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete/{petid}")
    @ApiOperation(value = "반려동물 삭제")
    public ResponseEntity<?> deletePetById(@PathVariable("petid") String petId) {
        try {
            petService.deletePetById(petId);
            return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
        } catch (AlreadyDeletePet e) {
            throw new AlreadyDeletePet();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
