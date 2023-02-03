package com.wepat.calendar.service;

import com.wepat.calendar.repository.CalendarRepository;
import com.wepat.schedule.ScheduleDto;
import com.wepat.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService{
    private final CalendarRepository calendarRepository;
    @Override
    public Map<String, List<String>> getScheduleByMonth(String calendarId, String date) {
        String startdate = DateUtil.getFirstDayOfMonth(date);
        String enddate = DateUtil.getFirstDayOfNextMonth(date);

        return calendarRepository.getScheduleByMonth(calendarId, startdate, enddate);
    }

    @Override
    public List<ScheduleDto> getScheduleListByDate(String calendarId, String date) throws ExecutionException, InterruptedException {
        return calendarRepository.getScheduleListByDate(calendarId, date);
    }

    @Override
    public void addSchedule(ScheduleDto scheduleDto) {
        Date startdate = DateUtil.getDate(scheduleDto.getStartDate());
        Date enddate = DateUtil.getDate(scheduleDto.getEndDate());

        int unit = scheduleDto.getRepeatUnit();
        int size = scheduleDto.getRepeatUnit();

        // startdate < enddate 인 경우
        if (scheduleDto.getRepeatUnit() > 0) {
            while (startdate.compareTo(enddate) < 0) {
                scheduleDto.setStartDate(DateUtil.getStringDate(startdate));
                calendarRepository.addSchedule(scheduleDto);

                // 반봅 주기만큼 더하기
                startdate = DateUtil.addDays(startdate, unit, size);
            }
        } else {
            calendarRepository.addSchedule(scheduleDto);
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
