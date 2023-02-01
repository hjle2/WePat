package com.wepat.exception.photo;

public class AlreadyDeleteImageException extends RuntimeException {
    public AlreadyDeleteImageException() {
        super();
    }
    public AlreadyDeleteImageException(String message) {
        super(message);
    }
}
