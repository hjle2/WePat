package com.wepat.exception;

import org.apache.http.HttpStatus;

public class TokenExpiredException extends RuntimeException{
    private static final String msg = "Access Token 이 만료되었습니다.\n재발급을 진행해주세요.";
    public static final int status = HttpStatus.SC_GONE;
    public TokenExpiredException() {
        super(msg);
    }
}
