package com.mjf.mjfmanagesystem.util;

import java.text.SimpleDateFormat;

/**
 * Created by lxt on 2017/5/10.
 */
public class CommonUtil {
    public static boolean isNotNUll(String value) {
        if (value != null && value.length() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isNUll(String value) {
        if (value != null && value.length() > 0) {
            return false;
        }
        return true;
    }

    public static String getCurrentTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }
}
