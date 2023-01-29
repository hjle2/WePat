package com.wepat.exception.member;

public class BlockMember extends RuntimeException{
    public BlockMember() {
        super();
    }
    public BlockMember(String message) {
        super(message);
    }
}
