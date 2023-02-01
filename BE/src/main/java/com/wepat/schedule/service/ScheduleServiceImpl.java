package com.wepat.schedule.service;

import com.wepat.schedule.ScheduleDto;
import com.wepat.schedule.repository.ScheduleRepository;
import com.wepat.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private static final int CALEDAR_DATE = 1;
    @Override
    public List<ScheduleDto> getScheduleByCalendarId(String calendarId) {
        return scheduleRepository.getScheduleByCalendarId(calendarId);
    }
    @Override
    public List<ScheduleDto> getScheduleListByDate(String calendarId, String date) throws ExecutionException, InterruptedException {
        Date startDate = DateUtil.getDate(date);
        Date endDate = DateUtil.addDays(startDate, CALEDAR_DATE, Calendar.DATE);

        startDate = DateUtil.setZeroTime(startDate);
        endDate = DateUtil.setZeroTime(endDate);
        return scheduleRepository.getScheduleListByDate(calendarId,DateUtil.getStringDate(startDate), DateUtil.getStringDate(endDate));
    }

    @Override
    public void addSchedule(String calendarId, ScheduleDto scheduleDto) {

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtil.getDate(scheduleDto.getStartDate());
            endDate = DateUtil.getDate(scheduleDto.getEndDate());
        } catch (Exception e) {
            throw new TypeNotPresentException("Date", e);
        }
        int unit = scheduleDto.getRepeatUnit();
        int size = scheduleDto.getRepeatAmount();

        scheduleDto.setCalendarId(calendarId);

        if (scheduleDto.getRepeatUnit() > 0) {
            // startdate < enddate 인 경우
            while (startDate.compareTo(endDate) <= 0) {
                scheduleDto.setStartDate(DateUtil.getStringDate(startDate));
                scheduleRepository.addSchedule(scheduleDto);

                // 반봅 주기만큼 더하기
                startDate = DateUtil.addDays(startDate, unit, size);
            }
        } else {
            scheduleRepository.addSchedule(scheduleDto);
        }
    }

    @Override
    public void modifySchedule(String calendarId, String scheduleId, ScheduleDto scheduleDto) throws ExecutionException, InterruptedException {
        scheduleRepository.modifySchedule(calendarId, scheduleId, scheduleDto);
    }

    @Override
    public void deleteSchedule(String calendarId, String scheduleId) {
        scheduleRepository.deleteSchedule(calendarId, scheduleId);
    }

    @Override
    public ScheduleDto getScheduleDetailByDate(String calendarId, String scheduleId) throws ExecutionException, InterruptedException {
        return scheduleRepository.getScheduleDetailByDate(calendarId, scheduleId);
    }
}
