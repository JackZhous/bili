package com.jack.zhou.bili.network;


import com.jack.zhou.bili.util.JLog;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 线程管理类
 * Created by jackzhous on 16-6-16.
 */
public class IOManager {
    private static IOManager instance = null;

    private IOManager(){

    }

    public static synchronized IOManager getInstance(){
        if(null == instance){
            instance = new IOManager();
        }

        return instance;
    }


    public void httpGet(){
        String url_str = "http://192.168.0.110:8080/BiliServer/servlet/testservlet?params=hellowrld";
        URL url = null;
        String data="params=hellowrld";
        try{
            JLog.default_print("网络连接开始");
            url = new URL(url_str);
            HttpURLConnection connection  = (HttpURLConnection)url.openConnection();
            JLog.default_print("网络连接开始111111");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "text/*;charset=utf-8");
            connection.setRequestProperty("Charset", "utf-8");
            JLog.default_print("网络连接开始222222");
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(data.getBytes());
            JLog.default_print("网络连接开始333333");
            out.flush();
            out.close();
        }catch(Exception e){
            e.printStackTrace();;
        }

    }

}
