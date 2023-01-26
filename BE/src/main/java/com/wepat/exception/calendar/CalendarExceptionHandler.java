package com.wepat.exception.calendar;

import com.wepat.controller.CalendarController;
import com.wepat.exception.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {CalendarController.class})
public class CalendarExceptionHandler {
    @ExceptionHandler(AlreadyFinishSchedule.class)
    public ResponseEntity<?> AlreadyFinishSchedule(AlreadyFinishSchedule e) {
        ErrorDto errorDto = new ErrorDto("AlreadyFinishSchedule", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
}
