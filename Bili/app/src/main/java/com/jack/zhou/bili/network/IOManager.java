package com.jack.zhou.bili.network;


import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jack.zhou.bili.inter.BiliCallback;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 线程管理类
 * Created by jackzhous on 16-6-16.
 */
public class IOManager {
    private static IOManager instance = null;

    private Activity activity;
    private RequestQueue queue;

    public  static final String url = "http://192.168.0.110:8080/BiliServer/servlet/testservlet";
    private IOManager(Activity activity){
        this.activity = activity;
        queue = Volley.newRequestQueue(activity.getApplicationContext());
    }

    public static synchronized IOManager getInstance(Activity activity){
        if(null == instance){
            instance = new IOManager(activity);
        }

        return instance;
    }

    /**
     * Get方法请求
     * @param params
     * @param url
     * @param listener
     */
    public void httpGet(String params, String url, final BiliCallback listener){

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                listener.onResponse(AppUtil.REQUEST_SUCCESS, s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onResponse(AppUtil.REQUEST_FAILED, volleyError.getMessage());
                JLog.default_print("error " + volleyError);
                volleyError.printStackTrace();
            }
        });
        queue.add(request);
    }

    /**
     * post请求
     * @param map
     * @param url
     * @param listener
     */
    public void httpPost(Map<String, String> map, String url, final BiliCallback listener){
        JSONObject jsonObject = new JSONObject(map);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String ret = "";
                if(jsonObject == null){
                    ret = "null";
                }else{
                    ret = jsonObject.toString();
                }
                listener.onResponse(AppUtil.REQUEST_SUCCESS, ret);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onResponse(AppUtil.REQUEST_FAILED, volleyError.getMessage());
                volleyError.printStackTrace();
            }
        });

        queue.add(request);
    }


}
