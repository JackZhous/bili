package com.jack.zhou.bili.exception;

import android.app.Activity;
import android.util.Log;

import com.jack.zhou.bili.util.JLog;

/**
 * 异常捕获类
 * Created by "jackzhous" on 2016/7/5.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler{

    private static final String TAG = "CrashHandler";
    private static CrashHandler instance;
    private Activity mActivity;
    private Thread.UncaughtExceptionHandler mDefaultExcHandler;


    private CrashHandler(Activity activity){
        this.mActivity = activity;

        mDefaultExcHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 初始化奔溃模块
     * @param activity
     */
    public void init(Activity activity){
        this.mActivity = activity;
        mDefaultExcHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    public static CrashHandler getInstance(Activity activity){
        if(null == instance){
            instance = new CrashHandler(activity);
        }

        return instance;
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        JLog.print_error(TAG, ex.getMessage(), ex);


    }
}
