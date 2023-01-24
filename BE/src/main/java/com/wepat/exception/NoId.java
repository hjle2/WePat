package com.wepat.exception;

public class NoId extends RuntimeException {
    public NoId() {
        super();
    };
    public NoId(String message) {
        super(message);
    }
}
