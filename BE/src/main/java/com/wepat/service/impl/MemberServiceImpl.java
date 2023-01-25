package com.wepat.service.impl;

import com.wepat.dto.MemberDto;
import com.wepat.entity.MemberDetails;
import com.wepat.repository.MemberRepository;
import com.wepat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utils.JwtUtil;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepo;

    @Override
    public MemberDto signUp(MemberDto member) throws ExecutionException, InterruptedException {
        return memberRepo.signUp(member);
    }

    @Override
    public MemberDto signIn(String memberId, String pwd) throws ExecutionException, InterruptedException {
        return memberRepo.signIn(memberId, pwd);
    }

    @Override
    public MemberDto findId(String email) throws ExecutionException, InterruptedException {
        return memberRepo.findId(email);
    }

    @Override
    public MemberDto modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException {
        return memberRepo.modifyPwd(memberId, pwd);
    }

    @Override
    public MemberDto getMember(String memberId) throws ExecutionException, InterruptedException {
        return memberRepo.getMember(memberId);
    }

    @Override
    public MemberDto modifyMember(MemberDto member) throws ExecutionException, InterruptedException {
        return memberRepo.modifyMember(member);
    }

    @Override
    public MemberDto deleteMember(String memberId) throws ExecutionException, InterruptedException {
        return memberRepo.deleteMember(memberId);
    }

    @Override
    public MemberDto logout(String memberId) throws ExecutionException, InterruptedException {
        return memberRepo.logout(memberId);
    }

    @Override
    public String createJwt(String memberId, String pwd) throws ExecutionException, InterruptedException {
        Long expireMs = 1000 * 60 * 60L;
        return JwtUtil.createJwt(memberId,expireMs);
    }

    @Override
    public MemberDto warnMember(String memberId) throws ExecutionException, InterruptedException {
        return memberRepo.warnMember(memberId);
    }

    @Override
    public MemberDto blockMember(String memberId) throws ExecutionException, InterruptedException {
        return memberRepo.blockMember(memberId);
    }

    @Override
    public MemberDetails loadMemberByMemberId(String memberId) throws UsernameNotFoundException {
        return null;
    }
}
