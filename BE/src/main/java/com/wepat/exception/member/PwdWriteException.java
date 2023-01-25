package com.wepat.exception.member;

public class PwdWriteException extends RuntimeException {
    public PwdWriteException() {
        super();
    }
    public PwdWriteException(String message) {
        super(message);
    }
}
