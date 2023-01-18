package com.wepat.entity;

import com.wepat.dto.FinanceDto;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

@Data
public class CalendarEntity {
    @ApiParam(value = "달력ID", required = true)
    private String docId;
    @ApiParam(value = "펫ID", required = true)
    private List<String> petId;
    @ApiParam(value = "가계부 내용 목록", required = true)
    private List<FinanceDto> financeList;
}
