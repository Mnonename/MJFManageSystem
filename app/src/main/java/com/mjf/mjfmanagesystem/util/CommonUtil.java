package com.mjf.mjfmanagesystem.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lxt on 2017/5/10.
 */
public class CommonUtil {
    private static String PREFERENCE_NAME ="mjf_test";
    public static final  String USERCODE ="usercode";
    public static final  String PASSWORD ="password";
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
    /**
     * check card all place of China
     *
     * @param card
     * @return
     */
    public static boolean isValidCard(String card) {
        if (card == null || "".equals(card)) return false;
        if (card.length() == 10 || card.length() == 15) {
            return true;
        } else if (card.length() == 18) {
            return isValidChinaCard(card);
        }
        return false;

    }
    public static boolean isValidChinaCard(String card) {
        card = card.toUpperCase();
        int year = Integer.parseInt(card.substring(6, 10));
        int month = Integer.parseInt(card.substring(10, 12));
        int day = Integer.parseInt(card.substring(12, 14));

        int[] weight = {2, 4, 8, 5, 10, 9, 7, 3, 6, 1, 2, 4, 8, 5, 10, 9, 7};
        char[] checkCard = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

        int sum = 0;
        int[] tmpCard = new int[18];

        if (year < 1900 || month < 1 || month > 12 || day < 1 || day > 31
                || ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30)
                || (month == 2 && ((year % 4 > 0 && day > 28) || day > 29))) {
            return false;
        }

        for (int i = 1; i < 18; i++) {
            int j = 17 - i;
            tmpCard[i - 1] = Integer.parseInt(card.substring(j, j + 1));
        }

        for (int i = 0; i < 17; i++) {
            sum += weight[i] * tmpCard[i];
        }
        sum = sum % 11;
        if (card.charAt(17) != checkCard[sum]) {
            return false;
        }
        return true;
    }
    public static boolean isphonenum(String phonenum) {
        if (TextUtils.isEmpty(phonenum)) {
            return false;
        }
        Pattern p = Pattern.compile("^\\d{11}");
        Matcher m = p.matcher(phonenum);
        return m.matches();
    }
    public static boolean putString(Context mContext ,String key, String value) {
        SharedPreferences settings = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }
    public static String getString(Context mContext ,String key) {
        SharedPreferences settings = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, null);
    }
}
