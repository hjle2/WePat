package com.wepat.repository;


import com.wepat.dto.MemberDto;

import java.util.concurrent.ExecutionException;

public interface MemberRepository {
    MemberDto signUp(MemberDto member) throws ExecutionException, InterruptedException;
    MemberDto signIn(String memberId, String pwd) throws ExecutionException, InterruptedException;
    MemberDto findId(String email) throws ExecutionException, InterruptedException;
    MemberDto modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException ;
    MemberDto getMember(String memberId) throws ExecutionException, InterruptedException;
    MemberDto modifyMember(MemberDto member) throws ExecutionException, InterruptedException;
    MemberDto deleteMember(String memberId) throws ExecutionException, InterruptedException;
    MemberDto logout(String memberId) throws ExecutionException, InterruptedException;

    // 관리자 기능
    MemberDto warnMember(String memberId) throws ExecutionException, InterruptedException;
    MemberDto blockMember(String memberId) throws ExecutionException, InterruptedException;

    void findPwd(String randomPassword, String memberId) throws ExecutionException, InterruptedException;
}
