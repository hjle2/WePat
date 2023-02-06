package com.wepat.notification.service;

import com.wepat.notification.NotificationDto;
import com.wepat.notification.repository.NotificationRepository;
import com.wepat.sse.SseEmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final SseEmitterRepository sseEmitterRepository;
    @Override
    public void addNotification(String calendarId,
                                String memberId,
                                String scheduleId,
                                String date,
                                int type) throws ExecutionException, InterruptedException {

        NotificationDto notificationDto = NotificationDto.builder()
                .calendarId(calendarId)
                .scheduleId(scheduleId)
                .memberId(memberId)
                .date(date)
                .notificationType(type).build();

        String notificationId = notificationRepository.addNotification(notificationDto); // db에 notification을 저장하고 id값 반환
        notificationDto.setNotificationId(notificationId);
    }

    @Override
    public void readNotification(String notificationId) {
        notificationRepository.readNotification(notificationId);
    }

    @Override
    public void deleteNotification(String notificationId) {
        notificationRepository.deleteNotification(notificationId);
    }

    @Override
    public void deleteAll(String memberId) {
        notificationRepository.deleteAll(memberId);
    }

    @Override
    public void readlAll(String memberId) {
        notificationRepository.readlAll(memberId);
    }

    @Override
    public String getScheduleIdByNotificationId(String notificationId) throws ExecutionException, InterruptedException {
        return notificationRepository.getScheduleIdByNotificationId(notificationId);
    }
}
