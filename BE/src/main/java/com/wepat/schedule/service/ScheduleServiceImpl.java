package com.wepat.schedule.service;

import com.wepat.notification.NotifiacationType;
import com.wepat.notification.NotificationDto;
import com.wepat.notification.repository.NotificationRepository;
import com.wepat.photo.CommentDto;
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

                ScheduleDto dto = new ScheduleDto();
                dto.setCalendarId(calendarId);
                dto.setTitle(scheduleDto.getTitle());
                dto.setCategory(scheduleDto.getCategory());
                dto.setDisplay(scheduleDto.isDisplay());
                dto.setPetId(scheduleDto.getPetId());
                dto.setStartDate(scheduleDto.getStartDate());
                dto.setNowDate(scheduleDto.getNowDate());
                dto.setEndDate(scheduleDto.getEndDate());
                dto.setRepeatEndDate(scheduleDto.getRepeatEndDate());
                dto.setAlarm(scheduleDto.getAlarm());
                dto.setWhoPlanned(scheduleDto.getWhoPlanned());
                dto.setWhoCompleted(scheduleDto.getWhoCompleted());
                dto.setCompleted(scheduleDto.isCompleted());
                dto.setRepeatUnit(scheduleDto.getRepeatUnit());
                dto.setRepeatAmount(scheduleDto.getRepeatAmount());
                dto.setMemo(scheduleDto.getMemo());
                dto.setPhotoUrl(scheduleDto.getPhotoUrl());
                dto.setReviewList(scheduleDto.getReviewList());

//                System.out.println(">>>>> " + startDate.toString());
//                System.out.println(">>>>> " + endDate.toString());
                dto.setStartDate(DateUtil.getStringDate(startDate));
                dto.setEndDate(DateUtil.getStringDate(endDate));
                scheduleId = scheduleRepository.addSchedule(dto);

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
        scheduleDto.setScheduleId(scheduleId);
        scheduleDto.setCalendarId(calendarId);

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

    @Override
    public void addCommentByScheduleId(String calendarId, String scheduleId, CommentDto commentDto) throws ExecutionException, InterruptedException {
        scheduleRepository.addCommentByScheduleId(calendarId, scheduleId, commentDto);
    }

    @Override
    public void modifyCommentByScheduleId(String calendarId, String scheduleId, String commentId, CommentDto commentDto) throws ExecutionException, InterruptedException {
        scheduleRepository.modifyCommentByScheduleId(calendarId, scheduleId, commentId, commentDto);
    }

    @Override
    public void deleteCommentByScheduleId(String calendarId, String scheduleId, String commentId) throws ExecutionException, InterruptedException {
        scheduleRepository.deleteCommentByScheduleId(calendarId, scheduleId, commentId);
    }

    @Override
    public void modifyPhotoUrlByScheduleId(String calendarId, String scheduleId, String photoUrl) throws ExecutionException, InterruptedException {
        scheduleRepository.modifyPhotoUrlByScheduleId(calendarId, scheduleId, photoUrl);
    }
}
