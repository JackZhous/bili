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

package com.jack.zhou.bili.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.inter.BiliCallback;
import com.jack.zhou.bili.inter.HttpListener;
import com.jack.zhou.bili.network.IOManager;
import com.jack.zhou.bili.network.Task;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;
import com.jack.zhou.bili.util.SharedPreferenceUtil;

import java.util.HashMap;

/**
 * Created by jackzhous on 16-7-20.
 */
public class BiliUserInfo extends AppCompatActivity implements BiliCallback{


    SharedPreferenceUtil util;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_layout);

        util = SharedPreferenceUtil.getInstance(this.getApplicationContext());
        util.init();
    }

    public void onLogout(View v){
        HashMap<String, String> data = new HashMap<>();
        data.put("task_flag", "logout");
        data.put("token", util.getString("token"));
        HttpListener listener = new HttpListener(this);
        Task task = new Task(AppUtil.VERIFY_TOKEN, data, listener);
        IOManager.getInstance(this).add_task_start(task);
    }

    @Override
    public void onResponse(int code, Object msg) {
        if(code == AppUtil.REQUEST_SUCCESS){
            util.putString("login_flag", "false");
            util.putString("token","");
            util.putString("nickname", "未登录");
            util.putString("icon_url","");
            this.finish();
        }else{
            Toast.makeText(BiliUserInfo.this, "注销失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onError(int code, Object obj) {
        JLog.default_print("http error " + obj);
    }
}
