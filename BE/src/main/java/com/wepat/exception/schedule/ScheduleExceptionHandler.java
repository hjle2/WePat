package com.wepat.exception.schedule;

import com.wepat.exception.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ScheduleExceptionHandler {

    @ExceptionHandler(NotExistScheduleException.class)
    public ResponseEntity<?> NotExistCalendarException(NotExistScheduleException e) {
        ErrorDto errorDto = new ErrorDto("NotExistScheduleException", "존재하지 않는 일정입니다.");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDto exHandler(Exception e) {
        return new ErrorDto("Exception", "서버에 오류가 발생했습니다. 죄송합니다.");
    }
}
