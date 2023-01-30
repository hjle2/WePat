package com.wepat.member.service;

import com.wepat.member.MemberDto;
import com.wepat.member.MemberEntity;

import javax.mail.MessagingException;
import java.util.concurrent.ExecutionException;

public interface MemberService {
    void signUp(MemberDto member) throws ExecutionException, InterruptedException;
    MemberDto signIn(String memberId, String pwd) throws ExecutionException, InterruptedException;
    MemberDto snsSignIn(String email,String id, String SNS)throws ExecutionException, InterruptedException;
    String findId(String email) throws ExecutionException, InterruptedException;
    void findPwd(String memberId, String email) throws ExecutionException, InterruptedException, MessagingException;
    void modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException;
    MemberDto getMemberDetail(String memberId) throws ExecutionException, InterruptedException;
    void modifyMemberDetail(String memberId, String nickName) throws ExecutionException, InterruptedException;
    void deleteMember(String memberId) throws ExecutionException, InterruptedException;
    void logout(String memberId) throws Exception;
    void modifyCalendarId(String memberId, String calendarId) throws ExecutionException, InterruptedException;

    void addWarnMember(String memberId, String warnMemberId) throws ExecutionException, InterruptedException;
    void addBlockMember(String memberId, String blockMemberId) throws ExecutionException, InterruptedException;

    void saveRefreshToken(String memberId, String refreshToken) throws Exception;

    void modifyCalendarIdAlone(String memberId) throws ExecutionException, InterruptedException;

}
