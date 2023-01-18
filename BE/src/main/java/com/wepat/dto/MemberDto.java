package com.wepat.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {
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
}
