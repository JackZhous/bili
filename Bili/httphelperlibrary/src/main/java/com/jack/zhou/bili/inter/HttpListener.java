package com.jack.zhou.bili.inter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jack.zhou.bili.util.JLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * 回调工具类，将http返回的信息回调给app
 * Created by "jackzhous" on 2016/6/30.
 */
public final class HttpListener implements Response.ErrorListener, Response.Listener {

    public static final int REQUEST_SUCCESS = 0x01;
    public static final int REQUEST_FAILED = 0x02;
    public static final int REQUEST_FERROR = 0x03;


    private WeakReference<BiliCallback> weakReference_BCallBack;                                        //将网络返回的数据回调给app
    private static final String OK = "ok";
    private static final String FAIL = "fail";

    public HttpListener(BiliCallback callback){
        weakReference_BCallBack = new WeakReference<>(callback);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        JLog.default_print("http response error \n");
        JLog.print_error("jackzhous", volleyError.getMessage(), volleyError);
        volleyError.printStackTrace();
        BiliCallback listener = weakReference_BCallBack.get();
        if(listener != null){
            listener.onError(REQUEST_FERROR, volleyError);
        }
    }

    /**
     *
     * @param o  o是直接返回的json数据  是一个字符串，格式为“result”:ok|fail, "message":"描述信息"
     * 改方法的作用，将Http回调的标识和信息转换为客户端定义的标识的信息，避免直接接触http的原生信息
     */
    @Override
    public void onResponse(Object o) {
        JLog.default_print("http response success " + o);
        int http_status = REQUEST_FAILED;

        JSONObject json;
        try {
            json = new JSONObject((String)o);

            if(OK.equals(json.get("result"))){
                http_status = REQUEST_SUCCESS;

            }else{
                http_status = REQUEST_FAILED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        BiliCallback listener = weakReference_BCallBack.get();
        if(listener != null){
            listener.onResponse(http_status,o);
        }

    }
}
