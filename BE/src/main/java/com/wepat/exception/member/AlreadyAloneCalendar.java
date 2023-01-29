package com.wepat.exception.member;

public class AlreadyAloneCalendar extends RuntimeException {
    public AlreadyAloneCalendar() {
        super();
    }
    public AlreadyAloneCalendar(String message) {
        super(message);
    }
}
