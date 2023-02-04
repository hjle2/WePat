package com.wepat.schedule.controller;

import com.wepat.notification.NotifiacationType;
import com.wepat.notification.NotificationDto;
import com.wepat.notification.service.NotificationService;
import com.wepat.schedule.ScheduleDto;
import com.wepat.schedule.service.ScheduleService;
import com.wepat.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final NotificationService notificationService;

    @GetMapping("/{calendarid}")
    @ApiOperation(value = "캘린더 메인페이지", notes = "캘린더에 해당 달에 대한 모든 스케쥴조회", response = ScheduleDto.class)
    public ResponseEntity<?> getScheduleByMonth(@PathVariable("calendarid") String calendarId) {
        return new ResponseEntity<>(scheduleService.getScheduleByCalendarId(calendarId), HttpStatus.OK);
    }

    @PostMapping("/add/{calendarid}")
    @ApiOperation(value = "일정 추가", notes = "일정 추가 버튼 클릭 시, 일정 추가", response = HttpStatus.class)
    public ResponseEntity<?> addSchedule(@PathVariable("calendarid") String calendarId,
                                         @RequestParam String nowDate,
                                         @RequestBody ScheduleDto scheduleDto,
                                         HttpServletRequest request) {

        // 알람 db에 추가 및 발생 처리
        String memberId = JwtUtil.getUserIdByHttpRequest(request);
        notificationService.addNotification(calendarId, memberId, scheduleDto.getScheduleId(), nowDate, NotifiacationType.ADD.ordinal());
        scheduleService.addSchedule(calendarId, scheduleDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/{calendarid}")
    @ApiOperation(value = "일정 확인", response = ScheduleDto.class)
    public ResponseEntity<?> getScheduleByDate(@PathVariable("calendarid") String calendarId,
                                               @RequestParam("nowDate") String nowDate) throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(scheduleService.getScheduleListByDate(calendarId, nowDate), HttpStatus.OK);
    }

    @GetMapping("/detail/{calendarid}/{scheduleid}")
    @ApiOperation(value = "세부 일정 확인", response = ScheduleDto.class)
    public ResponseEntity<?> getDetailScheduleByDate(@PathVariable("calendarid") String calendarId,
                                                     @PathVariable("scheduleid") String scheduleId) {
        try {
            scheduleService.getScheduleDetailByDate(calendarId, scheduleId);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/modify/{calendarid}/{scheduleid}")
    @ApiOperation(value = "일정 변경", response = HttpStatus.class)
    public ResponseEntity<?> modifySchedule(@PathVariable("calendarid") String calendarId,
                                            @PathVariable("scheduleid") String scheduleId,
                                            @RequestParam("nowDate") String nowDate,
                                            @RequestBody ScheduleDto scheduleDto,
                                            HttpServletRequest request) {
        try {

            String memberId = JwtUtil.getUserIdByHttpRequest(request);
            scheduleService.modifySchedule(calendarId, scheduleId, scheduleDto);
            notificationService.addNotification(calendarId, memberId, scheduleId, nowDate, NotifiacationType.MODIFY.ordinal());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/delete/{calendarid}")
    @ApiOperation(value = "일정 삭제", response = HttpStatus.class)
    public ResponseEntity<?> deleteSchedule(@PathVariable("calendarid") String calendarId,
                                            @RequestParam("scheduleid") String scheduleId) {
        scheduleService.deleteSchedule(calendarId, scheduleId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/complete/{calendarid}/{scheduleid}")
    @ApiOperation(value = "일정 완료", response = HttpStatus.class)
    public ResponseEntity<?> completeSchedule(@PathVariable("calendarid") String calendarId,
                                              @PathVariable("scheduleid") String scheduleId,
                                              @RequestParam("nowDate") String nowDate,
                                              HttpServletRequest request) {

        String memberId = JwtUtil.getUserIdByHttpRequest(request);
        scheduleService.deleteSchedule(calendarId, scheduleId);
        notificationService.addNotification(calendarId, memberId, scheduleId, nowDate, NotifiacationType.COMPLETED.ordinal());
        return ResponseEntity.ok().build();
    }
}
