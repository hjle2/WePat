package com.wepat.exception.finance;

public class NotExistCalendarException extends RuntimeException{
    public NotExistCalendarException() {
        super();
    }
    public NotExistCalendarException(String message) {
        super(message);
    }
}
