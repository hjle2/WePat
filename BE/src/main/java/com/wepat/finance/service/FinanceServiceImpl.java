package com.wepat.finance.service;

import com.wepat.finance.FinanceDto;
import com.wepat.finance.repository.FinanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FinanceServiceImpl implements FinanceService{

    private final FinanceRepository financeRepository;

    @Override
    public List<FinanceDto> getAllFinance(String calendarId) throws ExecutionException, InterruptedException {
        return financeRepository.getAllFinance(calendarId);
    }

    @Override
    public void addFinanceById(String calendarId, FinanceDto financeDto) throws ExecutionException, InterruptedException {
        financeRepository.addFinanceById(calendarId, financeDto);
    }

    @Override
    public FinanceDto getFinanceById(String calendarId, String financeId) throws ExecutionException, InterruptedException {
        return financeRepository.getFinanceById(calendarId, financeId);
    }

    @Override
    public void modifyFinanceById(String calendarId, String financeId, FinanceDto financeDto) throws ExecutionException, InterruptedException {
        financeRepository.modifyFinanceById(calendarId, financeId, financeDto);
    }

    @Override
    public void deleteFinanceById(String calendarId, String financeId) throws ExecutionException, InterruptedException {
        financeRepository.deleteFinanceById(calendarId, financeId);
    }

}
