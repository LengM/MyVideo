package com.lovcreate.talkingbox.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.lovcreate.core.utils.ScreenAdaptation;

/**
 * 设置Volley随应用启动创建单独的请求序列
 * Created by Albert.Ma on 2017/6/21 0021.
 */

public class MyApplication extends Application {

    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        initVolley();
        initEaseUI();
        // 初始化屏幕适配 需要传入ui设计给的大小
        new ScreenAdaptation(this, 720,1280).register();
    }

    ///////////////////////////////////    环信easeui    ////////////////////////////////////

    /**
     * EaseUI初始化
     */
    private void initEaseUI() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

        //初始化
        EaseUI.getInstance().init(getApplicationContext(), options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源 log:ONE SDK
        EMClient.getInstance().setDebugMode(true);
    }
    ///////////////////////////////////    Volley    ////////////////////////////////////

    /**
     * 设置Volley随应用启动创建单独的请求序列
     */
    private void initVolley() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

}
