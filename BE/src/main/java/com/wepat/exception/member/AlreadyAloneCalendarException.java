package com.wepat.exception.member;

public class AlreadyAloneCalendarException extends RuntimeException {
    public AlreadyAloneCalendarException() {
        super();
    }
    public AlreadyAloneCalendarException(String message) {
        super(message);
    }
}
