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

package com.jack.zhou.bili.bean;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jack.zhou.bili.R;

import java.util.List;
import java.util.Map;


/**
 * Created by "jackzhous" on 2016/7/27.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecViewHolder> {

    private Activity activity;
    private List<Map<String, String>> tv_list;
    private List<Drawable> image_list;

    public RecyclerViewAdapter(Activity activity, List<Map<String, String>> tv_list, List<Drawable> image_list){
        this.activity = activity;
        this.tv_list = tv_list;
        this.image_list = image_list;
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(activity).inflate(R.layout.recycler_layout,parent, false);
        RecViewHolder holder = new RecViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {
        holder.imageView.setImageDrawable(image_list.get(position));
        Map<String,String> map = tv_list.get(position);
        holder.tv.setText(map.get("tv_info"));
        holder.play_time.setText(map.get("play_time"));
        holder.up_time.setText(map.get("up_time"));
    }

    @Override
    public int getItemCount() {
        return image_list.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;                     //视频概图
        public TextView tv;                             //视频简介
        public TextView play_time;                      //播放次数
        public TextView up_time;                        //点赞次数

        public RecViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.image_view);
            tv = (TextView)itemView.findViewById(R.id.tv_info);
            play_time = (TextView)itemView.findViewById(R.id.time_play);
            up_time = (TextView)itemView.findViewById(R.id.time_dian);
        }
    }
}
