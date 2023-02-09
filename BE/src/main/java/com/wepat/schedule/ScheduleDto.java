package com.wepat.schedule;

import com.wepat.photo.CommentDto;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class ScheduleDto {
    private String calendarId;
    @ApiParam(value = "스케쥴 id", hidden = true)
    private String scheduleId;
    @ApiParam(value = "스케쥴 이름")
    private String title;
    @ApiParam(value = "카테고리")
    private String category;
    @ApiParam(value = "달력에 표시 여부")
    private boolean display;
    @ApiParam(value = "펫ID(PK)", required = true)
    private String petId;
    @ApiParam(value = "일정 시작일")
    private String startDate;
    @ApiParam(value = "일정 입력/수정/완료 처리 날짜")
    private String nowDate;
    @ApiParam(value = "일정 종료일")
    private String endDate;
    @ApiParam(value = "일정 반복 종료일")
    private String repeatEndDate;
    @ApiParam(value = "알람 여부")
    private int alarm;
    @ApiParam(value = "일정 예정자")
    private String whoPlanned;
    @ApiParam(value = "일정 완료자")
    private String whoCompleted;
    @ApiParam(value = "일정 수행 여부")
    private boolean completed = false;

    @ApiParam(value = "반복 주기")
    private int repeatUnit;
    @ApiParam(value = "일 or 달")
    private int repeatAmount;
    @ApiParam(value = "일정에 관한 메모")
    private String memo;
    @ApiParam(value = "사진ID", hidden = true)
    private String photoUrl;
    @ApiParam(value = "댓글Dto 리스트", hidden = true)
    private List<CommentDto> reviewList;
}
