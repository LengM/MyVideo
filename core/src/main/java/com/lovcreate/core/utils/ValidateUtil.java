package com.lovcreate.core.utils;

/**
 * 验证工具类
 * Created by Albert.Ma on 2016/7/13.
 */
public class ValidateUtil {

    /**
     * 验证 Email地址有效性
     */
    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    /**
     * 验证 密码有效性
     */
    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}
