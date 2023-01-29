package com.wepat.schedule;

import com.wepat.photo.CommentDto;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class ScheduleDto {
    @ApiParam(value = "스케쥴 id", hidden = true)
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
    private boolean repeat;
    @ApiParam(value = "일정 시작일")
    private String date;
    @ApiParam(value = "일정 종료일")
    private String endDate;
    @ApiParam(value = "반복 주기")
    private int unit;
    @ApiParam(value = "일 or 달")
    private int unitSize;
    @ApiParam(value = "일정에 관한 메모")
    private String memo;
    @ApiParam(value = "사진ID 리스트", hidden = true)
    private List<String> photoList;
    @ApiParam(value = "댓글Dto 리스트", hidden = true)
    private List<CommentDto> reviewList;
}
