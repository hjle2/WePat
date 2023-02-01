package com.wepat.exception.member;

public class NotExistMemberException extends RuntimeException {
    public NotExistMemberException() {
        super();
    }
    public NotExistMemberException(String message) {
        super(message);
    }
}
