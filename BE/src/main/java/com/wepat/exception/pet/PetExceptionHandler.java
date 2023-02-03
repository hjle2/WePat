package com.wepat.exception.pet;

import com.wepat.exception.ErrorDto;
import com.wepat.pet.controller.PetController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {PetController.class})
public class PetExceptionHandler {
    @ExceptionHandler(NotExistCalendarException.class)
    public ResponseEntity<?> NotExistCalendarException(NotExistCalendarException e) {
        ErrorDto errorDto = new ErrorDto("NotExistCalendarException", "존재하지 않는 코드입니다.");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistPetException.class)
    public ResponseEntity<?> NotExistPet(NotExistPetException e) {
        ErrorDto errorDto = new ErrorDto("NotExistPetException", "존재하지 않는 반려동물입니다.");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyDeletePetException.class)
    public ResponseEntity<?> AlreadyDeletePet(AlreadyDeletePetException e) {
        ErrorDto errorDto = new ErrorDto("AlreadyDeletePetException", "이미 삭제된 반려동물입니다.");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDto exHandler(Exception e) {
        return new ErrorDto("Exception", "서버에 오류가 발생했습니다.");
    }
}
