package com.wepat.exception.member;

public class AlreadyWarnMember extends RuntimeException {
    public AlreadyWarnMember() {
        super();
    }
    public AlreadyWarnMember(String message) {
        super(message);
    }
}
