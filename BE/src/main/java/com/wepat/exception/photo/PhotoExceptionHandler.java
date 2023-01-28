package com.wepat.exception.photo;

import com.wepat.controller.PhotoController;
import com.wepat.exception.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {PhotoController.class})
public class PhotoExceptionHandler {

    @ExceptionHandler(NotExistImage.class)
    public ResponseEntity<?> NotExistImage(NotExistImage e) {
        System.out.println("notExis호출!!!");
        ErrorDto errorDto = new ErrorDto("NotExistImage", "존재하지 않는 이미지입니다.");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyDeleteImage.class)
    public ResponseEntity<?> AlreadyDeleteImage(AlreadyDeleteImage e) {
        ErrorDto errorDto = new ErrorDto("AlreadyDeleteImage", "이미 삭제된 이미지입니다.");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UpdateSNSCancel.class)
    public ResponseEntity<?> UpdateSNSCancel(UpdateSNSCancel e) {
        ErrorDto errorDto = new ErrorDto("UpdateSNSCancel", "업로드 취소");
        return new ResponseEntity<>(errorDto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDto exHandler(Exception e) {
        return new ErrorDto("Exception", "서버에 오류가 발생했습니다.");
    }

}
