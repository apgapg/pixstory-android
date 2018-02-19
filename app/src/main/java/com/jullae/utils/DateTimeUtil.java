package com.jullae.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.jullae.constant.AppConstant.DateConstants.DATE_FORMAT;
import static com.jullae.constant.AppConstant.DateConstants.LOCAL_FORMAT;
import static com.jullae.constant.AppConstant.DateConstants.UTC_FORMAT;

/**
 * Developer: Saurabh Verma
 * Dated: 03-03-2017.
 */

public final class DateTimeUtil {

    private static final int ONE_MIN_IN_MILLISECONDS = 60000;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final int FIFTY = 50;
    private static final int NINTY = 90;
    private static final int TWENTY_FOUR = 24;
    private static final int FOURTY_EIGHT = 48;
    private static final long VALUE = 1000000000000L;

    /**
     * Empty Constructor
     * not called
     */
    private DateTimeUtil() {
    }

    /**
     * Method to get current timezone offset value
     *
     * @return offset value in minutes
     */
    public static String getCurrentZoneOffset() {
        TimeZone tz = TimeZone.getDefault();
        return String.valueOf(tz.getOffset(Calendar.ZONE_OFFSET) / ONE_MIN_IN_MILLISECONDS);
    }

    /**
     * Method to convert UTC to LOCAL time format
     *
     * @param date UTC date
     * @return local timezone converted date
     */
    public static String getLocalDateFromUTC(final String date) {
        DateFormat f = new SimpleDateFormat(UTC_FORMAT, Locale.ENGLISH);
        f.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date d = f.parse(date);
            DateFormat formatedDate = new SimpleDateFormat(LOCAL_FORMAT, Locale.ENGLISH);
            return formatedDate.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Get current date.
     *
     * @return date in format.
     */
    public static String getCurrentDate() {
        return getSimpleFormat().format(Calendar.getInstance().getTime());
    }

    /**
     * get date format.
     *
     * @return simple date format.
     */
    public static SimpleDateFormat getSimpleFormat() {
        return new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
    }

    /**
     * Convert Date from One Format to another Format
     *
     * @param toFormat   , Format in which to format date
     * @param fromFormat , Format to which format date
     * @param date       , date string to format
     * @return , formatted date string
     */
    public static String convertDateFormat(final String toFormat, final String fromFormat,
                                           final String date) {
        if (date != null && !"".equals(date)) {
            DateFormat originalFormat = new SimpleDateFormat(fromFormat, Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat(toFormat, Locale.ENGLISH);
            try {
                Date date1 = originalFormat.parse(date);
                return targetFormat.format(date1);
            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }

    /**
     * Get the time in ago.
     *
     * @param t time in millisec.
     * @return string of time.
     */
    public static String getTimeAgo(final long t) {
        long time = t;
        if (time < VALUE) {
            // if timestamp given in seconds, convert to millis
            time *= SECOND_MILLIS;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < FIFTY * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < NINTY * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < TWENTY_FOUR * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < FOURTY_EIGHT * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    /**
     * Get the UtC date convert into millisec.
     *
     * @param date UTC date
     * @return date in millisec
     * @throws ParseException exception
     */
    public static long getDateInMilliSec(final String date) throws ParseException {
        DateFormat f = new SimpleDateFormat(UTC_FORMAT, Locale.ENGLISH);
        f.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date d = f.parse(date);
        return d.getTime();
    }

}
