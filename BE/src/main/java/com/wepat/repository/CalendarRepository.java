package com.wepat.repository;

import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;

import com.wepat.dto.FinanceDto;
import com.wepat.dto.PetDto;

public interface CalendarRepository {
    // 반려동물 추가하기
    PetDto addPet(String calendarId, PetDto pet) throws ExecutionException, InterruptedException;
    // 반려동물 삭제하기
    PetDto deletePet(String calendarId, PetDto pet) throws ExecutionException, InterruptedException;
    // 가계부 데이터 추가하기
    FinanceDto addFinance(String calendarId, Timestamp writtendate) throws ExecutionException, InterruptedException;
    // 가계부 데이터 수정하기
    FinanceDto modifyFinance(String calendarId, Timestamp writtendate) throws ExecutionException, InterruptedException;
    // 가계부 데이터 삭제하기
    FinanceDto deleteFinance(String calendarId, Timestamp writtendate) throws ExecutionException, InterruptedException;
}
