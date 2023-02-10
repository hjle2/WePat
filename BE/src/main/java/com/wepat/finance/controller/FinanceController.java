package com.wepat.finance.controller;


import com.wepat.exception.finance.AlreadyDeleteFinanceException;
import com.wepat.finance.FinanceDto;
import com.wepat.finance.service.FinanceService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class FinanceController {
    private final FinanceService financeService;

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
    public ResponseEntity<?> addFinanceById(@PathVariable("calendarid") String calendarId, @RequestBody FinanceDto financeDto) {
        try {
            financeService.addFinanceById(calendarId, financeDto);
            return new ResponseEntity<>("추가 성공", HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    @GetMapping("/detail/{calendarid}/{financeid}")
    @ApiOperation(value = "가계부 세부 내용 조회", notes = "반환 값 : 선택한 가계부 세부 내용")
    public ResponseEntity<?> getFinanceById(@PathVariable("calendarid") String calendarId, @PathVariable("financeid") String financeId) {
        try {
            return new ResponseEntity<>(financeService.getFinanceById(calendarId, financeId), HttpStatus.OK);
        } catch (AlreadyDeleteFinanceException e) {
            throw new AlreadyDeleteFinanceException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    @PutMapping("/modify/{calendarid}/{financeid}")
    @ApiOperation(value = "가계부 세부 내용 변경", notes = "반환 값 : 성공 or 실패")
    public ResponseEntity<?> modifyFinanceById(@PathVariable("calendarid") String calendarId,
                                                 @PathVariable("financeid") String financeId,
                                                 @RequestBody FinanceDto financeDto) {
        try {
            financeService.modifyFinanceById(calendarId, financeId, financeDto);
            return new ResponseEntity<>("수정 성공", HttpStatus.OK);
        } catch (AlreadyDeleteFinanceException e) {
            throw new AlreadyDeleteFinanceException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @DeleteMapping("/delete/{calendarid}/{financeid}")
    @ApiOperation(value = "가계부 세부 내용 삭제", notes = "반환 값 : 성공 or 실패")
    public ResponseEntity<?> deleteFinanceById(@PathVariable("calendarid") String calendarId,
                                               @PathVariable("financeid") String financeId) {
        try {
            financeService.deleteFinanceById(calendarId, financeId);
            return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
        } catch (AlreadyDeleteFinanceException e) {
            throw new AlreadyDeleteFinanceException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
