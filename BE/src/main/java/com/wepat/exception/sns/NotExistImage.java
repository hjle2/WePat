package com.wepat.exception.sns;

public class NotExistImage extends RuntimeException {
    public NotExistImage() {
        super();
    }
    public NotExistImage(String message) {
        super(message);
    }
}
