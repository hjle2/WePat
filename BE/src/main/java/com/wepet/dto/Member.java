package com.wepet.dto;

//import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiParam;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {
    @ApiParam(value = "사용자 ID", required = true)
    private String id;
    @ApiParam(value = "사용자 이름", required = true)
    private String nickName;
    @ApiParam(value = "사용자 비밀번호", required = true)
    private String password;
}
