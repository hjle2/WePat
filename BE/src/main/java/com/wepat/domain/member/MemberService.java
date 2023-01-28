package com.wepat.domain.member;

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
    MemberEntity logout(String memberId) throws ExecutionException, InterruptedException;

    // 관리자 기능
    MemberEntity warnMember(String memberId) throws ExecutionException, InterruptedException;
    MemberEntity blockMember(String memberId) throws ExecutionException, InterruptedException;

    void findPwd(String memberId, String email) throws ExecutionException, InterruptedException, MessagingException;

    MemberEntity addWarnMember(String memberId, String warnMemberId) throws ExecutionException, InterruptedException;

    MemberEntity addBlockMember(String memberId, String blockMemberId) throws ExecutionException, InterruptedException;

    public void saveRefreshToken(String userid, String refreshToken) throws Exception;
    public Object getRefreshToken(String userid) throws Exception;
    public void deleRefreshToken(String userid) throws Exception;
}
