package com.wepat.notification.service;

import com.wepat.notification.NotificationDto;
import com.wepat.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    @Override
    public void addNotification(NotificationDto notificationDto) {
        notificationRepository.addNotification(notificationDto);
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
