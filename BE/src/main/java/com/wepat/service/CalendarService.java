package com.wepat.service;

import com.wepat.entity.CalendarEntity;

import java.util.concurrent.ExecutionException;

public interface CalendarService {
    CalendarEntity addSchedule(CalendarEntity member) throws ExecutionException, InterruptedException;

}
