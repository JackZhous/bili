package com.jack.zhou.bili.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.inter.SMSModule;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;

import org.w3c.dom.Text;

import java.sql.Time;

import cn.smssdk.SMSSDK;


/**
 * 验证用户接口页面
 * Created by "jackzhous" on 2016/7/8.
 */
public class VerifySMS extends AppCompatActivity{

    private static final int TIME_TASK = 1;
    private int time = 59;

    private TextView tv_verofy_show;                            //验证提示信息
    private TextView tv_phone;                                  //验证手机号
    private TextView ed_verify;                                 //输入的验证码
    private TextView tv_time;                                   //剩余时间
    private String time_remain;
    private Button btn_next;                                    // 下一步 按键
    private SMSModule smsModule;
    private String country, phone_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smsModule = SMSModule.getInstance(this);
        smsModule.setMhandler(handler);
        smsModule.registerHandler();

        initLayoutResource();
    }


    private void initLayoutResource(){
        setContentView(R.layout.verifysms);

        Toolbar bar = (Toolbar)this.findViewById(R.id.verify_bar);
        bar.setTitle("验证手机号");
        setSupportActionBar(bar);

        tv_verofy_show = (TextView)findViewById(R.id.tv_info);
        tv_phone = (TextView)findViewById(R.id.tv_phone);
        ed_verify = (EditText)findViewById(R.id.ed_verify);
        tv_time = (TextView)this.findViewById(R.id.tv_remain_sec);
        btn_next = (Button)findViewById(R.id.btn_next);
        time_remain = getResources().getString(R.string.tv_remain_sec);

        //拿到手机号
        Intent intent = getIntent();
        country = intent.getStringExtra("country");
        phone_no = intent.getStringExtra("phone_no");
        tv_phone.setText("+"+country+phone_no);

        handler.sendEmptyMessageDelayed(TIME_TASK, 1000);                                                 //延迟一秒发送  用于验证码倒计时技术

        ed_verify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(ed_verify.getText())){
                    btn_next.setEnabled(false);
                }else{
                    btn_next.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case TIME_TASK:
                    String str_tv_time = null;
                    time--;
                    if(time > 0){
                        str_tv_time = time+time_remain;
                        handler.sendEmptyMessageDelayed(TIME_TASK, 1000);
                    }else{
                        str_tv_time = "重发验证码";
                        tv_time.setTextColor(Color.RED);
                    }
                    tv_time.setText(str_tv_time);
                    break;

                case AppUtil.SMS_SUBMIT_VERIFICATION_CODE:                  //短信验证成功，将关闭注册之前的界面，并开启个人中心界面
                    Intent intent = new Intent();
                    intent.putExtra(AppUtil.CLOSED_ACTIVTY, true);
                    setResult(AppUtil.FLAG_ACTIVITY, intent);

                    Intent intent1 = new Intent(VerifySMS.this, SetPasswdActivity.class);
                    intent1.putExtra("country", country);
                    intent1.putExtra("phone", phone_no);
                    startActivity(intent1);
                    finish();
                    break;

                case AppUtil.SMS_ERROR:
                    String error = (String)msg.obj;
                    JLog.default_print(error);
                    verify_error_animation(error);
                    break;
            }
        }
    };

    /**
     * 验证错误动画,以及设置响应的提示信息
     */
    private void verify_error_animation(String msg){
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(ed_verify,"translationX", 0F, -10F,20F,-20F,10F).setDuration(300);
        set.play(objectAnimator1);
        set.start();
        tv_verofy_show.setText(msg);
        tv_verofy_show.setTextColor(Color.RED);
    }

    /**
     * 按键监听  用于验证码校验
     * @param v
     */
    public void onVerifyClick(View v){

        SMSSDK.submitVerificationCode(country, phone_no, ed_verify.getText().toString().trim());
    }


    @Override
    protected void onStop() {
        super.onStop();

        smsModule.unregisterSMSHandler();
    }
}
