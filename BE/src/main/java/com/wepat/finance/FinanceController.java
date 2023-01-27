package com.wepat.finance;


import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance")
@CrossOrigin(origins = "*")
public class FinanceController {
    private final static Logger logger = LoggerFactory.getLogger(FinanceController.class);
    @GetMapping("/{calendarid}")
    @ApiOperation(value = "가계부 내용", response = List.class)
    public ResponseEntity<?> getAllFinance(@PathVariable("calendarid") String calendarId) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @PostMapping("/add/{calendarid}")
    @ApiOperation(value = "가계부 내용 추가", response = HttpStatus.class)
    public ResponseEntity<?> addFinance(@PathVariable("calendarid") String calendarId, @RequestBody FinanceDto financeDto) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @GetMapping("/detail/{calendarid}")
    @ApiOperation(value = "가계부 세부 내용 조회", response = FinanceDto.class)
    public ResponseEntity<?> getFinanceById(@PathVariable("calendarid") String calendarId) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @PutMapping("/detail/{calendarid}")
    @ApiOperation(value = "가계부 세부 내용 변경")
    public ResponseEntity<?> modifyFinanceByTime(@PathVariable("calendarid") String calendarId, @RequestBody FinanceDto financeDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
