package com.wepat.schedule.repository;

import com.wepat.photo.CommentDto;
import com.wepat.schedule.ScheduleDto;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ScheduleRepository {
    // 현재 선택된 날짜 기준
    List<ScheduleDto> getScheduleByCalendarId(String calendarId);
    List<ScheduleDto> getScheduleListByDate(String calendarId, String startDate, String endDate) throws ExecutionException, InterruptedException;
    // 일정 추가
    String addSchedule(ScheduleDto scheduleDto);
    // 일정 변경
    void modifySchedule(String calendarId, String scheduleId, ScheduleDto scheduleDto) throws ExecutionException, InterruptedException;
    // 일정 삭제
    void deleteSchedule(String calendarId, String scheduleId);
    void completeSchedule(String calendarId, String scheduleId, String whoCompleted, boolean completed) throws ExecutionException, InterruptedException;
    // 일정 상세 정보 읽기
    ScheduleDto getScheduleByScheduleId(String calendarId, String scheduleId) throws ExecutionException, InterruptedException;
    // 캘린더 그룹 멤버 확인
    HashMap<String, String> getMemberListByCalendarId(String calendarId) throws ExecutionException, InterruptedException;

    void addCommentByScheduleId(String calendarId, String scheduleId, CommentDto commentDto) throws ExecutionException, InterruptedException;

    void modifyCommentByScheduleId(String calendarId, String scheduleId, String commentId, CommentDto commentDto) throws ExecutionException, InterruptedException;

    void deleteCommentByScheduleId(String calendarId, String scheduleId, String commentId) throws ExecutionException, InterruptedException;

    void modifyPhotoUrlByScheduleId(String calendarId, String scheduleId, String photoUrl) throws ExecutionException, InterruptedException;
}
