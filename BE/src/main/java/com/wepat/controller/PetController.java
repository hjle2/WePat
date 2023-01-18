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
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class PetController {
    private final Logger logger = LoggerFactory.getLogger(PetController.class);

    @GetMapping("/{petid}")
    @ApiOperation(value = "반려동물 상세페이지")
    public ResponseEntity<?> getPet(@PathVariable String petid) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{petid}/{weight}")
    @ApiOperation(value = "몸무게 추가")
    public ResponseEntity<?> addPetWeight(@PathVariable String petid, @PathVariable String weight) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{calendarid}")
    @ApiOperation(value = "반려동물 추가페이지")
    public ResponseEntity<?> addPagePet(@PathVariable String calendarid) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{calendarid}")
    @ApiOperation(value = "반려동물 추가")
    public ResponseEntity<?> addPet(@PathVariable String calendarid, @RequestBody PetDto petDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{calendarid}/{petid}")
    public ResponseEntity deletePet(@PathVariable String calendarid, @PathVariable String petid) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
