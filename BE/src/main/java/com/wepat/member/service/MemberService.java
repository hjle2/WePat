package com.wepat.member.service;

import com.wepat.member.MemberDto;
import com.wepat.member.MemberEntity;

import javax.mail.MessagingException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface MemberService {
    void signUp(MemberDto member) throws ExecutionException, InterruptedException;
    MemberDto signIn(String memberId, String pwd) throws ExecutionException, InterruptedException;
    String findId(String email) throws ExecutionException, InterruptedException;
    void findPwd(String memberId, String email) throws ExecutionException, InterruptedException, MessagingException;
    void modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException;
    MemberDto getMember(String memberId) throws ExecutionException, InterruptedException;
    void modifyMember(String memberId, String nickName) throws ExecutionException, InterruptedException;
    void deleteMember(String memberId) throws ExecutionException, InterruptedException;
    void logout(String memberId) throws ExecutionException, InterruptedException;
    void modifyCalendarId(String memberId, String calendarId) throws ExecutionException, InterruptedException;
    String createJwt(String memberId, String pwd);


    MemberEntity addWarnMember(String memberId, String warnMemberId) throws ExecutionException, InterruptedException;

    MemberEntity addBlockMember(String memberId, String blockMemberId) throws ExecutionException, InterruptedException;

    public void saveRefreshToken(String memberId, String refreshToken) throws Exception;
    public String getRefreshToken(String memberId) throws Exception;
    public void deleteRefreshToken(String memberId) throws Exception;
}
