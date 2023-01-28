package com.wepat.util;

public class DateUtil {
    public static int[] getDateFromString(String date) {
        int y = Integer.parseInt(date.substring(0, 4));
        int m = Integer.parseInt(date.substring(4, 6));
        int d = Integer.parseInt(date.substring(6, 8));

        return new int[] {y, m, d};
    }

    public static int[] getFirstDayofMonth(int[] ymd) {
        ymd[2] = 1;
        return ymd;
    }

    public static int[] getFirstDayofNextMonth(int[] ymd) {
        ymd[2] = 1;
        // next month
        ymd[1] += 1;

        // next year
        if (ymd[1] > 12) {
            ymd[1] = 1;
            ymd[0] += 1;
        }
        return ymd;
    }

    public static int getEndDayByMonth(int y, int m) {
        if (m == 2) {
            if (isLeapYear(y)) {
                return 29;
            } else {
                return 28;
            }
        }
        if (m == 4 || m == 6 || m == 9 || m == 11) {
            return 30;
        } else {
            return 31;
        }
    }

    public static boolean isLeapYear(int y) {
        if (y % 4 == 0) {
            if (y % 400 != 0 & y % 100 == 0) {
                return false;
            }
            return true;
        }
        return false;
    }

}
