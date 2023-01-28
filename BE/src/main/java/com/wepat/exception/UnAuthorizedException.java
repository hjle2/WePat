package com.wepat.exception;

import org.apache.http.HttpStatus;

public class UnAuthorizedException extends RuntimeException {
    private static final long serialVersionUID = -2238030302650813813L;

    private static final String msg = "계정 권한이 유효하지 않습니다.\n다시 로그인을 해주세요.";
    public static final int status = HttpStatus.SC_UNAUTHORIZED;
    public UnAuthorizedException() {
        super(msg);
    }
}
