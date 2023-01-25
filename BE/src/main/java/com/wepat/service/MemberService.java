package com.wepat.service;

import com.wepat.dto.MemberDto;
import com.wepat.entity.MemberEntity;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface MemberService {
    MemberDto signUp(MemberDto member) throws ExecutionException, InterruptedException;
    MemberDto signIn(String memberId, String pwd) throws ExecutionException, InterruptedException;
    MemberDto findId(String email) throws ExecutionException, InterruptedException;
    void findPwd(String memberId, String email) throws ExecutionException, InterruptedException, MessagingException;
    MemberDto modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException;
    MemberDto getMember(String memberId) throws ExecutionException, InterruptedException;
    MemberDto modifyMember(String memberId, String nickName) throws ExecutionException, InterruptedException;
    ResponseEntity<?> deleteMember(String memberId) throws ExecutionException, InterruptedException;
    MemberEntity logout(String memberId) throws ExecutionException, InterruptedException;

    ResponseEntity<?> addWarnMember(String memberId, String warnMemberId) throws ExecutionException, InterruptedException;
    // 관리자 기능
    List<String> warnMember() throws ExecutionException, InterruptedException;

    ResponseEntity<?> addBlockMember(String blockMemberId) throws ExecutionException, InterruptedException;

    //    MemberEntity blockMember(String memberId) throws ExecutionException, InterruptedException;

    String createJwt(String memberId, String pwd);
}
