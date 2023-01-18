package com.wepat.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class PetEntity {
    private String docId;
    private List<ScheduleEntity> schedule;
    private String name;
    private int age;
    private Timestamp birthday;
    private Timestamp adaptday;
    private List<Double> weightList;
}
