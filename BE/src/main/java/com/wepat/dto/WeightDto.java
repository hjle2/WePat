package com.wepat.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class WeightDto {
    private String petId;
    private double weight;
    private Timestamp date;
}
