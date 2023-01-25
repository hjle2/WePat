package com.wepat.exception.member;

public class NotExistEmailException extends RuntimeException{
    public NotExistEmailException() {
        super();
    }
    public NotExistEmailException(String message) {
        super(message);
    }
}
