package com.wepat.exception.member;

public class NotExistMember extends RuntimeException {
    public NotExistMember() {
        super();
    }
    public NotExistMember(String message) {
        super(message);
    }
}
