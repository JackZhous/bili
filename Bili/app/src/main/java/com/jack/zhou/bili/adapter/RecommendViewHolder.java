package com.jack.zhou.bili.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jack.zhou.bili.R;
import com.jack.zhou.jrecyclerview.adapter.JViewHolder;

import java.util.ArrayList;
import java.util.Map;

/***********
 * author: jackzhous
 * file: RecommendViewHolder.java
 * create date: 2016/8/8 14:28
 * desc:
 ************/
public class RecommendViewHolder implements JViewHolder{
    private LinearLayout head_Dot;
    private ViewPager    head_viewpager;
    private ImageView    body_image;
    private TextView     body_info;
    private TextView     body_time_play;
    private TextView     body_time_ding;

    private ArrayList<ImageView> head_image_list;
    private ArrayList<Drawable>  body_image_list;
    private ArrayList<Map<String, String>> body_info_list;

    private Drawable unSelectDot;
    private Drawable selectDot;

    public RecommendViewHolder(){

    }

    @Override
    public void findHead(View v) {
        head_Dot = (LinearLayout)v.findViewById(R.id.dot);
        head_viewpager = (ViewPager)v.findViewById(R.id.photo_viewpager);
    }

    @Override
    public void findBody(View v) {
        body_image = (ImageView)v.findViewById(R.id.image_view);
        body_info = (TextView)v.findViewById(R.id.tv_info);
        body_time_play = (TextView)v.findViewById(R.id.time_play);
        body_time_ding = (TextView)v.findViewById(R.id.time_dian);
    }

    @Override
    public void setHead() {

    }

    @Override
    public void setBody(int position) {

    }

    @Override
    public int size() {
        return body_image_list.size() + 1;
    }
}
