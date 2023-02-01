package com.wepat.exception.photo;

public class NotExistImageException extends RuntimeException {
    public NotExistImageException() {
        super();
    }
    public NotExistImageException(String message) {
        super(message);
    }
}
