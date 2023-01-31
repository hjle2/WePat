package com.wepat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseBody
    public ApiException handleBadRequestException(BadRequestException e){
        ApiException exception = new ApiException();
        exception.setErrorMessage(e.getMessage());
        exception.setStatusCode(HttpStatus.BAD_REQUEST.value());
        exception.setZonedDateTime(ZonedDateTime.now());
        return exception;
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseBody
    public ApiException handleUnAuthorizedException(UnAuthorizedException e){
        ApiException exception = new ApiException();
        exception.setErrorMessage(e.getMessage());
        exception.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        exception.setZonedDateTime(ZonedDateTime.now());
        return exception;
    }
}