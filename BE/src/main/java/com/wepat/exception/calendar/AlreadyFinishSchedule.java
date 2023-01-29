package com.wepat.exception.calendar;

public class AlreadyFinishSchedule extends RuntimeException {
    public AlreadyFinishSchedule() {
        super();
    }
    public AlreadyFinishSchedule(String message) {
        super(message);
    }
}
