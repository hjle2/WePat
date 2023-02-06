package com.wepat.notification.service;

import com.wepat.notification.NotificationDto;
import com.wepat.notification.repository.NotificationRepository;
import com.wepat.sse.SseEmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.List;

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


        NotificationDto notificationDto =
                new NotificationDto("", scheduleId, calendarId, memberId, date, false, type);
        String notificationId = notificationRepository.addNotification(notificationDto); // db에 notification을 저장하고 id값 반환
        notificationDto.setNotificationId(notificationId);
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
    public List<NotificationDto> getAllByMemberId(String memberId) throws ExecutionException, InterruptedException {
        return notificationRepository.getAllByMemberId(memberId);
    }

    @Override
    public NotificationDto getByNotificationId(String notificationId) throws ExecutionException, InterruptedException {
        return notificationRepository.getByNotificationId(notificationId);
    }

    @Override
    public int getCountByMemberId(String memberId) throws ExecutionException, InterruptedException {
        return notificationRepository.getCountByMemberId(memberId);
    }

    @Override
    public void readAllByMemberId(String memberId) throws ExecutionException, InterruptedException {
        notificationRepository.readAllByMemberId(memberId);
    }
}
