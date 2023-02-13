package com.wepat.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    public static String getStringDate(Date date) {
        return dateFormat.format(date);
    }

    public static Date getDate(String source) {
        Date date = null;
        try {
            date = dateFormat.parse(source);
        } catch (ParseException e) {
            try {
                date = dateFormat.parse(source);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
        return date;
    }
    public static Date addDays(Date date, int unit, int CALENDAR_UNIT) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(unit, CALENDAR_UNIT);

        return calendar.getTime();
    }

    public static String getFirstDayOfMonth(String ymd) {
        Date date = getDate(ymd);
        date.setDate(1);

        return dateFormat.format(date);
    }

    public static String getFirstDayOfNextMonth(String ymd) {
        Date date = getDate(ymd);
        date = addDays(date, 1, Calendar.MONTH);
        date.setDate(1);

        return dateFormat.format(date);
    }

    public static Date setZeroTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
