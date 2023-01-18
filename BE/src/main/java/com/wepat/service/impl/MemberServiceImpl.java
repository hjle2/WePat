package com.wepat.service.impl;

import com.wepat.dto.MemberDto;
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
    public MemberDto signUp(MemberDto member) throws ExecutionException, InterruptedException {
        return memberRepo.signUp(member);
    }

    @Override
    public MemberDto signIn(String memberid, String pwd) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public MemberDto findId(String email) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public MemberDto modifyPwd(String memberId, String pwd) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public MemberDto getMember(String memberId) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public MemberDto modifyMember(MemberDto member) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public MemberDto deleteMember(String memberId) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public MemberDto logout(String memberId) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public MemberDto warnMember(String memberId) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public MemberDto blockMember(String memberId) throws ExecutionException, InterruptedException {
        return null;
    }
}
