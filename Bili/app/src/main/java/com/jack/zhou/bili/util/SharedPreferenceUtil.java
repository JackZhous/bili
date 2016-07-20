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

package com.jack.zhou.bili.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * sharedPreference里面存放：
 *  token icon_url nickname login_flag标志位
 *  login_flag在登录和首次启动的时候，成功后会置为true
 *  结束或者注销的时候会置为false
 *  应用中间则根据该状态，决定是否继续取token icon_url等
 * Created by "jackzhous" on 2016/7/14.
 */
public class SharedPreferenceUtil {

    private static SharedPreferenceUtil mInstance;
    private Context context;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPreferenceUtil(Context context){
        this.context = context;
    }


    public static SharedPreferenceUtil getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPreferenceUtil(context);
        }

        return mInstance;
    }

    public void init(){
        sharedPreferences = context.getSharedPreferences("mybili", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void putString(String key, String msg){
        if(TextUtils.isEmpty(msg) || TextUtils.isEmpty(key)){
            JLog.default_print("save shared preferences failed");
            return;
        }

        editor.putString(key, msg);
        editor.commit();
    }


    public String getString(String key){
        if(TextUtils.isEmpty(key)){
            JLog.default_print("get shared preferences failed");
            return null;
        }

        String value = sharedPreferences.getString(key,"");
        return value;
    }

}
