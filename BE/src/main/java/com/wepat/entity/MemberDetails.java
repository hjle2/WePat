package com.wepat.entity;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public interface MemberDetails extends Serializable {
    Collection<? extends GrantedAuthority> getAuthorities();
    String getPassword();
    String getMemberId();
    String getMemberName();
    boolean isAccountNonLocked();
    boolean isCredentialNonExpired();
    boolean isEnabled();
}
