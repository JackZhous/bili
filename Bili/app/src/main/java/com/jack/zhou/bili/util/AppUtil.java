package com.jack.zhou.bili.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.jack.zhou.bili.R;

/**
 * Created by "jackzous" on 2016/6/16.
 */
public class AppUtil {


    private static int sdk_version = Build.VERSION.SDK_INT;

    public static final String TRUE = "true";

    public static final int REQUEST_SUCCESS = 0x01;
    public static final int REQUEST_FAILED = 0x02;
    public static final int REQUEST_FERROR = 0x03;

    public static final int FLAG_ACTIVITY = 0x04;              //activity启动之间的标识
    public static final String CLOSED_ACTIVTY = "close_activity";
    public static final String HEART_ACTION = "bili.heart.success";
    public static final String HEART_ERROR = "bili.hear.error";


    //短信模块标志量
    public static final int SMS_SUBMIT_VERIFICATION_CODE = 0x05;
    public static final int SMS_GET_VERIFICATION_CODE = 0x06;
    public static final int SMS_GET_SUPPORTED_COUNTRIES = 0x07;
    public static final int SMS_ERROR = 0x08;
    public static final int HANDLER_HEART_MSG = 0x09;                                   //心跳消息




    public static void integrationNotifcationBar(Activity activity){
        if(sdk_version >= Build.VERSION_CODES.KITKAT){
            setTranslucentStatus(true, activity);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);//通知栏所需颜色
        }
        activity.setTitle("");                              //让toolbar不显示app名
        //锁定屏幕方向为竖屏
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private static void setTranslucentStatus(boolean on, Activity activity) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}
