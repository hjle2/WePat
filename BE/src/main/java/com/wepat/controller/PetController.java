package com.wepat.controller;

import com.wepat.exception.member.NotExistCalendarException;
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
    public ResponseEntity<?> addPet(String calendarId, PetDto petDto) {
        System.out.println(">>>>>>>>>>.. 컨트롤러 호출!!");
        try {
            return new ResponseEntity<>(petService.addPet(calendarId, petDto), HttpStatus.OK);
        } catch (NotExistCalendarException e) {
            throw new NotExistCalendarException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException();
        }
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
