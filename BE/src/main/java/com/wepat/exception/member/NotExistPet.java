package com.wepat.exception.member;

public class NotExistPet extends RuntimeException {
    public NotExistPet() {
        super();
    }
    public NotExistPet(String message) {
        super(message);
    }
}
