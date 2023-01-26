package com.wepat.exception.sns;

public class UploadMemberBlock extends RuntimeException {
    public UploadMemberBlock() {
        super();
    }
    public UploadMemberBlock(String message) {
        super(message);
    }
}
