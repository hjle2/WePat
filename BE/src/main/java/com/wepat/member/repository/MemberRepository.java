package com.wepat.member.repository;


import com.wepat.member.MemberDto;
import com.wepat.member.MemberEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface MemberRepository {
    void signUpWithCalendar(MemberDto member) throws ExecutionException, InterruptedException;
    void signUp(MemberDto member) throws ExecutionException, InterruptedException;
    MemberDto signIn(String memberId, String pwd) throws ExecutionException, InterruptedException;
    String findId(String email) throws ExecutionException, InterruptedException;
    void findPwd(String randomPassword, String memberId) throws ExecutionException, InterruptedException;
    void modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException ;
    MemberDto getMember(String memberId) throws ExecutionException, InterruptedException;
    void modifyMember(String memberId, String nickName) throws ExecutionException, InterruptedException;
    void deleteMember(String memberId) throws ExecutionException, InterruptedException;
    void logout(String memberId) throws ExecutionException, InterruptedException;
    void modifyCalendarId(String memberId, String calendarId) throws ExecutionException, InterruptedException;

    MemberEntity addWarnMember(String memberId, String warnMemberId) throws ExecutionException, InterruptedException;

    MemberEntity addBlockMember(String memberId, String blockMemberId) throws ExecutionException, InterruptedException;
    public void saveRefreshToken(String memberId, String refreshToken) throws Exception;
    public String getRefreshToken(String memberId) throws Exception;
    public void deleteRefreshToken(String memberId) throws Exception;
}

