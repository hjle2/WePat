package com.wepat.controller;


import com.wepat.dto.FinanceDto;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class FinanceController {
    private final Logger logger = LoggerFactory.getLogger(FinanceController.class);
    @GetMapping("{calendarid}")
    @ApiOperation(value = "가계부 내용", response = List.class)
    public ResponseEntity<?> getAllFinance(@PathVariable String calendarId) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @PostMapping("/add/{calendarid}")
    @ApiOperation(value = "가계부 내용 추가", response = HttpStatus.class)
    public ResponseEntity<?> addFinance(@PathVariable String calendarId) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @PostMapping("/detail/{calendarid}")
    @ApiOperation(value = "가계부 세부 내용", response = FinanceDto.class)
    public ResponseEntity<?> getFinanceById(@PathVariable String calendarId) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @PutMapping("detail/{calendarid}")
    @ApiOperation(value = "가계부 세부 내용 변경")
    public ResponseEntity<?> updateFinanceByTime(@PathVariable String calendarid, @RequestBody FinanceDto financeDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
