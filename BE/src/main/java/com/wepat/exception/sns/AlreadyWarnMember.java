package com.wepat.exception.sns;

public class AlreadyWarnMember extends RuntimeException {
    public AlreadyWarnMember() {
        super();
    }
    public AlreadyWarnMember(String message) {
        super(message);
    }
}
