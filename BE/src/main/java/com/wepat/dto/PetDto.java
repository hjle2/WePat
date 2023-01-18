package com.wepat.dto;

import lombok.Data;

import java.security.Timestamp;
import java.util.List;

@Data
public class PetDto {
    private String name;
    private int age;
    private Timestamp birthday;
    private Timestamp adaptday;
    private List<String> schedule;
    private List<WeightDto> weightList;
}
