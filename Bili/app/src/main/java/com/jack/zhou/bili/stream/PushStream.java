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

package com.jack.zhou.bili.stream;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jack.zhou.bili.network.NetworkHelper;

import org.anyrtc.anyrtmp.HosterActivity;
import org.anyrtc.core.AnyRTMP;

/***********
 * author: jackzhous
 * file: PushStream.java
 * create date: 2016/10/10 17:19
 * desc:
 * 推流类
 ************/
public class PushStream {


    public PushStream(){
        AnyRTMP.Inst();   //开启子线程的handler
    }


    /**
     * 开始推流
     */
    public void startPushStream(Context context){
        Intent it = new Intent(context, HosterActivity.class);
        Bundle bd = new Bundle();
        bd.putString("rtmp_url", NetworkHelper.PUSH_ADDRESS);
        it.putExtras(bd);
        context.startActivity(it);
    }

}
