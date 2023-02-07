package com.wepat.member.service;

import com.wepat.member.MemberDto;
import com.wepat.member.MemberEntity;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

public interface MemberService {
    void signUp(MemberDto member) throws ExecutionException, InterruptedException;
    void socialSignUp(MemberDto member)throws ExecutionException, InterruptedException;
    MemberDto signIn(String memberId, String pwd) throws ExecutionException, InterruptedException;
    MemberDto socialSignIn(String memberId,int social) throws ExecutionException, InterruptedException;
    String findId(String email) throws ExecutionException, InterruptedException;
    void findPwd(String memberId, String email) throws ExecutionException, InterruptedException, MessagingException;
    void modifyPwdById(String memberId, String originPwd, String newPwd) throws ExecutionException, InterruptedException;
    MemberDto getMemberById(String memberId) throws ExecutionException, InterruptedException;
    void modifyMemberById(String memberId, String nickName) throws ExecutionException, InterruptedException;
    void modifyMemberPhotoById(String memberId, String photoUrl) throws ExecutionException, InterruptedException;
    void deleteMember(String memberId) throws ExecutionException, InterruptedException;
    void logout(String memberId) throws Exception;
    void modifyCalendarById(String memberId, String calendarId) throws ExecutionException, InterruptedException;

    void addWarnMember(String memberId, String warnMemberId) throws ExecutionException, InterruptedException;
    void addBlockMember(String memberId, String blockMemberId) throws ExecutionException, InterruptedException;

    void saveRefreshToken(String memberId, String refreshToken) throws Exception;

    void addCalendarById(String memberId) throws ExecutionException, InterruptedException;

}
