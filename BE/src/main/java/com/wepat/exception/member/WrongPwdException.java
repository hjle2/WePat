package com.wepat.exception.member;

public class WrongPwdException extends RuntimeException {
    public WrongPwdException() {
        super();
    }
    public WrongPwdException(String message) {
        super(message);
    }
}
