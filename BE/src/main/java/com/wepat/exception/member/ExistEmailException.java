package com.wepat.exception.member;

public class ExistEmailException extends RuntimeException{
    public ExistEmailException() {
        super();
    }
    public ExistEmailException(String message) {
        super(message);
    }
}
