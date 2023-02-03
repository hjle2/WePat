package com.wepat.schedule;

import com.wepat.member.MemberDto;
import com.wepat.finance.FinanceDto;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDto {
    @ApiParam(value = "펫ID")
    private List<String> petId;
    @ApiParam(value = "가계부 내용 목록")
    private List<FinanceDto> financeList;
    @ApiParam(value = "멤버")
    private MemberDto memberDto;
}
