package com.wepat.notification.controller;

import com.wepat.notification.NotificationDto;
import com.wepat.notification.service.NotificationService;
import com.wepat.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidParameterException;
import java.util.concurrent.ExecutionException;
import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @DeleteMapping("/delete/{notificationid}")
    public ResponseEntity<?> deleteNotification(@PathVariable("notificationid") String notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll(HttpServletRequest request) {
        String memberId = JwtUtil.getUserIdByHttpRequest(request);
        notificationService.deleteAll(memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllByMemberId(HttpServletRequest request) {
        String memberId = JwtUtil.getUserIdByHttpRequest(request);
        try {
            List<NotificationDto> notificationDtoList = notificationService.getAllByMemberId(memberId);

            notificationService.readAllByMemberId(memberId);
            return new ResponseEntity<>(notificationDtoList, HttpStatus.OK);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get/{notificationid}")
    public ResponseEntity<?> getScheduleIdByNotificationId(@PathVariable("notificationid") String notificationId) {
        try {
            return new ResponseEntity<>(notificationService.getByNotificationId(notificationId), HttpStatus.OK);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCountByMemberId(HttpServletRequest request) {
        try {
            String memberId = JwtUtil.getUserIdByHttpRequest(request);
            return new ResponseEntity<>(notificationService.getCountByMemberId(memberId), HttpStatus.OK);
        } catch (ExecutionException e) {
            throw new InvalidParameterException();
        } catch (InterruptedException e) {
            throw new InvalidParameterException();
        }
    }
    @PutMapping("/readAll")
    public ResponseEntity<?> readAllByMemberId(HttpServletRequest request) {
        try {
            String memberId = JwtUtil.getUserIdByHttpRequest(request);
            notificationService.readAllByMemberId(memberId);
            return ResponseEntity.ok().build();
        } catch (ExecutionException e) {
            throw new InvalidParameterException();
        } catch (InterruptedException e) {
            throw new InvalidParameterException();
        }
    }
}
