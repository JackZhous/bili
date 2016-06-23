package com.jack.zhou.bili.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.view.WindowManager;

/**
 * Created by "sinlov" on 2016/6/16.
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

    //company ip -- 10.8.230.117   home ip -- 192.168.0.110
    private static final String BASE_URL = "http://10.8.230.117:8080/BiliServer/";

    public static final String LOGIN_VERIFY = BASE_URL + "login/";

}
