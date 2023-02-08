package com.wepat.schedule.service;

import com.wepat.schedule.ScheduleDto;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ScheduleService {

    // 현재 선택된 날짜 기준
    List<ScheduleDto> getAllScheduleByCalendarId(String calendarId);
    List<ScheduleDto> getAllScheduleByDate(String calendarId, String date) throws ExecutionException, InterruptedException;
    // 일정 추가
    void addSchedule(String calendarId, String memberId, String nowDate, ScheduleDto scheduleDto) throws ExecutionException, InterruptedException;
    // 일정 변경
    void modifySchedule(String calendarId, String scheduleId, String memberId, String nowDate, ScheduleDto scheduleDto) throws ExecutionException, InterruptedException;
    // 일정 삭제
    void deleteSchedule(String calendarId, String scheduleId);
    void completeSchedule(String calendarId, String scheduleId, String whoCompleted, boolean completed) throws ExecutionException, InterruptedException;
    // 일정 상세 정보 읽기
    ScheduleDto getScheduleDetailByDate(String calendarId, String scheduleId) throws ExecutionException, InterruptedException;
    // 캘린더 그룹 멤버 확인
    HashMap<String, String> getMemberListByCalendarId(String calendarId) throws ExecutionException, InterruptedException;
}
