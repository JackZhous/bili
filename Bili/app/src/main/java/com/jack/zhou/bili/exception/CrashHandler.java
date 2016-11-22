package com.jack.zhou.bili.exception;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.jack.zhou.bili.util.FileUtil;
import com.jack.zhou.bili.util.JLog;



/**
 * 异常捕获类
 * Created by "jackzhous" on 2016/7/5.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler{

    private static final String TAG = "CrashHandler";
    private static CrashHandler instance;
    private Thread.UncaughtExceptionHandler mDefaultExcHandler;


    private CrashHandler(){

        mDefaultExcHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 初始化奔溃模块
     */
    public void init(){
        mDefaultExcHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    public static synchronized CrashHandler getInstance(){
        if(null == instance){
            instance = new CrashHandler();
        }

        return instance;
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        JLog.print_error(TAG, ex.getMessage(), ex);

        //开发者没处理，就交给系统默认的捕获器执行处理
        if((!handlerException(ex)) && mDefaultExcHandler != null){
            mDefaultExcHandler.uncaughtException(thread, ex);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());*/
        }
    }

    /**
     * 开发者异常执行处理
     * @param ex
     * @return
     */
    private boolean handlerException(Throwable ex){
        if(null == ex){

            return false;
        }
        //保存文件或者上传服务器后台分析
        FileUtil.getInstance().saveFile("crash.txt",ex.getLocalizedMessage());
        return false;
    }
}
