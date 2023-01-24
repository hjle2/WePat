package com.wepat.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class MemberEntity implements MemberDetails {
    @ApiParam(value = "사용자 PK", required = true)
    private String docId;
    @ApiParam(value = "사용자 ID", required = true)
    private String memberId;
    @ApiParam(value = "사용자 E-mail", required = true)
    private String email;
    @ApiParam(value = "사용자 이름", required = true)
    private String nickName;
    @ApiParam(value = "사용자 비밀번호", required = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pwd;
    @ApiParam(value = "사용자의 달력 ID")
    private String calendarId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getPassword() {
        return pwd;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getMemberName() {
        return memberId;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
