/*!
 @file TimeUtils.java
 ITS AP Forum FUKUOKA

 @author
    Created by Nagakura Hideharu.
 @copyright
    Copyright (c) 2018 Jorudan Co.,Ltd. All rights reserved.
 */
package kirin3.jp.honeycombbattle.util;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class TimeUtils {

    private static final int DAY_FLAGS = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NO_YEAR | DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_WEEKDAY;

    /**
     * 現在時刻ミリ秒を取得
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * String型("yyyy-MM-dd kk:mm:ss") → Date型に変換
     */
    public static Date parseTimestamp(String timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.JAPAN);
        try {
            return format.parse(timestamp);
        } catch (ParseException ex) {
        }

        return null;
    }

    /**
     * Date型 → String変換(YYYY/MM/DD)
     */
    public static String formatShortDate(Context context, Date date) {
        DateFormat format = android.text.format.DateFormat.getMediumDateFormat(context);
        return format.format(date).toLowerCase(Locale.JAPAN);
    }

    /**
     * Date型 → String変換(午前or午後HH:MM)
     * 午前10:58
     */
    public static String formatShortTime(Context context, Date time) {
        DateFormat format = android.text.format.DateFormat.getTimeFormat(context);
        return format.format(time).toLowerCase(Locale.JAPAN);
    }

    /**
     * long型 → String型変換
     * 2018年7月4日(水)
     */
    public static String formatDateTime(Context context, long time) {
        return DateUtils.formatDateTime(context, time, DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_ALL);
    }

    /**
     * long型 → String型変換
     * 7月4日(水)
     */
    public static String formatDaySeparator(Context context, long time) {
        StringBuilder recycle = new StringBuilder();
        Formatter formatter = new Formatter(recycle);
        return DateUtils.formatDateRange(context, formatter, time, time, DAY_FLAGS).toString();
    }


}
