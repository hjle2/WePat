package com.wepat.exception.member;

public class MessageError extends RuntimeException {
    public MessageError() {
        super();
    }
    public MessageError(String message) {
        super(message);
    }
}
