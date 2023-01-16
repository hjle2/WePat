package com.wepat.service.impl;

import com.wepat.dto.Member;
import com.wepat.repository.MemberRepository;
import com.wepat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    @Override
    public Member signUp(Member member) throws ExecutionException, InterruptedException {
        return memberRepository.signUp(member);
    }

    @Override
    public Member signIn(Member member) throws ExecutionException, InterruptedException {
        return memberRepository.signIn(member);
    }

    @Override
    public Member modify(Member member) throws ExecutionException, InterruptedException {
        return memberRepository.modify(member);
    }

    @Override
    public void delete(Member member) throws ExecutionException, InterruptedException {
        memberRepository.delete(member);
    }
}
