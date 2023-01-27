package com.wepat.exception.sns;

public class NotExistCalendarException extends RuntimeException{
    public NotExistCalendarException() {
        super();
    }
    public NotExistCalendarException(String message) {
        super(message);
    }
}
