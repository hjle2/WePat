package com.wepat.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Weight {
    private double weight;
    private Timestamp date;
}
