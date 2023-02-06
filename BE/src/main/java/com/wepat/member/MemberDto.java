package com.wepat.member;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
    @ApiParam(value = "CalendarId")
    private String calendarId;
    @ApiParam(value = "Social SNS")
    private int social = SOCIAL_ACCOUNT.NONE.ordinal();
    @ApiParam(value = "프로필 사진")
    private String photoUrl;
}
enum SOCIAL_ACCOUNT {
    NONE,
    GOOGLE,
    FACEBOOK,
    TWITTER,
    GITHUB,
}
