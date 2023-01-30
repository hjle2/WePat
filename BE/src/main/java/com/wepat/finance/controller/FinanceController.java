package com.wepat.finance.controller;


import com.wepat.exception.finance.AlreadyDeleteFinance;
import com.wepat.finance.FinanceDto;
import com.wepat.finance.service.FinanceService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/finance")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FinanceController {
    private static final Logger logger = LoggerFactory.getLogger(FinanceController.class);
    private final FinanceService financeService;

    private static final Logger logger = LoggerFactory.getLogger(FinanceController.class);
    @GetMapping("/{calendarid}")
    @ApiOperation(value = "가계부 모든 내용", notes = "반환 값 : 모든 가계부 내용 List")
    public ResponseEntity<?> getAllFinance(@PathVariable("calendarid") String calendarId) {
        try {
            return new ResponseEntity<>(financeService.getAllFinance(calendarId), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    @PostMapping("/add/{calendarid}")
    @ApiOperation(value = "가계부 내용 추가", notes = "반환 값 : 성공 or 실패")
    public ResponseEntity<?> addFinance(@PathVariable("calendarid") String calendarId, @RequestBody FinanceDto financeDto) {
        try {
            financeService.addFinance(calendarId, financeDto);
            return new ResponseEntity<>("추가 성공", HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    @GetMapping("/detail/{calendarid}/{financeid}")
    @ApiOperation(value = "가계부 세부 내용 조회", notes = "반환 값 : 선택한 가계부 세부 내용")
    public ResponseEntity<?> getFinanceById(@PathVariable("calendarid") String calendarId, @PathVariable("financeid") String financeId) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @PutMapping("/modify/{calendarid}/{financeid}")
    @ApiOperation(value = "가계부 세부 내용 변경", notes = "반환 값 : 성공 or 실패")
    public ResponseEntity<?> modifyFinanceById(@PathVariable("calendarid") String calendarId,
                                                 @PathVariable("financeid") String financeId,
                                                 @RequestBody FinanceDto financeDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{calendarid}/{financeid}")
    @ApiOperation(value = "가계부 세부 내용 삭제", notes = "반환 값 : 성공 or 실패")
    public ResponseEntity<?> deleteFinanceById(@PathVariable("calendarid") String calendarId,
                                               @PathVariable("financeid") String financeId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
