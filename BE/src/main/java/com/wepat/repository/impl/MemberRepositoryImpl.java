package com.wepat.repository.impl;

import com.wepat.dto.Member;
import com.wepat.repository.MemberRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    // file 설정을 외부로 뺄 수 있게
    public static final String COLLECTION_NAME = "member";
    @Override
    public Member signUp(Member member) {
        return member;
    }

    @Override
    public Member signIn(Member member) {
        return member;
    }

    @Override
    public Member modify(Member member) {
        return member;
    }

    @Override
    public void delete(Member member) {
    }
}
