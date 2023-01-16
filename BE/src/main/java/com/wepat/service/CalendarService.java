package com.wepat.service;

import com.wepat.dto.Calendar;

import java.util.concurrent.ExecutionException;

public interface CalendarService {
    Calendar addSchedule(Calendar member) throws ExecutionException, InterruptedException;

}
