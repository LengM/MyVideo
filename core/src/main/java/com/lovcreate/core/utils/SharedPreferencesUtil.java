package com.lovcreate.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * 共享参数工具类
 * Created by Albert.Ma on 2016/4/18.
 */
public class SharedPreferencesUtil {

    //配置存储
    public static final String SHARED_CONFIG = "SHARED_CONFIG";
    //是否首次运行
    public static final String IS_FIRST_RUN = "IS_FIRST_RUN";

    /**
     * 存储单个字符 例子
     */
    //序列号
    public static final String SERIAL_NUMBER = "SERIAL_NUMBER";

    /**
     * 存储List 例子
     */
    //ip config
    public static final String IP_CONFIG_SIZE = "IP_CONFIG_SIZE";
    public static final String IP_CONFIG = "IP_CONFIG_";


    /**
     * 首次运行以后，设置为false
     *
     * @param context
     * @return
     */
    public static boolean isFirstRun(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesUtil.SHARED_CONFIG, Activity.MODE_PRIVATE);
        boolean flag = sharedPreferences.getBoolean(SharedPreferencesUtil.IS_FIRST_RUN, true);
        if (flag) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(SharedPreferencesUtil.IS_FIRST_RUN, false);
            editor.apply();
        }
        return flag;
    }

    /**
     * 设置序列号
     */
    public static void setSerialNumber(Context context, String num) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesUtil.SHARED_CONFIG, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SharedPreferencesUtil.SERIAL_NUMBER, num);
        editor.apply();
    }

    /**
     * 获取序列号
     */
    public static String getSerialNumber(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesUtil.SHARED_CONFIG, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(SharedPreferencesUtil.SERIAL_NUMBER, "");
    }

    /**
     * 获取存储的IP,返回List<String>
     */
    public static List<String> getIpConfig(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesUtil.SHARED_CONFIG, Activity.MODE_PRIVATE);
        List<String> list = new ArrayList<>();
        String sizeKey = SharedPreferencesUtil.IP_CONFIG_SIZE;
        String itemKey = SharedPreferencesUtil.IP_CONFIG;

        String log = "存储的ip: \n";
        int size = sharedPreferences.getInt(sizeKey, 0);
        for (int i = 0; i < size; i++) {
            list.add(sharedPreferences.getString(itemKey + i, null));
            log = log + " i=" + i + "  item=" + sharedPreferences.getString(itemKey + i, null) + "\n";
        }
        Logcat.i(log);

        return list;
    }

    /**
     * 添加ip config 存储
     */
    public static boolean addIpConfig(Context context, String ip) {
        List<String> list = getIpConfig(context);

        if (ip == null) {
            return false;
        }

        for (String temp : list) {
            if (ip.equals(temp)) {
                return false;
            }
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesUtil.SHARED_CONFIG, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String sizeKey = SharedPreferencesUtil.IP_CONFIG_SIZE;
        String itemKey = SharedPreferencesUtil.IP_CONFIG;

        editor.putInt(sizeKey, list.size() + 1);
        editor.putString(itemKey + list.size(), ip);
        Logcat.i("添加ip config : sizeKey=" + sizeKey + "  size:" + (list.size() + 1));
        Logcat.i("添加ip config : itemKey=" + itemKey + list.size() + "  ip:" + ip);

        return editor.commit();
    }

    /**
     * 删除OBD Config
     */
    public static boolean deleteIpConfigByIp(Context context, String ip) {
        List<String> list = getIpConfig(context);
        List<String> newList = new ArrayList<>();

        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferencesUtil.SHARED_CONFIG, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String sizeKey = SharedPreferencesUtil.IP_CONFIG_SIZE;
        String itemKey = SharedPreferencesUtil.IP_CONFIG;

        int size = sharedPreferences.getInt(sizeKey, 0);
        for (int i = 0; i < size; i++) {
            if (!ip.equals(list.get(i))) {
                newList.add(list.get(i));
            } else {
                editor.remove(itemKey + i);
                Logcat.i("删除ip config : sizeKey=" + sizeKey + "  size:" + (list.size() - 1));
                Logcat.i("删除ip config : itemKey=" + itemKey + i + "  ip:" + ip);
            }
        }

        editor.putInt(sizeKey, newList.size());
        String log = "删除后新存储的ip: \n";
        for (int i = 0; i < newList.size(); i++) {
            editor.putString(itemKey + i, newList.get(i));
            log = log + " i=" + i + "  item=" + sharedPreferences.getString(itemKey + i, null) + "\n";
        }
        Logcat.i(log);

        return editor.commit();
    }

}
