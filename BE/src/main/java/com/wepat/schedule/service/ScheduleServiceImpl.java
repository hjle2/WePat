package com.wepat.schedule.service;

import com.wepat.schedule.ScheduleDto;
import com.wepat.schedule.repository.ScheduleRepository;
import com.wepat.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    @Override
    public Map<String, List<String>> getScheduleByMonth(String calendarId, String date) {
        String startdate = DateUtil.getFirstDayOfMonth(date);
        String enddate = DateUtil.getFirstDayOfNextMonth(date);

        return scheduleRepository.getScheduleByMonth(calendarId, startdate, enddate);
    }

    @Override
    public List<ScheduleDto> getScheduleListByDate(String calendarId, String date) throws ExecutionException, InterruptedException {
        return scheduleRepository.getScheduleListByDate(calendarId, date);
    }

    @Override
    public void addSchedule(ScheduleDto scheduleDto) {
        Date startdate = DateUtil.getDate(scheduleDto.getDate());
        Date enddate = DateUtil.getDate(scheduleDto.getEndDate());

        int unit = scheduleDto.getUnit();
        int size = scheduleDto.getUnitSize();

        // startdate < enddate 인 경우
        while (startdate.compareTo(enddate) <= 0) {
            scheduleDto.setDate(DateUtil.getStringDate(startdate));
            scheduleRepository.addSchedule(scheduleDto);

            // 반봅 주기만큼 더하기
            startdate = DateUtil.addDays(startdate, unit, size);
        }
    }

    @Override
    public ScheduleDto getScheduleByDate(String calendarId, String date) {
        return null;
    }

    @Override
    public void modifySchedule(ScheduleDto scheduleDto, String date) {

    }

    @Override
    public void deleteSchedule(String calendarId, String date) {

    }

    @Override
    public ScheduleDto getScheduleDetailByDate(String calendarId, String date) {
        return null;
    }
}
