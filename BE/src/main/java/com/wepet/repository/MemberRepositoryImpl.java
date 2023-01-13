package com.wepet.repository;

import com.wepet.dto.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    // file 설정을 외부로 뺄 수 있게
    public static final String COLLECTION_NAME = "testdb";
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
