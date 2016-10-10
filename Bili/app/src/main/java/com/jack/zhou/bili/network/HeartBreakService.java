/*
 *         Copyright (C) 2016-2017 宙斯
 *         All rights reserved
 *
 *        filename :HeartBreakService
 *        description :
 *
 *         created by jackzhous at  11/07/2016 12:12:12
 *         http://blog.csdn.net/jackzhouyu
 */

package com.jack.zhou.bili.network;

import android.app.Service;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.jack.zhou.bili.inter.BiliCallback;
import com.jack.zhou.bili.inter.HttpListener;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HeartBreakService extends Service implements BiliCallback{

    private static final long HeartRate = 5000;                     //心跳频率
    private LocalBroadcastManager mBroadManager;
    private HttpListener listener;
    private Task heart_task;
    private IOManager ioManager;
    private HashMap<String, String> data = new HashMap<>();


    @Override
    public void onCreate() {
        super.onCreate();
        JLog.default_print("service onCreate");
        ioManager = IOManager.getInstance(this);
        listener = new HttpListener(this);
        heart_task = new Task(NetworkHelper.HEART_BREAK, sendHeart("this_is_my_token"), listener);
        ioManager.add_task_start(heart_task);                                                                        //第一次发送

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    //===============================================================================
    //心跳报文回调
    @Override
    public void onResponse(int code, Object msg) {
        Message handler_msg = Message.obtain();

        String message;
        if(code == AppUtil.REQUEST_SUCCESS){
            message = "heart network success " + msg;
        }else{
            message = "heart network failed " + msg;
        }
        handler_msg.arg1 = code;
        handler_msg.obj = message;
        handler_msg.what = AppUtil.HANDLER_HEART_MSG;
        JLog.default_print(message);
        sendHandler.sendMessage(handler_msg);
    }

    @Override
    public void onError(int code, Object msg) {
        Message handler_msg = Message.obtain();
        handler_msg.arg1 = code;
        handler_msg.what = AppUtil.HANDLER_HEART_MSG;
        String message = "heart network error, " + msg;
        handler_msg.obj = message;

        JLog.default_print(message);

        sendHandler.sendMessage(handler_msg);
    }

    //===============================================================================

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JLog.default_print("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        JLog.default_print("onDestroy");
        super.onDestroy();
    }

    /**
     * 构造心跳报文内容  时间戳和token
     * @param usertoken
     * @return
     */
    private HashMap<String, String> sendHeart(String usertoken){
        long time = System.currentTimeMillis();
        HashMap<String, String> map = new HashMap<String , String >();
        map.put("time", time+"");
        map.put("token", usertoken);

        return map;
    }

    /**
     * 发送心跳报文主体
     */
    private Runnable MyTask = new Runnable(){
        @Override
        public void run() {
            HashMap<String, String> map = sendHeart("thi_is_my_token");
            heart_task.setmData(map);
            IOManager.getInstance(HeartBreakService.this).add_task_start(heart_task);
        }
    };

    /**
     * 定时发送心跳报文
     */
    private Handler sendHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case AppUtil.HANDLER_HEART_MSG:
                    if(msg.arg1 == AppUtil.REQUEST_SUCCESS){                        //心跳校验成功，继续发送心跳
                        sendHandler.postDelayed(MyTask, HeartRate);
                    }else{                                                          //失败则停止
                        Toast.makeText(HeartBreakService.this, "心跳测试失败", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    };

}
