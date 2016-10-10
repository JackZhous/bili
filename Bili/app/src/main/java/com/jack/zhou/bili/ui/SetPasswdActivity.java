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

package com.jack.zhou.bili.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.inter.BiliCallback;
import com.jack.zhou.bili.inter.HttpListener;
import com.jack.zhou.bili.network.IOManager;
import com.jack.zhou.bili.network.NetworkHelper;
import com.jack.zhou.bili.network.Task;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;
import com.jack.zhou.bili.util.JNIClass;
import com.jack.zhou.bili.util.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by "jackzhous" on 2016/7/13.
 */
public class SetPasswdActivity extends AppCompatActivity implements BiliCallback{

    private TextView tv_info;                                                   //提示信息
    private EditText ed_passwd;                                                 //密码
    private EditText ed_passwd1;                                                //确认密码
    private EditText ed_nickname;                                                  //昵称
    private Toolbar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtil.integrationNotifcationBar(this);
        initLayoutView();
    }



    private void initLayoutView(){
        setContentView(R.layout.activity_setuserinfo);

        bar = (Toolbar)findViewById(R.id.setuserinfo_bar);
        bar.setTitle("设置密码");

        tv_info = (TextView)findViewById(R.id.tv_info);
        ed_passwd = (EditText)findViewById(R.id.password);
        ed_passwd1 = (EditText)findViewById(R.id.password1);
        ed_nickname = (EditText)findViewById(R.id.ed_nickname);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 设置密码完成后的button监听
     * @param v
     */
    public void onDonePassword(View v){
        String passwd;
        String passwd1;
        String nickname;

        passwd = ed_passwd.getText().toString().trim();
        if(TextUtils.isEmpty(passwd) || passwd.length() < 6){
            tv_info.setTextColor(Color.RED);
            tv_info.setText("密码输入不合法");
            ed_passwd.setFocusable(true);
            ed_passwd.requestFocus();
            return;
        }
        passwd1 = ed_passwd1.getText().toString().trim();
        if(TextUtils.isEmpty(passwd1) || passwd1.length() < 6){
            tv_info.setTextColor(Color.RED);
            tv_info.setText("请确认两次输入的密码是否相同");
            ed_passwd1.setFocusable(true);
            ed_passwd1.requestFocus();
            return;
        }
        nickname = ed_nickname.getText().toString().trim();
        if(TextUtils.isEmpty(nickname) || nickname.length() < 3){
            tv_info.setTextColor(Color.RED);
            tv_info.setText("昵称输入不合法");
            ed_nickname.setFocusable(true);
            ed_nickname.requestFocus();
            return;
        }
        /**
         * 构造注册信息
         */
        HashMap<String , String> data = new HashMap<>();
        data.put("task_flag", "register_user");
        data.put("password", JNIClass.getMD5Char(passwd));                 //此处需要加密md5
        data.put("nickname", nickname);
        data.put("phone","18588431884");

        HttpListener listener = new HttpListener(this);
        Task task = new Task(NetworkHelper.REGISTER, data, listener);
        IOManager.getInstance(this).add_task_start(task);

    }

    @Override
    public void onResponse(int code, Object msg) {              //注册成功 回调json都是一个map的结构体
        if(code == AppUtil.REQUEST_SUCCESS){
            Toast.makeText(SetPasswdActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            try {
                /**
                 * 登陆或注册成功会获得一个token  , 保存在SharedPreference里面，
                 * 以后每次启动的时候就会去取出这个token拿到服务端去校验，如果校验通过就默认是登陆的
                 * 心跳报文也是发这个token和时间戳，拿到服务端去比较，时间戳差值来确定token的时效性
                 */
                JSONObject json = new JSONObject(msg.toString());
                String token = json.getString("token");
                SharedPreferenceUtil util = SharedPreferenceUtil.getInstance(this.getApplicationContext());
                util.init();
                util.putString("token", token);
                util.putString("icon_url", json.getString("icon_url"));
                util.putString("nickname", json.getString("nickname"));
                util.putString("login_flag", "ok");
                JLog.default_print("register back : " + msg);
                Intent intent = new Intent(SetPasswdActivity.this, RegisterSuccess.class);
                startActivity(intent);
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(SetPasswdActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onError(int code, Object obj) {
        JLog.default_print("set password error" + obj);
        finish();
    }
}
