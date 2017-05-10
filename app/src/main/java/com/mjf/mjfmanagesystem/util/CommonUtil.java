package com.mjf.mjfmanagesystem.util;

/**
 * Created by lxt on 2017/5/10.
 */
public class CommonUtil {
    public  static boolean isNotNUll(String value){
        if(value !=null && value.length()>0){
            return true;
        }
        return false;
    }
    public  static boolean isNUll(String value){
        if(value !=null && value.length()>0){
            return false;
        }
        return true;
    }
}
