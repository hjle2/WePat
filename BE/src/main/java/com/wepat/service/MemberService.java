package com.wepat.service;

import com.wepat.dto.MemberDto;

import java.util.concurrent.ExecutionException;

public interface MemberService {
    MemberDto signUp(MemberDto member) throws ExecutionException, InterruptedException;
    MemberDto signIn(MemberDto member) throws ExecutionException, InterruptedException;
    MemberDto modify(MemberDto member) throws ExecutionException, InterruptedException;
    void delete(MemberDto member) throws ExecutionException, InterruptedException;
}
