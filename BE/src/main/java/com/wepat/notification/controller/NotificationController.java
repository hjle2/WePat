package com.wepat.notification.controller;

import com.wepat.notification.NotificationDto;
import com.wepat.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.concurrent.ExecutionException;

@RestController("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/read/{notificationid}")
    public ResponseEntity<?> readNotification(@PathVariable("notificationid") String notificationId) {
        notificationService.readNotification(notificationId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{notificationid}")
    public ResponseEntity<?> deleteNotification(@PathVariable("notificationid") String notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAll/{memberId}")
    public ResponseEntity<?> deleteAll(@PathVariable("memberId") String memberId) {
        notificationService.deleteAll(memberId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/readAll/{memberid}")
    public ResponseEntity<?> readlAll(@PathVariable("memberId") String memberId) {
        notificationService.readlAll(memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{notificationid}")
    public ResponseEntity<?> getScheduleIdByNotificationId(@PathVariable("notificationid") String notificationId) {
        try {
            notificationService.getScheduleIdByNotificationId(notificationId);
        } catch (ExecutionException e) {
            throw new InvalidParameterException();
        } catch (InterruptedException e) {
            throw new InvalidParameterException();
        }
        return ResponseEntity.ok().build();
    }
}
