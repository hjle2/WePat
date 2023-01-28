package com.wepat.repository;


import com.wepat.dto.MemberDto;
import org.springframework.http.ResponseEntity;

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


//    ResponseEntity<?> addWarnMember(String memberId, String warnMemberId) throws ExecutionException, InterruptedException;
//     관리자 기능
//    List<String> warnMember() throws ExecutionException, InterruptedException;
//    ResponseEntity<?> addBlockMember(String blockMemberId) throws ExecutionException, InterruptedException;
//    MemberEntity blockMember(String memberId) throws ExecutionException, InterruptedException;

}

