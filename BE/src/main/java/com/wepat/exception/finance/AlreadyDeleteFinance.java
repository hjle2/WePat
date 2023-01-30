package com.wepat.exception.finance;

public class AlreadyDeleteFinance extends RuntimeException {
    public AlreadyDeleteFinance() {
        super();
    }
    public AlreadyDeleteFinance(String message) {
        super(message);
    }
}
