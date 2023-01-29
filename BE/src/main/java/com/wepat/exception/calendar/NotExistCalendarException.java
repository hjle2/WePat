package com.wepat.exception.calendar;

public class NotExistCalendarException extends RuntimeException{
    public NotExistCalendarException() {
        super();
    }
    public NotExistCalendarException(String message) {
        super(message);
    }
}
