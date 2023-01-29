package com.wepat.exception;

import org.apache.http.HttpStatus;

public class DataNotExitsException extends RuntimeException {

    private static final String msg = "변경 또는 삭제할 데이터가 존재하지 않습니다.";
    public static final int status = HttpStatus.SC_FAILED_DEPENDENCY;
    public DataNotExitsException() {
        super(msg);
    }
}
