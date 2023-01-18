package com.wepat.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ScheduleDto {
    @ApiParam(value = "스케쥴 이름")
    private String title;
    @ApiParam(value = "펫ID(PK)", required = true)
    private String petId;
    @ApiParam(value = "반복여부")
    private boolean isRepeated;
    @ApiParam(value = "일정 시작일")
    private Timestamp startDate;
    @ApiParam(value = "일정 종료일")
    private Timestamp endDate;
    @ApiParam(value = "일정 작성일")
    private Timestamp writtenDate;
    @ApiParam(value = "일정 수행 여부")
    private boolean isCompleted = false;
    @ApiParam(value = "반복 주기")
    private int period;
    @ApiParam(value = "반복 횟수")
    private int nPeriod;
    @ApiParam(value = "몸무게")
    private double weight;
    @ApiParam(value = "일정에 관한 메모")
    private String memo;
    @ApiParam(value = "사진ID 리스트")
    private List<String> photoList;
    @ApiParam(value = "댓글Dto 리스트")
    private List<CommentDto> reviewList;
}
