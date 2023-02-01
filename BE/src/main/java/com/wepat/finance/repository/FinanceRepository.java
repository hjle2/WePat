package com.wepat.finance.repository;

import com.wepat.finance.FinanceDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FinanceRepository {
    List<FinanceDto> getAllFinance(String calendarId) throws ExecutionException, InterruptedException;

    void addFinanceById(String calendarId, FinanceDto financeDto) throws ExecutionException, InterruptedException;

    FinanceDto getFinanceById(String calendarId, String financeId) throws ExecutionException, InterruptedException;

    void modifyFinanceById(String calendarId, String financeId, FinanceDto financeDto) throws ExecutionException, InterruptedException;

    void deleteFinanceById(String calendarId, String financeId) throws ExecutionException, InterruptedException;
}
