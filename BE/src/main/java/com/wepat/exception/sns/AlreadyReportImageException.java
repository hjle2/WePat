package com.wepat.exception.sns;

public class AlreadyReportImageException extends RuntimeException {
    public AlreadyReportImageException() {
        super();
    }
    public AlreadyReportImageException(String message) {
        super(message);
    }
}
