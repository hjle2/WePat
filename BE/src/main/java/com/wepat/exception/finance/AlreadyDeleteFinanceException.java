package com.wepat.exception.finance;

public class AlreadyDeleteFinanceException extends RuntimeException {
    public AlreadyDeleteFinanceException() {
        super();
    }
    public AlreadyDeleteFinanceException(String message) {
        super(message);
    }
}
