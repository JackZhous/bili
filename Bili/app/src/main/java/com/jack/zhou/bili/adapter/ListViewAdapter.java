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

package com.jack.zhou.bili.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.bean.LiveShowBean;
import com.jack.zhou.bili.util.JLog;

import java.util.ArrayList;

/**
 * author: jackzhous
 * file : ListViewAdapter.java
 * create time: 16-10-24 下午10:59
 * desc:
 **/
public class ListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<LiveShowBean> arrayList;
    public static Drawable unselectedIcon;
    public static  Drawable selectedIcon;

    public static void init(Context context){
        unselectedIcon = ContextCompat.getDrawable(context, R.drawable.abc_btn_radio_to_on_mtrl_000);
        DrawableCompat.setTint(unselectedIcon, ContextCompat.getColor(context, R.color.gray));
        selectedIcon = ContextCompat.getDrawable(context, R.drawable.abc_btn_radio_to_on_mtrl_015);
        DrawableCompat.setTint(selectedIcon, ContextCompat.getColor(context, R.color.colorPrimary));
    }

    public ListViewAdapter(Activity context, ArrayList<LiveShowBean> list){

        inflater = context.getLayoutInflater();
        arrayList = list;

    }



    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * getView  产生view后显示条目到界面上去， convertview是缓存的视图，可以重复利用
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        //如果缓冲试图convert为null就需要创建新的视图显示
        if(convertView == null){
            JLog.default_print("pos " + position + " null " + " -- ");
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_country, null);
            holder.image = (ImageView)convertView.findViewById(R.id.icon);
            holder.tv = (TextView)convertView.findViewById(R.id.country);
            convertView.setTag(holder);
        }else{
            JLog.default_print("pos " + position + " not null " +" -- ");
            holder = (ViewHolder)convertView.getTag();
        }
        Drawable icon = arrayList.get(position).getisSelectId() == true ? selectedIcon : unselectedIcon;
        holder.image.setImageDrawable(icon);
        holder.tv.setText(arrayList.get(position).getValue());

        return convertView;
    }


    class ViewHolder{
        public ImageView image;
        public TextView tv;
    }
}
