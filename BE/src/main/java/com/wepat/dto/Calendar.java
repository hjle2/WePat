package com.wepat.dto;

import lombok.Data;

import java.util.List;

@Data
public class Calendar {
    private List<String> petId;
    private List<Finance> financeList;
}
