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

package com.jack.zhou.jrecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jack.zhou.jrecyclerview.util.JLog;

import java.util.ArrayList;

/**
 * Created by "jackzhous" on 2016/7/29.
 */
public class JAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_CONTENT = 1;                          //胸部
    private static final String TAG = "JAdapter";
    public static final int TYPE_HEADER = 0;                           //头部
    private static final int TYPE_BODY_START = 2;
    private static final int TYPE_BODY_END = 3;

    private int header_layout;
    private int body_layout;
    private int[] body_start;
    private int[] body_end;
    private JViewHolder viewHolder;


    public JAdapter(JViewHolder viewHolder, int header, int bodyer) {
        JLog.print(TAG, "JAdapter -- header = " + header);
        this.header_layout = header;
        this.body_layout = bodyer;
        this.viewHolder = viewHolder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        JLog.print(TAG, "onCreateViewHolder -- position = " + position);
        View view = null;
        int viewType = getType(position);
        position = position / 6;
        if (viewType == TYPE_HEADER) {
            JLog.print(TAG, "onCreateViewHolder -- TYPE_HEADER");
            view = LayoutInflater.from(parent.getContext()).inflate(header_layout, null);
            viewHolder.findHead(view);
        } else if(viewType == TYPE_CONTENT){
            JLog.print(TAG, "onCreateViewHolder -- TYPE_CONTENT");
            view = LayoutInflater.from(parent.getContext()).inflate(body_layout, null);
            viewHolder.findBody(view);
        } else if(viewType == TYPE_BODY_START){
            JLog.print(TAG, "onCreateViewHolder -- TYPE_BODY_START");
            view = LayoutInflater.from(parent.getContext()).inflate(body_start[position], null);
            viewHolder.findBodyStart(view, position);

        } else if(viewType == TYPE_BODY_END){
            JLog.print(TAG, "onCreateViewHolder -- TYPE_BODY_END");
            view = LayoutInflater.from(parent.getContext()).inflate(body_end[position -1], null);
            viewHolder.findBodyEnd(view, position - 1);
        }
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        JLog.print(TAG, "onBindViewHolder -- position = " + position);
        if (getType(position) == TYPE_CONTENT) {                                            //说明是胸部
            int div = position / 6 + 1;
            position = position - div * 2;
            JLog.print(TAG, "position " + position);
            viewHolder.setBody(position);
        } else if(getType(position) == TYPE_HEADER){
            viewHolder.setHead();
        } else if(getType(position) == TYPE_BODY_START){
            viewHolder.setBodyStart(position);
        } else if(getType(position) == TYPE_BODY_END){
            viewHolder.setBodyEnd(position);
        }
    }

    @Override
    public int getItemCount() {
        JLog.print(TAG, "getItemCount  " + viewHolder.size());
        return viewHolder.size();
    }

    /**
     * 直接返回位置，让onCreateView里面使用的是位置信息
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        JLog.print(TAG, "getItemViewType  position " + position);
        /*int type = TYPE_HEADER;
        if (position != 0) {
            position = position % 6;
            if(position != 0 && position != 1){
                type = TYPE_CONTENT;
            }else {
                type = (position == 0 ? TYPE_BODY_END : TYPE_BODY_START);
            }
        }*/
        return position;
    }


    private int getType(int position){
        int type = TYPE_HEADER;


        if (position != 0) {
            position = position % 6;
            if(position != 0 && position != 1){
                type = TYPE_CONTENT;
            }else {
                type = (position == 0 ? TYPE_BODY_END : TYPE_BODY_START);
            }

        }

        return type;
    }


    private class Holder extends RecyclerView.ViewHolder{
        public Holder(View itemView) {
            super(itemView);
        }
    }

    public void setBody_start(int[] body_start) {
        this.body_start = body_start;
    }

    public void setBody_end(int[] body_end) {
        this.body_end = body_end;
    }
}