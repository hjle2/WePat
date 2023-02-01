package com.wepat.schedule;

import com.wepat.photo.CommentDto;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleEntity {
    @ApiParam(value = "스케쥴ID", required = true)
    private String scheduleId;
    @ApiParam(value = "스케쥴 이름")
    private String title;
    @ApiParam(value = "카테고리")
    private String category;
    @ApiParam(value = "달력에 표시 여부")
    private boolean show;
    @ApiParam(value = "펫ID(PK)", required = true)
    private String petId;
    @ApiParam(value = "반복여부")
    private boolean repeat = false;
    @ApiParam(value = "일정 시작일")
    private String startDate;
    @ApiParam(value = "일정 종료일")
    private String endDate;
    @ApiParam(value = "일정 작성일")
    private String date;
    @ApiParam(value = "일정 수행 여부")
    private boolean completed = false;
    @ApiParam(value = "반복 주기")
    private int unit;
    @ApiParam(value = "일 or 달")
    private int unitSize;
    @ApiParam(value = "몸무게")
    private double weight;
    @ApiParam(value = "일정에 관한 메모")
    private String memo;
    @ApiParam(value = "사진ID 리스트")
    private List<String> photoList;
    @ApiParam(value = "댓글Dto 리스트")
    private List<CommentDto> reviewList;
}
