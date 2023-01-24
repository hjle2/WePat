package com.wepat.exception.member;

import com.wepat.exception.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(IdWriteException.class)
    public ResponseEntity<?> noIdException(IdWriteException e) {
        ErrorDto errorDto = new ErrorDto("NoId", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PwdWriteException.class)
    public ResponseEntity<?> errorPwdException(PwdWriteException e) {
        ErrorDto errorDto = new ErrorDto("ErrorPwd", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDto exHandler(Exception e) {
        return new ErrorDto("Exception", "서버 내부 오류");
    }
}
