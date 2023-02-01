package com.wepat.exception.sns;

public class NotExistImageException extends RuntimeException {
    public NotExistImageException() {
        super();
    }
    public NotExistImageException(String message) {
        super(message);
    }
}
