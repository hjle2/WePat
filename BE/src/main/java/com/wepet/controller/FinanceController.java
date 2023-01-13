package com.wepet.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/finance")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class FinanceController {
    @GetMapping("{calendarid}")
    public ResponseEntity<?> getAllFinance(@PathVariable String calendarId) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @PostMapping("/add/{calendarid}")
    public ResponseEntity<?> addFinance(@PathVariable String calendarId) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @PostMapping("/detail/{calendarid}")
    public ResponseEntity<?> getFinanceById(@PathVariable String calendarId) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
