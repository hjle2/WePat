package com.wepat.repository;

import java.sql.Timestamp;
import java.util.List;

import com.wepat.dto.FinanceDto;
import com.wepat.dto.PetDto;
import com.wepat.dto.ScheduleDto;

public interface CalendarRepository {
    // 반려동물 추가하기
    PetDto addPet(String calendarId, PetDto pet);
    // 가계부 데이터 추가하기
    FinanceDto addFinance(String calendarId, FinanceDto finance);
}
