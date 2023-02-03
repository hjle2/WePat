package com.wepat.exception.sns;

import com.wepat.exception.ErrorDto;
import com.wepat.sns.controller.SNSController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {SNSController.class})
public class SNSExceptionHandler {

    @ExceptionHandler(NotExistImageException.class)
    public ResponseEntity<?> NotExistImage(NotExistImageException e) {
        ErrorDto errorDto = new ErrorDto("NotExistImageException", "존재하지 않는 이미지입니다.");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyReportImageException.class)
    public ResponseEntity<?> AlreadyReportImage(AlreadyReportImageException e) {
        ErrorDto errorDto = new ErrorDto("AlreadyReportImageException", "이미 신고한 이미지입니다.");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDto exHandler(Exception e) {
        return new ErrorDto("Exception", "서버에 오류가 발생했습니다.");
    }

}
