package com.jack.zhou.bili.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jack.zhou.bili.R;
import com.jack.zhou.bili.inter.BiliCallback;
import com.jack.zhou.bili.inter.HttpListener;
import com.jack.zhou.bili.network.IOManager;
import com.jack.zhou.bili.network.Task;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;
import com.jack.zhou.bili.util.JNIClass;
import com.jack.zhou.bili.util.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by "jackzhous" on 2016/6/16.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, BiliCallback{
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private EditText username;
    private EditText passwd;
    private ImageView login_left_image;
    private ImageView login_right_image;

    private Button login;
    private Button regisrer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtil.integrationNotifcationBar(this);
        setContentView(R.layout.activity_login);
        initView();

    }


    /**
     * 初始化组件
     */
    private void initView() {
        Toolbar login_bar = (Toolbar) this.findViewById(R.id.login_bar);
        setSupportActionBar(login_bar);
        login_bar.setTitle("未登录");

        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);       //使用toolbar左上角图标可点击可显示
            supportActionBar.setTitle("未登录");
        }
        //点击返回  回到主页
        login_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        login_left_image = (ImageView)this.findViewById(R.id.login_left);
        login_right_image = (ImageView)this.findViewById(R.id.login_right);
        username = (EditText)this.findViewById(R.id.username);
        passwd = (EditText)this.findViewById(R.id.passwd);
        login = (Button)this.findViewById(R.id.login);
        regisrer = (Button)this.findViewById(R.id.regist);
        login.setOnClickListener(this);
        regisrer.setOnClickListener(this);

        final Drawable drawable_username_default = ContextCompat.getDrawable(this, R.drawable.ic_login_username_default);
        Drawable.ConstantState state_user = drawable_username_default.getConstantState();
        final Drawable drawable_username_tint = (state_user == null) ? drawable_username_default : state_user.newDrawable().mutate();
        DrawableCompat.setTint(drawable_username_tint, ContextCompat.getColor(this, R.color.colorPrimaryDark));

        final Drawable drawable_passwd_default = ContextCompat.getDrawable(this, R.drawable.ic_login_password_default);
        Drawable.ConstantState state_passwd = drawable_passwd_default.getConstantState();
        final Drawable drawable_passwd_tint = (state_passwd == null) ? drawable_passwd_default : state_passwd.newDrawable().mutate();
        DrawableCompat.setTint(drawable_passwd_tint, ContextCompat.getColor(this, R.color.colorPrimaryDark));

        drawable_passwd_default.setBounds(0, 0, drawable_passwd_default.getIntrinsicWidth() - 35, drawable_passwd_default.getIntrinsicHeight() - 35);
        drawable_username_default.setBounds(0, 0, drawable_username_default.getIntrinsicWidth() - 35, drawable_username_default.getIntrinsicHeight() - 35);
        drawable_passwd_tint.setBounds(0, 0, drawable_passwd_tint.getIntrinsicWidth() - 35, drawable_passwd_tint.getIntrinsicHeight() - 35);
        drawable_username_tint.setBounds(0, 0, drawable_username_tint.getIntrinsicWidth() - 35, drawable_username_tint.getIntrinsicHeight() - 35);          //setBounds 指定一个矩形区域  在此区域内作画
        username.setCompoundDrawables(drawable_username_default, null, null, null);
        passwd.setCompoundDrawables(drawable_passwd_default, null, null, null);
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    drawable_username_tint.setBounds(0, 0, drawable_username_tint.getIntrinsicWidth() - 35, drawable_username_tint.getIntrinsicHeight() - 35);
                    username.setCompoundDrawables(drawable_username_tint, null, null, null);
                } else {
                    drawable_username_default.setBounds(0, 0, drawable_username_default.getIntrinsicWidth() - 35, drawable_username_default.getIntrinsicHeight() - 35);
                    username.setCompoundDrawables(drawable_username_default, null, null, null);
                }
            }
        });

        passwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    drawable_passwd_tint.setBounds(0, 0, drawable_passwd_tint.getIntrinsicWidth() - 35, drawable_passwd_tint.getIntrinsicHeight() - 35);
                    passwd.setCompoundDrawables(drawable_passwd_tint, null, null, null);
                    login_left_image.setImageDrawable(ContextCompat.getDrawable(LoginActivity.this, R.drawable.ic_22_hide));
                    login_right_image.setImageDrawable(ContextCompat.getDrawable(LoginActivity.this,R.drawable.ic_33_hide));
                } else {
                    login_left_image.setImageDrawable(ContextCompat.getDrawable(LoginActivity.this, R.drawable.ic_22));
                    login_right_image.setImageDrawable(ContextCompat.getDrawable(LoginActivity.this,R.drawable.ic_33));
                    drawable_passwd_default.setBounds(0, 0, drawable_passwd_default.getIntrinsicWidth() - 35, drawable_passwd_default.getIntrinsicHeight() - 35);
                    passwd.setCompoundDrawables(drawable_passwd_default, null, null, null);
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();



    }

    @Override
    public void onStop() {
        super.onStop();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                userLogin();
                break;

            case R.id.regist:
                registerUser();
                break;
        }
    }


    private void registerUser(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(intent, AppUtil.FLAG_ACTIVITY);
    }


    /**
     * 进入登录  向服务器发送登录信息
     */
    private void userLogin(){
        String user = username.getText().toString();
        String password = passwd.getText().toString();

        if(TextUtils.isEmpty(user) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "请确认输入信息", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", user);
        map.put("password", JNIClass.getMD5Char(password));
        map.put("task_flag", "login");


        Task task = new Task(AppUtil.LOGIN_VERIFY, map, new HttpListener(this));
        IOManager.getInstance(this).add_task_start(task);
    }

    @Override
    public void onResponse(int code, Object msg) {
        JSONObject json = null;
        try {
            json = new JSONObject((String)msg);
            if(code == AppUtil.REQUEST_SUCCESS){
                JLog.default_print("登录成功");

                String token = json.getString("token");
                SharedPreferenceUtil util = SharedPreferenceUtil.getInstance(this.getApplicationContext());
                util.init();
                util.putString("token", token);
                util.putString("icon_url", json.getString("icon_url"));
                util.putString("nickname", json.getString("nickname"));
                util.putString("login_flag","ok");
                Toast.makeText(LoginActivity.this, "恭喜,登录成功", Toast.LENGTH_SHORT).show();
                this.finish();
            }else{
                JLog.default_print("登录失败 ");
                String cause = json.getString("message");
                Toast.makeText(LoginActivity.this, "登录失败, " + cause, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(int code, Object obj) {
        JLog.default_print("登录失败 " + obj);
        Toast.makeText(LoginActivity.this, "http错误" + obj, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case AppUtil.FLAG_ACTIVITY:    //  Activity之间传递的信息量
                if(null == data){
                    return;
                }
                boolean closed = data.getBooleanExtra(AppUtil.CLOSED_ACTIVTY, false);           //注册成功就关闭之前的注册页面

                if(closed){
                    this.finish();
                }
        }
    }
}
