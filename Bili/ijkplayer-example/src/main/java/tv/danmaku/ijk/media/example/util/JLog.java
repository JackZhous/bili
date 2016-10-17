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

package tv.danmaku.ijk.media.example.util;

import android.util.Log;

/***********
 * author: jackzhous
 * file: JLog.java
 * create date: 2016/9/26 14:19
 * desc:
 ************/
public class JLog {
    private static boolean debug = true;



    public static void i(String TAG, String msg){
        if(debug){
            Log.i("jackzhous", "--- " + TAG + " --- msg: " + msg + " ----");
        }

    }
}
