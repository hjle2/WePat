package com.wepat.exception.pet;

public class AlreadyDeletePet extends RuntimeException {
    public AlreadyDeletePet() {
        super();
    }
    public AlreadyDeletePet(String message) {
        super(message);
    }
}
