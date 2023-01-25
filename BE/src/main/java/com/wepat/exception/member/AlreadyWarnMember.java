package com.wepat.exception.member;

import javax.management.relation.RoleUnresolved;

public class AlreadyWarnMember extends RuntimeException {
    public AlreadyWarnMember() {
        super();
    }
    public AlreadyWarnMember(String message) {
        super(message);
    }
}
