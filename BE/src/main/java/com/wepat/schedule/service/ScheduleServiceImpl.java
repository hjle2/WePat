package com.wepat.schedule.service;

import com.wepat.notification.NotifiacationType;
import com.wepat.notification.NotificationDto;
import com.wepat.notification.repository.NotificationRepository;
import com.wepat.schedule.ScheduleDto;
import com.wepat.schedule.repository.ScheduleRepository;
import com.wepat.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final NotificationRepository notificationRepository;
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
    public void addSchedule(String calendarId, String memberId, String nowDate, ScheduleDto scheduleDto) throws ExecutionException, InterruptedException {

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
        scheduleDto.setScheduleId(memberId);
        scheduleDto.setNowDate(nowDate);

        if (scheduleDto.getRepeatUnit() > 0) {
            // startdate < enddate 인 경우
            for (int i=0; i<scheduleDto.getRepeatAmount(); i++) {
                scheduleDto.setStartDate(DateUtil.getStringDate(startDate));
                scheduleDto.setEndDate(DateUtil.getStringDate(endDate));
                String scheduleId = scheduleRepository.addSchedule(scheduleDto);

                // 알람 추가하기
                NotificationDto notificationDto = new NotificationDto("", scheduleId,
                        calendarId, memberId, nowDate, false, NotifiacationType.ADD.ordinal());
                notificationRepository.addNotification(notificationDto);
                // 반봅 주기만큼 더하기
                startDate = DateUtil.addDays(startDate, unit, size);
                endDate = DateUtil.addDays(endDate, unit, size);
            }
        } else {
            String scheduleId = scheduleRepository.addSchedule(scheduleDto);

            // 알람 추가하기
            NotificationDto notificationDto = new NotificationDto("", scheduleId,
                    calendarId, memberId, nowDate, false, NotifiacationType.ADD.ordinal());
            notificationRepository.addNotification(notificationDto);
        }
    }

    @Override
    public void modifySchedule(String calendarId, String scheduleId, String memberId, String nowDate, ScheduleDto scheduleDto) throws ExecutionException, InterruptedException {
        scheduleDto.setNowDate(nowDate);
        scheduleRepository.modifySchedule(calendarId, scheduleId, scheduleDto);

        // 알람 추가하기
        NotificationDto notificationDto = new NotificationDto("", scheduleId,
                calendarId, memberId, nowDate, false, NotifiacationType.MODIFY.ordinal());
        notificationRepository.addNotification(notificationDto);
    }

    @Override
    public void deleteSchedule(String calendarId, String scheduleId) {
        scheduleRepository.deleteSchedule(calendarId, scheduleId);
    }

    @Override
    public void compleateSchedule(String calendarId, String scheduleId) {
        scheduleRepository.completeSchedule(calendarId, scheduleId);
    }

    @Override
    public ScheduleDto getScheduleDetailByDate(String calendarId, String scheduleId) throws ExecutionException, InterruptedException {
        return scheduleRepository.getScheduleDetailByDate(calendarId, scheduleId);
    }
}
