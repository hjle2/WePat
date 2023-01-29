package com.wepat.exception.calendar;

public class NotExistPet extends RuntimeException {
    public NotExistPet() {
        super();
    }
    public NotExistPet(String message) {
        super(message);
    }
}
