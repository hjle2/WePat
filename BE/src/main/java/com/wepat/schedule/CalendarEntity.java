package com.wepat.schedule;

import com.wepat.finance.FinanceDto;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEntity {
    public CalendarEntity(String memberId) {
        this.memberId = new ArrayList<>();
        this.memberId.add(memberId);
        this.petId = new ArrayList<>();
        this.financeList = new ArrayList<>();
    }
    @ApiParam(value = "사용자ID", required = true)
    private List<String> memberId;
    @ApiParam(value = "펫ID", required = true)
    private List<String> petId;
    @ApiParam(value = "가계부 내용 목록", required = true)
    private List<FinanceDto> financeList;
}
