package com.jack.zhou.bili.network;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jack.zhou.bili.inter.BiliCallback;
import com.jack.zhou.bili.inter.HttpListener;
import com.jack.zhou.bili.util.JLog;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 线程管理类
 * Created by jackzhous on 16-6-16.
 */
public class IOManager {
    private static IOManager instance = null;

    private Context mContext;
    private RequestQueue queue;                 //网络请求

    private IOManager(Context activity){
        mContext = activity.getApplicationContext();
        queue = Volley.newRequestQueue(mContext);
    }

    public static synchronized IOManager getInstance(Context activity){
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
                listener.onResponse(HttpListener.REQUEST_SUCCESS, s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onResponse(HttpListener.REQUEST_FAILED, volleyError.getMessage());
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
    public void httpPost(final Map<String, String> map, String url, final BiliCallback listener){
        JSONObject json = new JSONObject();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                JLog.default_print("收到服务端消息了，真高兴");
                JLog.default_print("s" + s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    response.headers.put("HTTP.CONTENT_TYPE", "utf-8");
//                String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    String jsonString = new String(response.data,"utf-8");

                    return Response.success(new JSONObject(jsonString).toString(), HttpHeaderParser.parseCacheHeaders(response));
                }
                catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
                catch (org.json.JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };

        queue.add(request);
    }



    public void add_task_start(Task task){
        if(null == queue){
            queue = Volley.newRequestQueue(mContext);
        }
        JLog.default_print("start task");
        queue.add(task);
    }

    /**
     * volley缓存类
     */
    private class BitmapCache implements ImageLoader.ImageCache{

        private LruCache<String, Bitmap> cache;

        public BitmapCache(){
            cache = new LruCache<String, Bitmap>(10 * 1024 * 1024){

                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {

            return cache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url,bitmap);
        }
    }

    /**
     * 图片下载任务
     * @param url
     */
    public void startImageTask(String url, ImageLoader.ImageListener listener){
        ImageLoader loader = new ImageLoader(queue, new BitmapCache());
        String final_url = NetworkHelper.HTTP_BASE_URL + url;

        JLog.default_print("final url " + final_url);
        loader.get(final_url, listener);
    }


}
