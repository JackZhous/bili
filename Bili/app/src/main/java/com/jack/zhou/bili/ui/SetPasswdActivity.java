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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.inter.BiliCallback;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by "sinlov" on 2016/7/13.
 */
public class SetPasswdActivity extends AppCompatActivity implements BiliCallback{

    private TextView tv_info;                                                   //提示信息
    private EditText ed_passwd;                                                 //密码
    private EditText ed_passwd1;                                                //确认密码
    private EditText ed_nickname;                                                  //昵称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initLayoutView();
    }



    private void initLayoutView(){
        setContentView(R.layout.activity_setuserinfo);

        Toolbar bar = (Toolbar)findViewById(R.id.setuserinfo_bar);
        bar.setTitle("设置密码");

        tv_info = (TextView)findViewById(R.id.tv_info);
        ed_passwd = (EditText)findViewById(R.id.password);
        ed_passwd1 = (EditText)findViewById(R.id.password1);
        ed_nickname = (EditText)findViewById(R.id.nickname);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    public void onDonePassword(View v){
        String passwd;
        String passwd1;
        String nickname;

        passwd = ed_passwd.getText().toString().trim();
        if(TextUtils.isEmpty(passwd) || passwd.length() < 6){
            tv_info.setText("密码输入不合法");
            ed_passwd.setFocusable(true);
            ed_passwd.requestFocus();
            return;
        }
        passwd1 = ed_passwd1.getText().toString().trim();
        if(TextUtils.isEmpty(passwd1) || passwd1.length() < 6){
            tv_info.setText("请确认两次输入的密码是否相同");
            ed_passwd1.setFocusable(true);
            ed_passwd1.requestFocus();
            return;
        }
        nickname = ed_nickname.getText().toString().trim();
        if(TextUtils.isEmpty(nickname) || nickname.length() < 3){
            tv_info.setText("昵称输入不合法");
            ed_nickname.setFocusable(true);
            ed_nickname.requestFocus();
            return;
        }
        /**
         * 构造注册信息
         */
        HashMap<String , String> data = new HashMap<>();
        data.put("passwd", passwd);                 //此处需要加密md5
        data.put("nickname", nickname);
        data.put("phone","18588431884");

    }

    @Override
    public void onResponse(int code, Object msg) {

    }

    @Override
    public void onError(int code, String msg) {

    }
}
