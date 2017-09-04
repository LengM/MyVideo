package com.lovcreate.core.utils;

import android.util.Log;

import com.lovcreate.core.constant.Constant;

/**
 * 日志工具
 * Created by Albert.Ma on 2016/4/20.
 */
public class Logcat {

    //控制是否打印日志
    public static boolean isLoggerOn = true;

    /**
     * 打印info
     */
    public static void i(String log) {
        if (isLoggerOn) {
            Log.i(Constant.Logcat, log);
        }
    }

    /**
     * 打印error
     */
    public static void e(String log) {
        if (isLoggerOn) {
            Log.e(Constant.Logcat, log);
        }
    }
}
