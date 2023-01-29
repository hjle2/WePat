package com.wepat.exception.pet;

public class NotExistCalendarException extends RuntimeException{
    public NotExistCalendarException() {
        super();
    }
    public NotExistCalendarException(String message) {
        super(message);
    }
}
