package com.lovcreate.core.utils;

/**
 * 字符串处理工具
 * Created by albert.ma on 15-7-22.
 */
public class StringUtil {

    /**
     * 判断返回对象的toString,空时返回""
     */
    public static String getToStringOrEmpty(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * 判断数字字符串是否是null或者0
     */
    public static boolean isNullOr0(String property) {
        return property != null && !"0".equals(property);
    }

    /**
     * 返回数字字符串 如果为空返回0
     */
    public static String getStringOr0(String str) {
        return str != null && !"".equals(str) ? str : "0";
    }

    /**
     * 判断第一个数字字符串是否是第二个数字字符串的0.7-1.3之间
     */
    public static boolean isIn30Percent(String firstPrice, String secondPrice) {
        return Double.parseDouble(firstPrice) < 0.7 * Double.parseDouble(secondPrice)
                || Double.parseDouble(firstPrice) > 1.3 * Double.parseDouble(secondPrice);
    }


    /**
     * 数字字符返回数字,如果不是数字返回0
     */
    public static int getNumber(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || "".equals(str);
    }
}
