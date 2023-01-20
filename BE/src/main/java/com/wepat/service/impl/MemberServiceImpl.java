package com.wepat.service.impl;

import com.wepat.dto.MemberDto;
import com.wepat.entity.MemberEntity;
import com.wepat.repository.MemberRepository;
import com.wepat.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepo;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Override
    public MemberEntity signUp(MemberDto member) throws ExecutionException, InterruptedException {
        return memberRepo.signUp(member);
    }

    @Override
    public MemberEntity signIn(String memberId, String pwd) throws ExecutionException, InterruptedException {
        return memberRepo.signIn(memberId, pwd);
    }

    @Override
    public MemberEntity findId(String email) throws ExecutionException, InterruptedException {
        return memberRepo.findId(email);
    }

    @Override
    public MemberEntity modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException {
        return memberRepo.modifyPwd(memberId, pwd);
    }

    @Override
    public MemberEntity getMember(String memberId) throws ExecutionException, InterruptedException {
        return memberRepo.getMember(memberId);
    }

    @Override
    public MemberEntity modifyMember(MemberDto member) throws ExecutionException, InterruptedException {
        return memberRepo.modifyMember(member);
    }

    @Override
    public MemberEntity deleteMember(String memberId) throws ExecutionException, InterruptedException {
        return memberRepo.deleteMember(memberId);
    }

    @Override
    public MemberEntity logout(String memberId) throws ExecutionException, InterruptedException {
        return memberRepo.logout(memberId);
    }

    @Override
    public MemberEntity warnMember(String memberId) throws ExecutionException, InterruptedException {
        return memberRepo.warnMember(memberId);
    }

    @Override
    public MemberEntity blockMember(String memberId) throws ExecutionException, InterruptedException {
        return memberRepo.blockMember(memberId);
    }
}
