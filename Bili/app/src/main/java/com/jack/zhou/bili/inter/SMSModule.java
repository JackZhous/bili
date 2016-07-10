package com.jack.zhou.bili.inter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 短信模块类，由于提交手机号发送验证码在RegisterActivity， 而输入验证码校验在另一个类里面
 * 所以需要另起一个短信工具类，将收到的消息分别发送给其他的类
 * 注意：使用之前必须要先设置handler   发送给谁
 * Created by jackzhous on 16-7-10.
 */
public class SMSModule {

    private static SMSModule instance;
    private Handler mhandler;
    private String appkey = "14ad7047d60ee";
    private String appsecrect = "4f8a62e8e4d3595b27a1ddc31ea3be47";

    private SMSModule(Context context){
        SMSSDK.initSDK(context, appkey, appsecrect);
    }


    /**
     * 初始化短信sdk
     */
    public void registerHandler(){

        SMSSDK.registerEventHandler(handler);
    }


    public static SMSModule getInstance(Context context){
        if(instance == null){
            instance = new SMSModule(context);
        }

        return instance;
    }


    public void setMhandler(Handler handler){
        this.mhandler = handler;
    }

    /**
     * 这个有可能不是在主线程中运行的 ---   注意了
     */
    private EventHandler handler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, final Object data) {
            if (SMSSDK.RESULT_COMPLETE == result) {
                JLog.default_print("短信回调完成");
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    JLog.default_print("短信验证码提交成功 ");
                    mhandler.sendEmptyMessage(AppUtil.SMS_SUBMIT_VERIFICATION_CODE);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {              //获取短信验证码成功，可以进行回调
                    JLog.default_print("获取短信验证码成功");
                    mhandler.sendEmptyMessage(AppUtil.SMS_GET_VERIFICATION_CODE);

                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    JLog.default_print("获取短信支持的国家列表");
                    mhandler.sendEmptyMessage(AppUtil.SMS_GET_SUPPORTED_COUNTRIES);
                }
            } else {
                JLog.default_print("短信sdk程序异常");
                Message msg = mhandler.obtainMessage();
                msg.what = AppUtil.SMS_ERROR;
                try {
                    msg.obj = new JSONObject(((Throwable)data).getMessage()).getString("detail");
                }catch (JSONException e) {
                    msg.obj = "请检查输入信息";
                }
                mhandler.sendMessage(msg);
                ((Throwable)data).printStackTrace();
            }
        }
    };


    public void unregisterSMSHandler(){
        SMSSDK.unregisterAllEventHandler();
    }


}
