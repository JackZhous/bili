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
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.jack.zhou.bili.inter.BiliCallback;
import com.jack.zhou.bili.inter.HttpListener;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;

import java.util.HashMap;

public class HeartBreakService extends Service implements BiliCallback{

    private static final long HeartRate = 5000;                     //心跳频率
    private LocalBroadcastManager mBroadManager;


    @Override
    public void onCreate() {
        super.onCreate();
        JLog.default_print("service onCreate");
        mBroadManager = LocalBroadcastManager.getInstance(this);
        HashMap<String , String> msg = new HashMap<String, String>();
        msg.put("heart", "heart");
        HttpListener listener = new HttpListener(this);
        Task heart_task = new Task(AppUtil.HEART_BREAK, msg, listener);
        IOManager.getInstance(this).add_task_start(heart_task);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onResponse(int code, Object msg) {

    }

    @Override
    public void onError(int code, String msg) {

    }

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
}
