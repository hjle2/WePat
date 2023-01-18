package com.wepat.service;

import com.wepat.dto.MemberDto;

import java.util.concurrent.ExecutionException;

public interface MemberService {
    MemberDto signUp(MemberDto member);
    MemberDto signIn(MemberDto member);
    MemberDto findId(String email);
    void findPwd();
    MemberDto getMember(String memberId);
    MemberDto modifyMember(MemberDto member);
    MemberDto deleteMember(String memberId);
    MemberDto logout(String memberId);

    // 관리자 기능
    MemberDto warnMember(String memberId);
    MemberDto blockMember(String memberId);
}
