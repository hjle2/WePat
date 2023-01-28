package com.wepat.calendar.service;

import com.wepat.schedule.ScheduleDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CalendarService {
    // 현재 선택된 날짜 기준
    Map<String, List<String>> getScheduleByMonth(String calendarId, String date);
    List<ScheduleDto> getScheduleListByDate(String calendarId, String date);
    // 일정 추가
    void addSchedule(ScheduleDto scheduleDto);
    ScheduleDto getScheduleByDate(String calendarId, String date);
    // 일정 변경
    void modifySchedule(ScheduleDto scheduleDto, String date);
    // 일정 삭제
    void deleteSchedule(String calendarId, String date);
    // 일정 상세 정보 읽기
    ScheduleDto getScheduleDetailByDate(String calendarId, String date);
}
