package com.wepat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wepat.dto.PetDto;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pet")
@CrossOrigin(origins = "*")
public class PetController {
    private final Logger logger = LoggerFactory.getLogger(PetController.class);

    @GetMapping("/{petId}")
    @ApiOperation(value = "반려동물 상세페이지")
    public ResponseEntity<?> getPet(@PathVariable String petId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{petId}/{weight}")
    @ApiOperation(value = "몸무게 추가")
    public ResponseEntity<?> addPetWeight(@PathVariable String petId, @PathVariable String weight) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{calendarId}")
    @ApiOperation(value = "반려동물 추가페이지")
    public ResponseEntity<?> addPagePet(@PathVariable String calendarId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{calendarId}")
    @ApiOperation(value = "반려동물 추가")
    public ResponseEntity<?> addPet(@PathVariable String calendarId, @RequestBody PetDto petDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{calendarId}/{petId}")
    public ResponseEntity deletePet(@PathVariable String calendarId, @PathVariable String petId) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
