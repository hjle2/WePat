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

    @GetMapping("/{calendarid}/{date}")
    @ApiOperation(value = "캘린더 메인페이지", notes = "캘린더에 해당 달에 대한 모든 스케쥴조회", response = ScheduleDto.class)
    public ResponseEntity<?> getScheduleByMonth(@PathVariable("calendarid") String calendarId,
                                                @PathVariable("date") String date) {
        scheduleService.getScheduleByMonth(calendarId, date);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add/{calendarid}/{date}")
    @ApiOperation(value = "일정 추가", notes = "일정 추가 버튼 클릭 시, 일정 추가", response = HttpStatus.class)
    public ResponseEntity<?> addSchedule(@PathVariable("date") String date,
                                         @RequestBody ScheduleDto scheduleDto) {
        scheduleService.addSchedule(scheduleDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list/{calendarid}/{scheduleid}")
    @ApiOperation(value = "일정 확인", response = ScheduleDto.class)
    public ResponseEntity<?> getScheduleByDate(@PathVariable("calendarid") String calendarId,
                                               @PathVariable("scheduleid") String scheduleId) throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(scheduleService.getScheduleListByDate(calendarId, scheduleId), HttpStatus.OK);
    }

    @GetMapping("/detail/{calendarid}/{scheduleid}")
    @ApiOperation(value = "세부 일정 확인", response = ScheduleDto.class)
    public ResponseEntity<?> getDetailScheduleByDate(@PathVariable("calendarid") String calendarId,
                                                     @PathVariable("scheduleid") String scheduleId) {
        scheduleService.getScheduleDetailByDate(calendarId, scheduleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/modify/{calendarid}/{date}")
    @ApiOperation(value = "일정 변경", response = HttpStatus.class)
    public ResponseEntity<?> modifySchedule(@RequestBody ScheduleDto scheduleDto,
                                            @PathVariable("date") String writtenDate) {
        scheduleService.modifySchedule(scheduleDto, writtenDate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/delete/{calendarid}/{date}")
    @ApiOperation(value = "일정 삭제", response = HttpStatus.class)
    public ResponseEntity<?> deleteSchedule(@PathVariable("calendarid") String calendarId,
                                            @PathVariable("date") String writtenDate) {
        scheduleService.deleteSchedule(calendarId, writtenDate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
