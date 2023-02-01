package com.wepat.exception.pet;

public class AlreadyDeletePetException extends RuntimeException {
    public AlreadyDeletePetException() {
        super();
    }
    public AlreadyDeletePetException(String message) {
        super(message);
    }
}
