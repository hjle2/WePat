package com.wepat.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import java.util.List;

@Data
public class CalendarDto {
    @ApiParam(value = "펫ID", required = true)
    private List<String> petId;
    @ApiParam(value = "가계부 내용 목록", required = true)
    private List<FinanceDto> financeList;
}
