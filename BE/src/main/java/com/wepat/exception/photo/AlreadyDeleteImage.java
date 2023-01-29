package com.wepat.exception.photo;

public class AlreadyDeleteImage extends RuntimeException {
    public AlreadyDeleteImage() {
        super();
    }
    public AlreadyDeleteImage(String message) {
        super(message);
    }
}
