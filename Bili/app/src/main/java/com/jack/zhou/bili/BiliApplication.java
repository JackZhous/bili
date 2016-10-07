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

package com.jack.zhou.bili;

import android.app.Application;

import com.qiniu.pili.droid.streaming.StreamingEnv;

/**
 * author: jackzhous
 * file : BiliApplication.java
 * create time: 16-10-7 下午5:49
 * desc:
 **/
public class BiliApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        StreamingEnv.init(getApplicationContext());
    }
}
