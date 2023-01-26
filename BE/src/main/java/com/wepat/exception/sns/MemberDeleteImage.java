package com.wepat.exception.sns;

public class MemberDeleteImage extends RuntimeException {
    public MemberDeleteImage() {
        super();
    }
    public MemberDeleteImage(String message) {
        super(message);
    }
}
