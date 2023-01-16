package com.wepat.repository;

import com.wepat.dto.Member;

public interface MemberRepository {
    Member signUp(Member member);
    Member signIn(Member member);
    Member modify(Member member);
    void delete(Member member);
}
