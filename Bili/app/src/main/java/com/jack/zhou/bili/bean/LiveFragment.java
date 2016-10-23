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

package com.jack.zhou.bili.bean;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.inter.BiliCallback;
import com.jack.zhou.bili.inter.HttpListener;
import com.jack.zhou.bili.network.IOManager;
import com.jack.zhou.bili.network.NetworkHelper;
import com.jack.zhou.bili.network.Task;
import com.jack.zhou.bili.stream.PushStream;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;
import com.jack.zhou.bili.util.JNIClass;
import com.jack.zhou.bili.util.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import tv.danmaku.ijk.media.example.activities.VideoActivity;

/**
 * 直播选项卡页面
 * Created by "jackzhous" on 2016/7/21.
 */
public class LiveFragment extends Fragment implements View.OnClickListener{

    private Context context;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        boolean connect = NetworkHelper.isConnectNetwork(context);

        View view = initLayoutResouce(inflater,container,connect);
        return view;
    }


    private View initLayoutResouce(LayoutInflater inflater, ViewGroup container, boolean isConnected){
        TextView tv_live, tv_watch_live;
        int resourceId = isConnected ? R.layout.live_fragment : R.layout.live_fragment;
//        int resourceId = isConnected ? R.layout.live_fragment : R.layout.activity_network_error;

        View view = inflater.inflate(resourceId, container, false);
       /* if(!isConnected){
            return view;
        }*/

        tv_live = (TextView)view.findViewById(R.id.tv_live_on);
        tv_watch_live = (TextView)view.findViewById(R.id.tv_watch_live);
        tv_live.setOnClickListener(this);
        tv_watch_live.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_live_on:               //直播
                if(getLiveVideoUrl()){
                    showDialog("确定要进行直播吗？");
                }else{
                    Toast.makeText(context, "请先登陆", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.tv_watch_live:            //观看直播

                break;
        }
    }

    /**
     * 获取直播推流地址
     * @return
     */
    private boolean getLiveVideoUrl(){
        SharedPreferenceUtil util = SharedPreferenceUtil.getInstance(context);
        String login_flag = util.getString(SharedPreferenceUtil.LOGIN_FLAG);
        if(login_flag != null && login_flag.equals("ok")){
            String token = util.getString(SharedPreferenceUtil.TOKEN);

            HashMap<String, String> map = new HashMap<String, String>();
            map.put(SharedPreferenceUtil.TOKEN, token);
            map.put("task_flag", NetworkHelper.GET_VIDEO_PUSH_ADDRESS);
            HttpListener listener = new HttpListener(liveVideoUrlListener);
            Task task = new Task(NetworkHelper.GET_PUSH_VIDEO_URL, map, listener);

            IOManager.getInstance(context).add_task_start(task);
            return true;
        }

        return false;

    }




    private String uid;
    private String nickname;


    public String getUid() {
        return uid;
    }

    public String getNickname() {
        return nickname;
    }

    private BiliCallback liveVideoUrlListener = new BiliCallback() {
        @Override
        public void onResponse(int code, Object msg) {
            String url = "";
            if(code == AppUtil.REQUEST_SUCCESS){
                try {
                    JSONObject json = new JSONObject(String.valueOf(msg));
                    nickname = json.optString("nickname");
                    uid = json.optString("uid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onError(int code, Object obj) {

        }
    };


    public void showDialog(String message){
        new AlertDialog.Builder(context).setTitle("提示")
                                        .setMessage(message)
                                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                try {
                                                    Thread.sleep(1500);
                                                } catch (Exception e) {

                                                }
                                                PushStream.getInstance().startPushStream(context, nickname, uid);
                                            }
                                        })
                                        .setPositiveButton("取消", null).show();
    }


}
