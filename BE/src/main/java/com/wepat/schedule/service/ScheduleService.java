package com.wepat.schedule.service;

import com.wepat.schedule.ScheduleDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ScheduleService {

    // 현재 선택된 날짜 기준
    Map<String, List<String>> getScheduleByMonth(String calendarId, String date);
    List<ScheduleDto> getScheduleListByDate(String calendarId, String date) throws ExecutionException, InterruptedException;
    // 일정 추가
    void addSchedule(ScheduleDto scheduleDto);
    // 일정 변경
    void modifySchedule(ScheduleDto scheduleDto, String date);
    // 일정 삭제
    void deleteSchedule(String calendarId, String date);
    // 일정 상세 정보 읽기
    ScheduleDto getScheduleDetailByDate(String calendarId, String scheduleId);}
