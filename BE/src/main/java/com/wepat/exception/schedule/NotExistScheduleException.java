package com.wepat.exception.schedule;

public class NotExistScheduleException extends RuntimeException {
    public NotExistScheduleException() {
        super();
    }
    public NotExistScheduleException(String message) {
        super(message);
    }
}
