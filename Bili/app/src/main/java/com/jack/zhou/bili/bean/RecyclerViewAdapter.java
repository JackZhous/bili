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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by "jackzhous" on 2016/7/27.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecViewHolder> {

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecViewHolder extends RecyclerView.ViewHolder {
        public RecViewHolder(View itemView) {
            super(itemView);
        }
    }
}
