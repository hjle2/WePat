package com.wepat.exception.member;

public class ExistIdException extends RuntimeException{
    public ExistIdException() {
        super();
    }
    public ExistIdException(String message) {
        super(message);
    }
}
