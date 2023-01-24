package com.wepat.controller;

import com.wepat.dto.ErrorDto;
import com.wepat.exception.ErrorPwd;
import com.wepat.exception.NoId;
import com.wepat.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> userException(UserException e) {
        log.error("[userException] e", e);
        ErrorDto errorDto = new ErrorDto("유저 ex", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoId.class)
    public ResponseEntity<?> noIdException(NoId e) {
        ErrorDto errorDto = new ErrorDto("NoId", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorPwd.class)
    public ResponseEntity<?> errorPwdException(ErrorPwd e) {
        ErrorDto errorDto = new ErrorDto("ErrorPwd", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDto exHandler(Exception e) {
        return new ErrorDto("Exception", "서버 내부 오류");
    }
}
