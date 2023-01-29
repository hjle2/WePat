package com.wepat.exception.pet;

public class NotExistPet extends RuntimeException {
    public NotExistPet() {
        super();
    }
    public NotExistPet(String message) {
        super(message);
    }
}
