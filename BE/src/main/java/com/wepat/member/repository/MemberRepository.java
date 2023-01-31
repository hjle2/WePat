package com.wepat.member.repository;


import com.wepat.member.MemberDto;

import java.util.concurrent.ExecutionException;

public interface MemberRepository {
    void signUpWithCalendar(MemberDto member) throws ExecutionException, InterruptedException;
    void signUp(MemberDto member) throws ExecutionException, InterruptedException;
    void socialsignup(MemberDto member)throws ExecutionException, InterruptedException;

    MemberDto signIn(String memberId, String pwd) throws ExecutionException, InterruptedException;
    MemberDto socialsignin( String id, int sns)throws ExecutionException, InterruptedException;

    String findId(String email) throws ExecutionException, InterruptedException;
    void changePwdToRandom(String randomPassword, String memberId) throws ExecutionException, InterruptedException;
    void modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException ;
    MemberDto getMemberById(String memberId) throws ExecutionException, InterruptedException;
    void modifyMember(String memberId, String nickName) throws ExecutionException, InterruptedException;
    void deleteMember(String memberId) throws ExecutionException, InterruptedException;
    void modifyCalendarId(String memberId, String calendarId) throws ExecutionException, InterruptedException;

    void addWarnMember(String memberId, String warnMemberId) throws ExecutionException, InterruptedException;
    void addBlockMember(String memberId, String blockMemberId) throws ExecutionException, InterruptedException;
    void saveRefreshToken(String memberId, String refreshToken) throws Exception;
    void deleteRefreshToken(String memberId) throws Exception;

    void modifyCalendarIdAlone(String memberId) throws ExecutionException, InterruptedException;



}

