package com.wepat.controller;

import com.wepat.dto.WeightDto;
import com.wepat.exception.member.NotExistCalendarException;
import com.wepat.exception.pet.NotExistPet;
import com.wepat.service.PetService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wepat.dto.PetDto;
import io.swagger.annotations.ApiOperation;

import java.util.concurrent.ExecutionException;
import java.util.List;

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
        System.out.println(">>>>>>>>>>.. 컨트롤러 호출!!");
        try {
            return new ResponseEntity<>(petService.addPet(petDto), HttpStatus.OK);
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

    @PutMapping("/add/weight")
    @ApiOperation(value = "몸무게 추가")
    public ResponseEntity<?> addPetWeight(String petId, WeightDto weightDto) {
        try {
            return new ResponseEntity<>(petService.addPetWeight(petId, weightDto), HttpStatus.OK);
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
            return new ResponseEntity<>(petService.deletePet(calendarId, petId), HttpStatus.OK);
        } catch (NotExistPet e) {
            throw new NotExistPet(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }



}
