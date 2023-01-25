package com.wepat.service.impl;

import com.wepat.dto.ScheduleDto;
import com.wepat.repository.MemberRepository;
import com.wepat.repository.ScheduleRepository;
import com.wepat.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepo;

    @Override
    public List<Timestamp> getAllSchedule(String calendarId) throws ExecutionException, InterruptedException {
        return scheduleRepo.getAllSchedule(calendarId);
    }

    @Override
    public ScheduleDto addSchedule(ScheduleDto Schedule) throws ExecutionException, InterruptedException {
        return scheduleRepo.addSchedule(Schedule);
    }

    @Override
    public List<Timestamp> getScheduleByPet(String petId) throws ExecutionException, InterruptedException {
        return scheduleRepo.getScheduleByPet(petId);
    }

    @Override
    public List<ScheduleDto> getScheduleByDate(String CalendarId, Timestamp date) throws ExecutionException, InterruptedException {
        return scheduleRepo.getScheduleByDate(CalendarId, date);
    }

    @Override
    public ScheduleDto modifySchedule(String calendarId, Timestamp writtenTime, ScheduleDto scheduleDto) throws ExecutionException, InterruptedException {
        return scheduleRepo.modifySchedule(calendarId, writtenTime, scheduleDto);
    }
}
