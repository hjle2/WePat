package com.wepat.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ScheduleDto {
    private String title;
    private String petId;
    private boolean isRepeated;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp date;
    private boolean isCompleted = false;
    private int period;
    private int nPeriod;
    private double weight;
    private String memo;
    private List<String> photoList;
    private List<CommentDto> reviewList;
}
