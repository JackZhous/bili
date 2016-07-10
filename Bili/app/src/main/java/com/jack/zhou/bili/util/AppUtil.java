package com.jack.zhou.bili.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.view.WindowManager;

/**
 * Created by "jackzous" on 2016/6/16.
 */
public class AppUtil {

    private static int sdk_version = Build.VERSION.SDK_INT;

    public static void integrationNotifcationBar(Activity activity){
        if(sdk_version >= Build.VERSION_CODES.KITKAT){
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        //锁定屏幕方向为竖屏
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static final int REQUEST_SUCCESS = 0x01;
    public static final int REQUEST_FAILED = 0x02;
    public static final int REQUEST_FERROR = 0x03;

    public static final int FLAG_ACTIVITY = 0x04;              //activity启动之间的标识
    public static final String CLOSED_ACTIVTY = "close_activity";

    //短信模块标志量
    public static final int SMS_SUBMIT_VERIFICATION_CODE = 0x05;
    public static final int SMS_GET_VERIFICATION_CODE = 0x06;
    public static final int SMS_GET_SUPPORTED_COUNTRIES = 0x07;
    public static final int SMS_ERROR = 0x08;

    //company ip -- 10.8.230.117   home ip -- 192.168.0.110
    private static final String BASE_URL = "http://192.168.189.129:8080/BiliServer/";

    public static final String LOGIN_VERIFY = BASE_URL + "login/";

}
