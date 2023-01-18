package com.wepat.service;

import com.wepat.dto.FinanceDto;
import com.wepat.dto.PetDto;
import com.wepat.entity.CalendarEntity;

import java.util.concurrent.ExecutionException;

public interface CalendarService {
    // 반려동물 추가하기
    PetDto addPet(String calendarId, PetDto pet);
    // 가계부 데이터 추가하기
    FinanceDto addFinance(String calendarId, FinanceDto finance);
}
