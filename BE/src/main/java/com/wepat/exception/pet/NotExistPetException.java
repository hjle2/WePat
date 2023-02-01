package com.wepat.exception.pet;

public class NotExistPetException extends RuntimeException {
    public NotExistPetException() {
        super();
    }
    public NotExistPetException(String message) {
        super(message);
    }
}
