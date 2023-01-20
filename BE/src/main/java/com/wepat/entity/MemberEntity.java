package com.wepat.entity;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {
    public MemberEntity(String memberId) {
        this.memberId = memberId;
        this.warnMemberList = new ArrayList<>();
        this.blockMemberList = new ArrayList<>();
    }
    @ApiParam(value = "사용자 ID", required = true)
    private String memberId;
    @ApiParam(value = "사용자 비밀번호", required = true)
    private String pwd;
    @ApiParam(value = "사용자 이름", required = true)
    private String nickName;
    @ApiParam(value = "사용자 E-mail", required = true)
    private String email;
    @ApiParam(value = "사용자의 달력 ID")
    private String calendarId;
    @ApiParam(value = "신고한 회원 목록")
    private List<String> warnMemberList;
    @ApiParam(value = "차단한 계정")
    private List<String> blockMemberList;
}
