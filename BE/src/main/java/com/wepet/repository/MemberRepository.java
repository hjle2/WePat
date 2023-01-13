package com.wepet.repository;

import com.wepet.dto.Member;

import java.util.List;

public interface MemberRepository {
    Member signUp(Member member);
    Member signIn(Member member);
    Member modify(Member member);
    void delete(Member member);
}
