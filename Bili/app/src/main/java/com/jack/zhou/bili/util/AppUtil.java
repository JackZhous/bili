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

    public static final int REQUEST_SUCCESS = 0;
    public static final int REQUEST_FAILED = 1;
    public static final int REQUEST_FERROR = 2;

    public static final int FLAG_ACTIVITY = 3;              //activity启动之间的标识
    public static final String CLOSED_ACTIVTY = "close_activity";

    //company ip -- 10.8.230.117   home ip -- 192.168.0.110
    private static final String BASE_URL = "http://192.168.189.129:8080/BiliServer/";

    public static final String LOGIN_VERIFY = BASE_URL + "login/";

}
