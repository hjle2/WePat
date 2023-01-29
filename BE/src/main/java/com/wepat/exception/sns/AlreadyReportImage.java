package com.wepat.exception.sns;

public class AlreadyReportImage extends RuntimeException {
    public AlreadyReportImage() {
        super();
    }
    public AlreadyReportImage(String message) {
        super(message);
    }
}
