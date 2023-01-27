package com.wepat.exception.photo;

public class NotExistPet extends RuntimeException {
    public NotExistPet() {
        super();
    }
    public NotExistPet(String message) {
        super(message);
    }
}
