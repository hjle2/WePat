package com.wepat.repository;

import com.wepat.dto.ScheduleDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ScheduleRepository {
    // 전체 일정 가져오기 (달력에 표시할 일정이 있는 날짜들 가져오기)
    List<Timestamp> getAllSchedule(String calendarId) throws ExecutionException, InterruptedException ;
    // 일정 추가하기
    ScheduleDto addSchedule(ScheduleDto Schedule) throws ExecutionException, InterruptedException ;
    // 펫의 일정 가져오기 (달력에 표시할 일정이 있는 날짜들 가져오기)
    List<Timestamp> getScheduleByPet(String petId) throws ExecutionException, InterruptedException ;
    // 특정 날짜의 일정 가져오기 (특정 날짜의 일정들)
    List<ScheduleDto> getScheduleByDate(String CalendarId, Timestamp date) throws ExecutionException, InterruptedException ;
    // 일정 변경하기
    ScheduleDto modifySchedule(String calendarId, Timestamp writtenTime, ScheduleDto scheduleDto) throws ExecutionException, InterruptedException ;
}
