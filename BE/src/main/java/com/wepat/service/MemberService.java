package com.wepat.service;

import com.wepat.dto.Member;

import java.util.concurrent.ExecutionException;

public interface MemberService {
    Member signUp(Member member) throws ExecutionException, InterruptedException;
    Member signIn(Member member) throws ExecutionException, InterruptedException;
    Member modify(Member member) throws ExecutionException, InterruptedException;
    void delete(Member member) throws ExecutionException, InterruptedException;
}
