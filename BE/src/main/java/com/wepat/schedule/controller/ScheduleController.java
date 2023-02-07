package com.wepat.schedule.controller;

import com.wepat.notification.NotifiacationType;
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

    // 캘린더의 메인페이지에서 모든 일정 데이터 얻기
    @GetMapping("/{calendarid}")
    @ApiOperation(value = "캘린더 메인페이지", notes = "캘린더에 해당 달에 대한 모든 스케쥴조회", response = ScheduleDto.class)
    public ResponseEntity<?> getAllScheduleByCalendarId(@PathVariable("calendarid") String calendarId) {
        return new ResponseEntity<>(scheduleService.getAllScheduleByCalendarId(calendarId), HttpStatus.OK);
    }

    // 일정 추가하기
    @PostMapping("/add/{calendarid}")
    @ApiOperation(value = "일정 추가", notes = "일정 추가 버튼 클릭 시, 일정 추가", response = HttpStatus.class)
    public ResponseEntity<?> addSchedule(@PathVariable("calendarid") String calendarId,
                                         @RequestParam String nowDate,
                                         @RequestBody ScheduleDto scheduleDto,
                                         HttpServletRequest request) throws ExecutionException, InterruptedException {

        // 알람 db에 추가 및 발생 처리
        String memberId = JwtUtil.getUserIdByHttpRequest(request);
        scheduleService.addSchedule(calendarId, memberId, nowDate, scheduleDto);
        return ResponseEntity.ok().build();
    }

    // 해당 날짜의 일정 확인
    @GetMapping("/list/{calendarid}")
    @ApiOperation(value = "일정 확인", response = ScheduleDto.class)
    public ResponseEntity<?> getAllScheduleByDate(@PathVariable("calendarid") String calendarId,
                                               @RequestParam("date") String date) throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(scheduleService.getAllScheduleByDate(calendarId, date), HttpStatus.OK);
    }

    // 스케쥴의 세부 정보
    @GetMapping("/detail/{calendarid}/{scheduleid}")
    @ApiOperation(value = "세부 일정 확인", response = ScheduleDto.class)
    public ResponseEntity<?> getScheduleDateByDate(@PathVariable("calendarid") String calendarId,
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
    // 스케쥴의 정보 변경
    @PutMapping("/modify/{calendarid}/{scheduleid}")
    @ApiOperation(value = "일정 변경", response = HttpStatus.class)
    public ResponseEntity<?> modifySchedule(@PathVariable("calendarid") String calendarId,
                                            @PathVariable("scheduleid") String scheduleId,
                                            @RequestParam("nowDate") String nowDate,
                                            @RequestBody ScheduleDto scheduleDto,
                                            HttpServletRequest request) {
        try {

            String memberId = JwtUtil.getUserIdByHttpRequest(request);
            scheduleService.modifySchedule(calendarId, scheduleId, memberId, nowDate, scheduleDto);
//            notificationService.addNotification(calendarId, memberId, scheduleId, nowDate, NotifiacationType.MODIFY.ordinal());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }
    // 일정 삭제
    @DeleteMapping("/delete/{calendarid}")
    @ApiOperation(value = "일정 삭제", response = HttpStatus.class)
    public ResponseEntity<?> deleteSchedule(@PathVariable("calendarid") String calendarId,
                                            @RequestParam String scheduleId) {
        scheduleService.deleteSchedule(calendarId, scheduleId);
        return ResponseEntity.ok().build();
    }
    // 일정 완료 처리
    @PutMapping("/complete/{calendarid}/{scheduleid}")
    @ApiOperation(value = "일정 완료", response = HttpStatus.class)
    public ResponseEntity<?> completeSchedule(@PathVariable("calendarid") String calendarId,
                                              @PathVariable("scheduleid") String scheduleId,
                                              @RequestParam String nowDate,
                                              @RequestParam String whoCompleted,
                                              @RequestParam boolean completed,
                                              HttpServletRequest request) throws ExecutionException, InterruptedException {

        String memberId = JwtUtil.getUserIdByHttpRequest(request);
        scheduleService.completeSchedule(calendarId, scheduleId, whoCompleted, completed);
        if (completed)
            notificationService.addNotification(calendarId, memberId, scheduleId, nowDate, NotifiacationType.COMPLETE.ordinal());
        else
            notificationService.addNotification(calendarId, memberId, scheduleId, nowDate, NotifiacationType.INCOMPLETE.ordinal());
        return ResponseEntity.ok().build();
    }
}
