package com.wepat.exception;

public class ErrorPwd extends RuntimeException {
    public ErrorPwd() {
        super();
    }
    public ErrorPwd(String message) {
        super(message);
    }
}
