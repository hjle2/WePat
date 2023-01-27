package com.wepat.exception.photo;

import com.wepat.controller.PhotoController;
import com.wepat.exception.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {PhotoController.class})
public class PhotoExceptionHandler {

    @ExceptionHandler(NotExistImage.class)
    public ResponseEntity<?> NotExistImage(NotExistImage e) {
        System.out.println("notExis호출!!!");
        ErrorDto errorDto = new ErrorDto("NotExistImage", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyDeleteImage.class)
    public ResponseEntity<?> AlreadyDeleteImage(AlreadyDeleteImage e) {
        ErrorDto errorDto = new ErrorDto("AlreadyDeleteImage", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

}
