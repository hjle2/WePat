package com.wepat.exception.member;

public class IdWriteException extends RuntimeException {
    public IdWriteException() {
        super();
    };
    public IdWriteException(String message) {
        super(message);
    }
}
