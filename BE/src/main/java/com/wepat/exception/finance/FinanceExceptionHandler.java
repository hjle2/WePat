package com.wepat.exception.finance;

import com.wepat.controller.FinanceController;
import com.wepat.exception.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {FinanceController.class})
public class FinanceExceptionHandler {
    @ExceptionHandler(NotExistFinance.class)
    public ResponseEntity<?> NotExistFinance(NotExistFinance e) {
        ErrorDto errorDto = new ErrorDto("NotExistFinance", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(OverMoney.class)
    public ResponseEntity<?> OverMoney(OverMoney e) {
        ErrorDto errorDto = new ErrorDto("OverMoney", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
}
