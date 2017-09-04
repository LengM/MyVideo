package com.lovcreate.core.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * App工具类
 * Created by Albert.Ma on 2015/10/26.
 */
public class AppUtil {
    /**
     * 获取APP版本信息
     */
    public static String getAPPVersionCodeFromAPP(Context ctx) {
        int currentVersionCode = 0;
        String appVersionName = "";
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
            System.out.println(currentVersionCode + " " + appVersionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Logcat.e("getAPPVersionCodeFromAPP(AppUtil)获取版本信息失败" + e.getMessage());
        }
        return appVersionName;
    }

    /**
     * 获取APP名称
     */
    public static String getAPPName(Context ctx) {
        String packageName = ctx.getPackageName();
        return packageName.substring(packageName.lastIndexOf("."),packageName.length());
    }

    /**
     * APP是否在后台运行
     */
    public static boolean isApplicationBroughtToBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (tasks != null && !tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
