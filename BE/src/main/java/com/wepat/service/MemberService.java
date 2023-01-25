package com.wepat.service;

import com.wepat.dto.MemberDto;
import com.wepat.entity.MemberDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.concurrent.ExecutionException;

public interface MemberService {
    MemberDto signUp(MemberDto member) throws ExecutionException, InterruptedException;
    MemberDto signIn(String memberId, String pwd) throws ExecutionException, InterruptedException;
    MemberDto findId(String email) throws ExecutionException, InterruptedException;
    MemberDto modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException;
    MemberDto getMember(String memberId) throws ExecutionException, InterruptedException;
    MemberDto modifyMember(MemberDto member) throws ExecutionException, InterruptedException;
    MemberDto deleteMember(String memberId) throws ExecutionException, InterruptedException;
    MemberDto logout(String memberId) throws ExecutionException, InterruptedException;
    String createJwt(String memberId, String pwd) throws ExecutionException, InterruptedException;
    // 관리자 기능
    MemberDto warnMember(String memberId) throws ExecutionException, InterruptedException;
    MemberDto blockMember(String memberId) throws ExecutionException, InterruptedException;

    // JWT
    MemberDetails loadMemberByMemberId(String memberId) throws UsernameNotFoundException;
}
