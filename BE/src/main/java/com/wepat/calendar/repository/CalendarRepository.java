package com.wepat.calendar.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.wepat.pet.PetDto;
import com.wepat.finance.FinanceDto;
import com.wepat.schedule.ScheduleDto;

public interface CalendarRepository {
    // 현재 선택된 날짜 기준
    Map<String, List<String>> getScheduleByMonth(String calendarId, String startDate, String endDate);
    List<ScheduleDto> getScheduleListByDate(String calendarId, String date) throws ExecutionException, InterruptedException;
    // 일정 추가
    void addSchedule(ScheduleDto scheduleDto);
    ScheduleDto getScheduleByDate(String calendarId, String date);
    // 일정 변경
    void modifySchedule(String calendarId, ScheduleDto scheduleDto, String date);
    // 일정 삭제
    void deleteSchedule(String calendarId, String date);
    // 일정 상세 정보 읽기
    ScheduleDto getScheduleDetailByDate(String calendarId, String date);
}
