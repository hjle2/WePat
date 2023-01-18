package com.wepat.service.impl;

import com.wepat.dto.ScheduleDto;

import java.sql.Timestamp;
import java.util.List;

public interface ScheduleService {
    // 전체 일정 가져오기
    List<Timestamp> getAllSchedule(String calendarId);
    // 일정 추가하기
    ScheduleDto addSchedule(ScheduleDto Schedule);
    // 펫의 일정 가져오기
    List<Timestamp> getScheduleByPet(String petId);
    // 특정 날짜의 일정 가져오기
    List<ScheduleDto> getScheduleByDate(String CalendarId, Timestamp date);
    // 일정 변경하기
    ScheduleDto modifySchedule(String calendarId, Timestamp writtenTime, ScheduleDto scheduleDto);
}
