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

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;

/**
 * Created by "sinlov" on 2016/7/14.
 */
public class RegisterSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtil.integrationNotifcationBar(this);

        initLayoutResource();

    }


    private void initLayoutResource(){
        setContentView(R.layout.register_success);

        Toolbar bar = (Toolbar)findViewById(R.id.register_success_bar);
        setSupportActionBar(bar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView tv = (TextView)findViewById(R.id.tv_barrage);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_answer_danmaku2);
        drawable.setBounds(0,0, drawable.getMinimumWidth() - 40, drawable.getMinimumHeight() - 45);
        tv.setCompoundDrawables(drawable, null, null, null);
        tv.setCompoundDrawablePadding(40);                   //图标和文字之间的距离

        tv = (TextView)findViewById(R.id.tv_collect);
        drawable = ContextCompat.getDrawable(this, R.drawable.ic_answer_favorite);
        drawable.setBounds(0, 0, drawable.getMinimumWidth() - 40, drawable.getMinimumHeight() - 45);
        tv.setCompoundDrawables(drawable, null, null, null);
        tv.setCompoundDrawablePadding(40);                   //图标和文字之间的距离

        tv = (TextView)findViewById(R.id.tv_barrage_spec);
        drawable = ContextCompat.getDrawable(this, R.drawable.ic_answer_danmaku);
        drawable.setBounds(0, 0, drawable.getMinimumWidth() - 40, drawable.getMinimumHeight() - 45);
        tv.setCompoundDrawables(drawable, null, null, null);
        tv.setCompoundDrawablePadding(40);                   //图标和文字之间的距离

        tv = (TextView)findViewById(R.id.tv_video_comment);
        drawable = ContextCompat.getDrawable(this, R.drawable.ic_answer_comment);
        drawable.setBounds(0, 0, drawable.getMinimumWidth() - 40, drawable.getMinimumHeight() - 45);
        tv.setCompoundDrawables(drawable, null, null, null);
        tv.setCompoundDrawablePadding(40);

        tv = (TextView)findViewById(R.id.tv_video_coin);
        drawable = ContextCompat.getDrawable(this, R.drawable.ic_answer_coin);
        drawable.setBounds(0, 0, drawable.getMinimumWidth() - 40, drawable.getMinimumHeight() - 45);
        tv.setCompoundDrawables(drawable, null, null, null);
        tv.setCompoundDrawablePadding(40);

        tv = (TextView)findViewById(R.id.tv_idol);
        drawable = ContextCompat.getDrawable(this, R.drawable.ic_answer_author);
        drawable.setBounds(0, 0, drawable.getMinimumWidth() - 40, drawable.getMinimumHeight() - 45);
        tv.setCompoundDrawables(drawable, null, null, null);
        tv.setCompoundDrawablePadding(40);
    }


    /**
     * 关闭此activity
     * @param view
     */
    public void onOpenNewWorld(View view){
        finish();
    }


}
