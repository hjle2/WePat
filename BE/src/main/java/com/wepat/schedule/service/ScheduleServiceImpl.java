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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final NotificationRepository notificationRepository;
    private static final int CALEDAR_DATE = 1;
    @Override
    public List<ScheduleDto> getAllScheduleByCalendarId(String calendarId) {
        return scheduleRepository.getScheduleByCalendarId(calendarId);
    }
    @Override
    public List<ScheduleDto> getAllScheduleByDate(String calendarId, String date) throws ExecutionException, InterruptedException {
        Date startDate = DateUtil.getDate(date); // 날짜의 시작 시간 기준
        Date endDate = DateUtil.addDays(startDate, CALEDAR_DATE, Calendar.DATE); // 날짜의 끝 시간 기준

        startDate = DateUtil.setZeroTime(startDate);
        endDate = DateUtil.setZeroTime(endDate);
        return scheduleRepository.getScheduleListByDate(calendarId,DateUtil.getStringDate(startDate), DateUtil.getStringDate(endDate));
    }

    @Override
    public void addSchedule(String calendarId, String memberId, String nowDate, ScheduleDto scheduleDto) throws ExecutionException, InterruptedException {

        Date startDate = null;
        Date endDate = null;
        Date repeatEndDate = null;
        try {
            startDate = DateUtil.getDate(scheduleDto.getStartDate());
            endDate = DateUtil.getDate(scheduleDto.getEndDate());
            repeatEndDate = DateUtil.getDate(scheduleDto.getRepeatEndDate());
        } catch (Exception e) {
            throw new TypeNotPresentException("Date", e);
        }
        int unit = scheduleDto.getRepeatUnit();
        int size = scheduleDto.getRepeatAmount();

        scheduleDto.setCalendarId(calendarId);
        scheduleDto.setScheduleId(memberId);
        scheduleDto.setNowDate(nowDate);
        String scheduleId = null;

        if (scheduleDto.getRepeatUnit() > 0) {
            // endDate < repeatEndDate 인 경우
            while (endDate.compareTo(repeatEndDate) < 0) {
                scheduleDto.setStartDate(DateUtil.getStringDate(startDate));
                scheduleDto.setEndDate(DateUtil.getStringDate(endDate));
                scheduleId = scheduleRepository.addSchedule(scheduleDto);
                // 반봅 주기만큼 더하기
                startDate = DateUtil.addDays(startDate, unit, size);
                endDate = DateUtil.addDays(endDate, unit, size);
            }
        } else {
            scheduleId = scheduleRepository.addSchedule(scheduleDto);
        }
        // 알람 추가하기
        NotificationDto notificationDto = new NotificationDto("", scheduleId,
                calendarId, memberId, nowDate, false, NotifiacationType.ADD.ordinal());
        notificationRepository.addNotification(notificationDto);
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
    public void completeSchedule(String calendarId, String scheduleId, String whoCompleted, boolean completed) throws ExecutionException, InterruptedException {
        scheduleRepository.completeSchedule(calendarId, scheduleId, whoCompleted, completed);

    }

    @Override
    public ScheduleDto getScheduleDetailByDate(String calendarId, String scheduleId) throws ExecutionException, InterruptedException {
        return scheduleRepository.getScheduleByScheduleId(calendarId, scheduleId);
    }

    @Override
    public HashMap<String, String> getMemberListByCalendarId(String calendarId) throws ExecutionException, InterruptedException {
        return scheduleRepository.getMemberListByCalendarId(calendarId);
    }
}
