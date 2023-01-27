package com.wepat.calendar;

import com.wepat.finance.FinanceDto;
import com.wepat.member.MemberDto;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CalendarDto {
    @ApiParam(value = "펫ID")
    private List<String> petId;
    @ApiParam(value = "가계부 내용 목록")
    private List<FinanceDto> financeList;
    @ApiParam(value = "멤버")
    private MemberDto memberDto;
}
