package com.wepat.schedule.controller;

import com.wepat.schedule.ScheduleDto;
import com.wepat.schedule.service.ScheduleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ScheduleController {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
    private final ScheduleService scheduleService;

    @GetMapping("/{calendarid}")
    @ApiOperation(value = "캘린더 메인페이지", notes = "캘린더에 해당 달에 대한 모든 스케쥴조회", response = ScheduleDto.class)
    public ResponseEntity<?> getScheduleByMonth(@PathVariable("calendarid") String calendarId) {
        return new ResponseEntity<>(scheduleService.getScheduleByCalendarId(calendarId), HttpStatus.OK);
    }

    @PostMapping("/add/{calendarid}")
    @ApiOperation(value = "일정 추가", notes = "일정 추가 버튼 클릭 시, 일정 추가", response = HttpStatus.class)
    public ResponseEntity<?> addSchedule(@PathVariable("calendarid") String calendarId,
                                         @RequestBody ScheduleDto scheduleDto) {
        scheduleService.addSchedule(calendarId, scheduleDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list/{calendarid}")
    @ApiOperation(value = "일정 확인", response = ScheduleDto.class)
    public ResponseEntity<?> getScheduleByDate(@PathVariable("calendarid") String calendarId,
                                               @RequestParam("date") String date) throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(scheduleService.getScheduleListByDate(calendarId, date), HttpStatus.OK);
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
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/modify/{calendarid}/{scheduleid")
    @ApiOperation(value = "일정 변경", response = HttpStatus.class)
    public ResponseEntity<?> modifySchedule(@PathVariable("calendarid") String calendarId,
                                            @PathVariable("scheduleid") String scheduleId,
                                            @RequestBody ScheduleDto scheduleDto) {
        try {
            scheduleService.modifySchedule(calendarId, scheduleId, scheduleDto);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/delete/{calendarid}")
    @ApiOperation(value = "일정 삭제", response = HttpStatus.class)
    public ResponseEntity<?> deleteSchedule(@PathVariable("calendarid") String calendarId,
                                            @RequestParam("date") String writtenDate) {
        scheduleService.deleteSchedule(calendarId, writtenDate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
