package com.lovcreate.core.utils;

/**
 * 禁止重复操作工具
 * Created by Albert.Ma on 2015/12/25.
 */
public class ForbidRepeatOperateUtil {

    private static long OPERATE_INTERVAL = 3000;//操作间隔

    private static long lastOperateTime;

    private static boolean isFirstPressFlag;

    private static long viewId =-1;//禁止控件的id

    /**
     * 初始化
     * 放在 setOnClickListener 前
     */
    public static void init() {
        isFirstPressFlag = true;
        lastOperateTime = System.currentTimeMillis();
    }

    /**
     * 判断有没有重复点击(一个控件时)
     * 放在 setOnClickListener 的 onClick方法前部
     * if (ForbidRepeatOperateUtil.isRepeatPress()) return;
     */
    public static boolean isRepeatPress() {
        long lastPressTime = System.currentTimeMillis();
        long interval = lastPressTime - lastOperateTime;//与上一次点击的时差
        //如果不是第一次点击,且与上一次点击的间隔小于5000则返回真
        if ((0 < interval && interval < OPERATE_INTERVAL) && !isFirstPressFlag) {
            return true;
        } else {
            isFirstPressFlag = false;
            lastOperateTime = lastPressTime;
            return false;
        }

    }


    /**
     * 判断有没有重复点击(多个控件时)
     * 放在 setOnClickListener 的 onClick方法前部
     * if (ForbidRepeatOperateUtil.isRepeatPress(R.id.button)) return;
     * @param currentViewId 当前控件id
     */
    public static boolean isRepeatPress(long currentViewId) {

        //初始化禁止控件的id
        if(viewId <0){
            viewId =currentViewId;
        }

        //如果当前控件id不是禁止控件id
        if(currentViewId!= viewId){
            viewId =currentViewId;
            isFirstPressFlag = true;
        }

        long lastPressTime = System.currentTimeMillis();
        long interval = lastPressTime - lastOperateTime;//与上一次点击的时差
        //如果不是第一次点击,且与上一次点击的间隔小于5000则返回真
        if ((0 < interval && interval < OPERATE_INTERVAL) && !isFirstPressFlag) {
            return true;
        } else {
            isFirstPressFlag = false;
            lastOperateTime = lastPressTime;
            return false;
        }

    }

}
