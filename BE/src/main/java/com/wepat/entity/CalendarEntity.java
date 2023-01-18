package com.wepat.entity;

import com.wepat.dto.FinanceDto;
import lombok.Data;

import java.util.List;

@Data
public class CalendarEntity {
    private String docId;
    private List<String> petId;
    private List<FinanceDto> financeList;
}
