package com.yng.demo.constant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yng.demo.utils.VMLog;

/**
 * Created by lzan13 on 2015/7/4.
 * Activity 的基类，做一些子类公共的工作
 */
public class VMBaseActivity extends AppCompatActivity {

    protected String className = this.getClass().getSimpleName();

    // 当前界面的上下文菜单对象
    protected VMBaseActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VMLog.i("%s onCreate", className);
        activity = this;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        VMLog.i("%s onRestart", className);
    }

    @Override
    protected void onStart() {
        super.onStart();
        VMLog.i("%s onStart", className);
    }

    @Override
    protected void onResume() {
        super.onResume();
        VMLog.i("%s onResume", className);
    }

    @Override
    protected void onPause() {
        super.onPause();
        VMLog.i("%s onPause", className);
    }

    @Override
    protected void onStop() {
        super.onStop();
        VMLog.i("%s onStop", className);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VMLog.i("%s onDestroy", className);
        activity = null;
    }

    /**
     * 自定义 Activity 结束方法
     */
    protected void onFinish() {
        activity.finish();
    }
}
