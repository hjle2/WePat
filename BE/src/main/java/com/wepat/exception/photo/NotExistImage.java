package com.wepat.exception.photo;

public class NotExistImage extends RuntimeException {
    public NotExistImage() {
        super();
    }
    public NotExistImage(String message) {
        super(message);
    }
}
