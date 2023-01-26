package com.wepat.service;

import com.wepat.dto.MemberDto;
import com.wepat.entity.MemberEntity;

import javax.mail.MessagingException;
import java.util.concurrent.ExecutionException;

public interface MemberService {
    MemberEntity signUp(MemberDto member) throws ExecutionException, InterruptedException;
    MemberEntity signIn(String memberId, String pwd) throws ExecutionException, InterruptedException;
    MemberEntity findId(String email) throws ExecutionException, InterruptedException;
    MemberEntity modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException;
    MemberEntity getMember(String memberId) throws ExecutionException, InterruptedException;
    MemberEntity modifyMember(MemberDto member) throws ExecutionException, InterruptedException;
    MemberEntity deleteMember(String memberId) throws ExecutionException, InterruptedException;
    MemberEntity logout(String refreshToken) throws ExecutionException, InterruptedException;



    // 관리자 기능
    MemberEntity warnMember(String memberId) throws ExecutionException, InterruptedException;
    MemberEntity blockMember(String memberId) throws ExecutionException, InterruptedException;

    void findPwd(String memberId, String email) throws ExecutionException, InterruptedException, MessagingException;

    MemberEntity addWarnMember(String memberId, String warnMemberId) throws ExecutionException, InterruptedException;

    MemberEntity addBlockMember(String memberId, String blockMemberId) throws ExecutionException, InterruptedException;


    //JWT
    String createAccessToken(String memberId);

    String createRefreshToken(String memberId);

    String createJwt(String memberId, String subject, Long expireMs);

    void saveRefreshToken(String memberId, String refreshToken) throws ExecutionException, InterruptedException;

    String getRefreshToken(String memberId);
}
