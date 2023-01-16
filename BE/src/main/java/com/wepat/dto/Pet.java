package com.wepat.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Pet {
    private List<Schedule> schedule;
    private String name;
    private int age;
    private LocalDate birthday;
    private LocalDate adaptday;
    private List<Double> weightList;
}
