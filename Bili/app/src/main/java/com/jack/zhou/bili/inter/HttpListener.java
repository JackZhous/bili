package com.jack.zhou.bili.inter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;

/**
 * 回调工具类，将http返回的信息回调给app
 * Created by "jackzhous" on 2016/6/30.
 */
public final class HttpListener implements Response.ErrorListener, Response.Listener {

    private  BiliCallback listener;                     //将网络返回的数据回调给app
    public HttpListener(BiliCallback callback){
        listener = callback;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        JLog.default_print("http response error");
        listener.onError(AppUtil.REQUEST_FERROR, volleyError.getMessage());
    }

    @Override
    public void onResponse(Object o) {
        JLog.default_print("http response success");
        listener.onResponse(AppUtil.REQUEST_SUCCESS,o);
    }
}
