package com.wepat.controller;

import com.wepat.dto.PetDto;
import com.wepat.dto.ScheduleDto;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/calendar")
@CrossOrigin(origins = "*")
public class CalendarController {
    private final static Logger logger = LoggerFactory.getLogger(CalendarController.class);
    @GetMapping("/{calendarid}/{date}")
    @ApiOperation(value = "캘린더 메인페이지", notes = "캘린더에 해당 달에 대한 모든 스케쥴조회", response = ScheduleDto.class)
    public ResponseEntity<?> getCalendar(@PathVariable("calendarid") String calendarId, @PathVariable Timestamp date) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{calendarid}/{date}/{petid}")
    @ApiOperation(value = "특정 반려동물의 캘린더", notes = "반려동물 선택시, 선택한 반려동물의 해당 달에 대한 스케쥴만 조회", response = ScheduleDto.class)
    public ResponseEntity<?> getCalendarByPetId(@PathVariable("calendarid") String calendarId,
                                                @PathVariable Timestamp date,
                                                @PathVariable("petid") String petId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/add/{calendarid}")
    @ApiOperation(value = "일정 추가 페이지", notes = "일정 추가 페이지 진입 시, 등록되어 있는 반려동물 보여주기", response = PetDto.class)
    public ResponseEntity<?> addSchedulePage(@PathVariable("calendarid") String calendarId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/add/{calendarid}/{petid}")
    @ApiOperation(value = "추가페이지 반려동물 선택", notes = "일정 추가페이지에서 반려동물 선택시 이름 자동 생성", response = String.class)
    public ResponseEntity<?> getPetName(@PathVariable("calendarid") String calendarId, @PathVariable("petid") String petId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add/{calendarid}/{petid}")
    @ApiOperation(value = "일정 추가", notes = "일정 추가 버튼 클릭 시, 일정 추가", response = HttpStatus.class)
    public ResponseEntity<?> addSchedule(@PathVariable("calendarid") String calendarId, @PathVariable("petid") String petId,
                                         @RequestBody ScheduleDto scheduleDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{calendarid}/detail/{scheduleid}")
    @ApiOperation(value = "세부 일정 확인", response = ScheduleDto.class)
    public ResponseEntity<?> getScheduleById(@PathVariable("calendarid") String calendarId, @PathVariable("scheduleid") String scheduleId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{calendarid}/detail/{scheduleid}")
    @ApiOperation(value = "세부 일정 변경", response = HttpStatus.class)
    public ResponseEntity<?> modifyScheduleById(@PathVariable("calendarid") String calendarId, @PathVariable("scheduleid") String scheduleId,
                                                @RequestBody ScheduleDto scheduleDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
