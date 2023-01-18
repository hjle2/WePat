package com.wepat.dto;

import lombok.Data;
import java.util.List;

@Data
public class CalendarDto {
    private List<String> petId;
    private List<FinanceDto> financeList;
}
