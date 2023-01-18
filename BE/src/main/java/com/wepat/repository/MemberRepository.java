package com.wepat.repository;


import com.wepat.dto.MemberDto;

public interface MemberRepository {
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
