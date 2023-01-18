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
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/calendar")
@CrossOrigin(origins = "*")
public class CalendarController {
    private final Logger logger = LoggerFactory.getLogger(CalendarController.class);
    private final String COLLECTION_NAME = "calendar";
    @GetMapping("/test")
    public ResponseEntity<?> testCalendar() throws ExecutionException, InterruptedException {
//        Schedule schedule = new Schedule();
//        schedule.setMemo("memo");
//        schedule.setPeriod(3);
//        schedule.setNPeriod(10);
//        schedule.setWeight(3.5);
//        List<String> photoList = new ArrayList<>();
//        photoList.add("hihi");
//        schedule.setPhotoList(photoList);
//        schedule.setTitle("happy byul");
//        schedule.setDate(Timestamp.now().toSqlTimestamp());
//
//        System.out.println(schedule);
//
//        Firestore db = FirestoreClient.getFirestore();
//
//        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        String docId = "";
//        for (QueryDocumentSnapshot document : documents) {
//            System.out.println(document.getId());
//            docId = document.getId();
//        }
//        ApiFuture<com.google.cloud.firestore.WriteResult> apiFuture
//                = db.collection(COLLECTION_NAME).document().set(schedule);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @GetMapping("/{calendarId}/{date}")
    @ApiOperation(value = "캘린더 메인페이지", notes = "캘린더에 해당 달에 대한 모든 스케쥴조회", response = ScheduleDto.class)
    public ResponseEntity<?> getCalendar(@PathVariable String calendarId, @PathVariable Timestamp date) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{calendarId}/{date}/{petId}")
    @ApiOperation(value = "특정 반려동물의 캘린더", notes = "반려동물 선택시, 선택한 반려동물의 해당 달에 대한 스케쥴만 조회", response = ScheduleDto.class)
    public ResponseEntity<?> getCalendarByPetId(@PathVariable String calendarId,
                                                @PathVariable Timestamp date,
                                                @PathVariable String petId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/add/{calendarId}")
    @ApiOperation(value = "일정 추가 페이지", notes = "일정 추가 페이지 진입 시, 등록되어 있는 반려동물 보여주기", response = PetDto.class)
    public ResponseEntity<?> addSchedulePage(@PathVariable String calendarId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/add/{calendarId}/{petId}")
    @ApiOperation(value = "추가페이지 반려동물 선택", notes = "일정 추가페이지에서 반려동물 선택시 이름 자동 생성", response = String.class)
    public ResponseEntity<?> getPetName(@PathVariable String calendarId, @PathVariable String petId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add/{calendarId}/{petId}")
    @ApiOperation(value = "일정 추가", notes = "일정 추가 버튼 클릭 시, 일정 추가", response = HttpStatus.class)
    public ResponseEntity<?> addSchedule(@PathVariable String calendarId, @PathVariable String petId,
                                         @RequestBody ScheduleDto scheduleDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{calendarId}/detail/{scheduleId}")
    @ApiOperation(value = "세부 일정 확인", response = ScheduleDto.class)
    public ResponseEntity<?> getScheduleById(@PathVariable String calendarId, @PathVariable String scheduleId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{calendarId}/detail/{scheduleId}")
    @ApiOperation(value = "세부 일정 변경", response = HttpStatus.class)
    public ResponseEntity<?> modifyScheduleById(@PathVariable String calendarId, @PathVariable String scheduleId,
                                                @RequestBody ScheduleDto scheduleDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
