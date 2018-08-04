package com.jullae.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    private static long currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime().getTime();
    }

    public static String getTimeAgo(Date date) {


        long time = date.getTime();
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = currentDate();
        if (time > now || time <= 0) {
            return "in the future";
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "1m";
        } else if (diff < 60 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + "m";
        } else if (diff < 2 * HOUR_MILLIS) {
            return "1h";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + "h";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "1d";
        } else {
            if (diff / DAY_MILLIS < 31)
                return diff / DAY_MILLIS + "d";
            else if (((diff / DAY_MILLIS) / 30) < 12)
                return ((diff / DAY_MILLIS) / 30) + "mon";
            else
                return ((diff / DAY_MILLIS) / (30 * 12)) + "yr";
        }
    }
}
