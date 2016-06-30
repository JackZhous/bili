package com.jack.zhou.bili.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.jack.zhou.bili.inter.BiliCallback;
import com.jack.zhou.bili.inter.HttpListener;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by "jackzhous" on 2016/6/30.
 */
public class Task extends StringRequest{

    private Object mData;                            //传递的数据
    private HttpListener mCallback;                  //回调接口
    private String url;

    public Task(String url, byte[] mData, HttpListener listener){

        this(Method.POST, url, listener, listener);
        this.mData = mData;
        this.mCallback = listener;
        this.url = url;
    }

    public Task(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public Task(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected void deliverResponse(String response) {
        super.deliverResponse(response);
    }


    /**
     * 对服务器传回的数据进行解码或者编码
     * @param response
     * @return
     */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {

        try {
            response.headers.put("HTTP.CONTENT_TYPE", "utf-8");

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

}
