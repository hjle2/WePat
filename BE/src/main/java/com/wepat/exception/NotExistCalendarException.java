package com.wepat.exception;

public class NotExistCalendarException extends RuntimeException{
    public NotExistCalendarException() {
        super();
    }
    public NotExistCalendarException(String message) {
        super(message);
    }
}
