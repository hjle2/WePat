package com.wepat.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    public static String getStringDate(Date date) {
        return dateFormat.format(date);
    }

    public static Date getDate(String ymd) {
        Date date = null;
        try {
            date = dateFormat.parse(ymd);
        } catch (ParseException e) {
            try {
                date = new SimpleDateFormat("yyyyMMddHHmmss").parse(ymd);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
        return date;
    }
    public static Date addDays(Date date, int unit, int CALENDAR_UNIT) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(CALENDAR_UNIT, unit);
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
}
