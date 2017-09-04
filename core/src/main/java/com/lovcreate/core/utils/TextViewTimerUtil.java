package com.lovcreate.core.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 倒计时更新TextView界面 工具类
 * 使用方法:
 * 1,初始化
 *  TextViewTimerUtil tu = new TextViewTimerUtil(getActivity(),targetTextView);
 * 2.设置倒数回调
 *  tu.setCountdownCallback(new TextViewTimerUtil.CountdownCallback() {
 *       @Override
 *       public void beginCountdown() {
 *           switchCityLayout.setBackgroundColor(Color.RED);
 *       }
 *       @Override
 *       public void endCountdown() {
 *           switchCityLayout.setBackgroundColor(Color.GREEN);
 *       }
 *   });
 * 3,开始调计时
 *  tu.startCountdown(30,"还剩时间","s");
 *
 * Created by Albert.Ma on 2016/4/6.
 */
public class TextViewTimerUtil {

    private Context context;
    private TextView targetView;
    private CountdownCallback countdownCallback;

    //总时间长度
    private int totalTime;
    private String timingPrefix;//计时前缀
    private String timingSuffix;//计时后缀
    //计时器
    private Timer timer;
    //计时器任务
    private TimerTask task;
    //通知UI线程更新界面
    private Handler updateTextHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //查看是否到时间
            if (totalTime <= 1) {
                timer.cancel();
                countdownCallback.endCountdown();
                return;
            }
            //继续执行
            totalTime--;
            targetView.setText(timingPrefix + String.valueOf(totalTime) + timingSuffix);
        }
    };

    public TextViewTimerUtil(Context context, TextView targetView) {
        this.context = context;
        this.targetView = targetView;
    }

    public TextViewTimerUtil(Context context, TextView targetView, CountdownCallback countdownCallback) {
        this.context = context;
        this.targetView = targetView;
        this.countdownCallback = countdownCallback;
    }

    /**
     * 设置倒计时回调
     *
     * @param countdownCallback
     */
    public void setCountdownCallback(CountdownCallback countdownCallback) {
        this.countdownCallback = countdownCallback;
    }

    /**
     * 开始执行倒计时 (每秒执行)
     *
     * @param totalTime 总时间长度(秒)
     */
    public void startCountdown(int totalTime, String timingPrefix, String timingSuffix) {
        //显示刚开始的时间
        targetView.setText(timingPrefix + totalTime + timingSuffix);
        //延迟1s执行,周期1s
        startCountdown(totalTime, 1000, 1000, timingPrefix, timingSuffix);
    }

    /**
     * 开始执行倒计时
     *
     * @param totalTime    总时间长度(秒)
     * @param delay        延迟时间ms
     * @param period       周期时间ms
     * @param timingPrefix 计时前缀
     * @param timingSuffix 计时后缀
     */
    public void startCountdown(int totalTime, long delay, long period, String timingPrefix, String timingSuffix) {
        this.totalTime = totalTime;
        this.timingPrefix = timingPrefix;
        this.timingSuffix = timingSuffix;
        //每次实例化Timer和TimerTask,因为是消耗品,只能执行一次
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                updateTextHandler.sendEmptyMessage(0);
            }
        };
        countdownCallback.beginCountdown();
        timer.schedule(task, delay, period);
    }

    /**
     * 停止执行倒计时
     */
    public void stopCountDown(){
        if(timer!=null){
            timer.cancel();
            updateTextHandler.removeMessages(0);
        }
    }

    /**
     * 倒计时回调
     */
    public interface CountdownCallback {
        //开始倒计时执行
        void beginCountdown();

        //结束倒计时执行
        void endCountdown();
    }
}
