/*
 *         Copyright (C) 2016-2017 宙斯
 *         All rights reserved
 *
 *        filename :Class4
 *        description :
 *
 *         created by jackzhous at  11/07/2016 12:12:12
 *         http://blog.csdn.net/jackzhouyu
 */

package com.jack.zhou.bili.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/***********
 * author: jackzhous
 * file: NetworkHelper.java
 * create date: 2016/10/10 17:00
 * desc:
 * 网络工具辅助类
 ************/
public class NetworkHelper {

    //company ip -- 10.8.230.117   home ip -- 192.168.0.110
    public static final String HTTP_BASE_URL = "http://192.168.0.105:8080/BiliServer/";

    public static final String LOGIN_VERIFY = HTTP_BASE_URL + "login/";
    public static final String HEART_BREAK = HTTP_BASE_URL + "heart";
    public static final String REGISTER = HTTP_BASE_URL + "register";
    public static final String VERIFY_TOKEN = HTTP_BASE_URL + "VerifyToken";
    public static final String GET_IMAGE = HTTP_BASE_URL + "refreshImage";
    public static final String GET_PUSH_VIDEO_URL = HTTP_BASE_URL + "GetVideoPushAddress";

    public static final String RTMP_BASE_URL = "rtmp://192.168.0.105:1395/bili/";

    public static final String PUSH_ADDRESS = RTMP_BASE_URL + "push";


    public static final String GET_VIDEO_PUSH_ADDRESS = "push_address";
    /**
     * 检查网络是否连接
     * @param context
     * @return
     */
    public static boolean isConnectNetwork(Context context){

        Context context1 = context.getApplicationContext();
        ConnectivityManager cs = (ConnectivityManager)context1.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = cs.getActiveNetworkInfo();

        if(info != null && info.isConnected()){
            return true;
        }

        return false;
    }

}
