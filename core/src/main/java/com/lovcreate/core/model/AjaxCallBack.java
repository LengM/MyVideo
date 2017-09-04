package com.lovcreate.core.model;

import com.google.gson.Gson;
import com.lovcreate.core.utils.Logcat;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 封装okHttp回调
 * Created by Albert.Ma on 2017/6/19 0019.
 */

public abstract class AjaxCallBack extends Callback<AjaxResponse> {

    public AjaxCallBack() {
    }

    @Override
    public void onBefore(Request request) {
        Logcat.i("OkHttp:onBefore");
        super.onBefore(request);
    }

    @Override
    public void onAfter() {
        super.onAfter();
        Logcat.i("OkHttp:onAfter");
    }

    @Override
    public AjaxResponse parseNetworkResponse(Response response) throws Exception {
        String data = response.body().string();
        Logcat.i("OkHttp:获取数据:" + data);
        AjaxResponse ajaxResponse = new Gson().fromJson(data, AjaxResponse.class);
        return ajaxResponse;
    }

}
