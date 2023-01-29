package com.wepat.exception.finance;

public class OverMoney extends RuntimeException {
    public OverMoney() {
        super();
    }
    public OverMoney(String message) {
        super(message);
    }
}
