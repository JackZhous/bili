package com.jack.zhou.bili.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.util.AppUtil;

/**
 * 用户注册界面
 * Created by "jackzhous" on 2016/7/5.
 */
public class RegisterActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtil.integrationNotifcationBar(this);
        initLayoutResouce();
    }


    private void initLayoutResouce(){

        this.setContentView(R.layout.activity_register);
        Toolbar login_bar = (Toolbar) this.findViewById(R.id.login_bar);
        setSupportActionBar(login_bar);
        login_bar.setTitle("注册账号");

        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);       //使用toolbar左上角图标可点击可显示
            supportActionBar.setTitle("注册账号");
        }
        //点击返回  回到主页
        login_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
