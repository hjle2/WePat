package com.wepat.dto;

import com.wepat.photo.CommentDto;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class ScheduleDto {
    @ApiParam(value = "스케쥴 이름")
    private String title;
    @ApiParam(value = "카테고리")
    private String category;
    @ApiParam(value = "펫ID(PK)", required = true)
    private String petId;
    @ApiParam(value = "반복여부")
    private boolean repeat;
    @ApiParam(value = "일정 시작일")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @ApiParam(value = "일정 종료일")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
    @ApiParam(value = "일정 수행 날짜")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    @ApiParam(value = "일정 작성일")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate writtenDate;
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
    @ApiParam(value = "댓글 리스트")
    private List<CommentDto> commandList;
}
