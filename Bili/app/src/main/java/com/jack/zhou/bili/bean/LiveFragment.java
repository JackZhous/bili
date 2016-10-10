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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.network.NetworkHelper;
import com.jack.zhou.bili.stream.PushStream;

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
                showDialog("确定要进行直播吗？");
                break;

            case R.id.tv_watch_live:            //观看直播

                break;
        }
    }

    public void showDialog(String message){
        new AlertDialog.Builder(context).setTitle("提示")
                                        .setMessage(message)
                                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                PushStream.getInstance().startPushStream(context);
                                            }
                                        })
                                        .setPositiveButton("取消", null).show();
    }


}
