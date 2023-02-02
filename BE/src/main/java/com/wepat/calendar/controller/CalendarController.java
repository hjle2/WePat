package com.wepat.calendar.controller;

import com.wepat.calendar.service.CalendarService;
import com.wepat.schedule.ScheduleDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CalendarController {
    private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);
    private final CalendarService calService;

    @GetMapping("/{calendarid}/{date}")
    @ApiOperation(value = "캘린더 메인페이지", notes = "캘린더에 해당 달에 대한 모든 스케쥴조회", response = ScheduleDto.class)
    public ResponseEntity<?> getScheduleByMonth(@PathVariable("calendarid") String calendarId,
                                                @PathVariable("date") String date) {
        calService.getScheduleByMonth(calendarId, date);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/add/{calendarid}/{date}")
    @ApiOperation(value = "일정 추가", notes = "일정 추가 버튼 클릭 시, 일정 추가", response = HttpStatus.class)
    public ResponseEntity<?> addSchedule(@PathVariable("date") String date,
                                         @RequestBody ScheduleDto scheduleDto) {
        calService.addSchedule(scheduleDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/{calendarid}/{date}")
    @ApiOperation(value = "일정 확인", response = ScheduleDto.class)
    public ResponseEntity<?> getScheduleByDate(@PathVariable("calendarid") String calendarId,
                                               @PathVariable("date") String writtenDate) {
        calService.getScheduleByDate(calendarId, writtenDate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/detail/{calendarid}/{date}")
    @ApiOperation(value = "세부 일정 확인", response = ScheduleDto.class)
    public ResponseEntity<?> getDetailScheduleByDate(@PathVariable("calendarid") String calendarId,
                                                     @PathVariable("date") String writtenDate) {
        calService.getScheduleDetailByDate(calendarId, writtenDate);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/modify/{calendarid}/{date}")
    @ApiOperation(value = "일정 변경", response = HttpStatus.class)
    public ResponseEntity<?> modifySchedule(@RequestBody ScheduleDto scheduleDto,
                                            @PathVariable("date") String writtenDate) {
        calService.modifySchedule(scheduleDto, writtenDate);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/delete/{calendarid}/{date}")
    @ApiOperation(value = "일정 삭제", response = HttpStatus.class)
    public ResponseEntity<?> deleteSchedule(@PathVariable("calendarid") String calendarId,
                                            @PathVariable("date") String writtenDate) {
        calService.deleteSchedule(calendarId, writtenDate);
        return ResponseEntity.ok().build();
    }
}
