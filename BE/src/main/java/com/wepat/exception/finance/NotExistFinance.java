package com.wepat.exception.finance;

public class NotExistFinance extends RuntimeException {
    public NotExistFinance() {
        super();
    }
    public NotExistFinance(String message) {
        super(message);
    }
}
