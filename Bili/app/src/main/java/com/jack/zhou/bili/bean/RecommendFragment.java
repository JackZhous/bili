/*
 *         Copyright (C) 2016-2017 宙斯
 *         All rights reserved
 *
 *        filename :RecommendFragment .java
 *        description :
 *              推荐版块
 *         created by jackzhous at  25/07/2016 10:21:12
 *         http://blog.csdn.net/jackzhouyu
 */

package com.jack.zhou.bili.bean;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.adapter.RecommendViewHolder;
import com.jack.zhou.bili.inter.BiliCallback;
import com.jack.zhou.bili.inter.HttpListener;
import com.jack.zhou.bili.network.IOManager;
import com.jack.zhou.bili.network.Task;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;
import com.jack.zhou.jrecyclerview.adapter.JViewHolder;
import com.jack.zhou.jrecyclerview.recycler.JRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendFragment extends Fragment{

    private static final String TAG = "RecommendFragment";
    private JRecyclerView jRecyclerView;
    private JViewHolder holder;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recommend_layout,container, false);
        JLog.default_print(TAG + " onCreateView");
        initLayout(v);
        return v;
    }


    private void initLayout(View v){

        int[] layout_content_start_list = new int[]{R.layout.recycler_content_start, R.layout.recycler_content_start1};                                      //胸部起始布局集合
        int[] layout_content_end_list = new int[]{R.layout.recycler_content_end, R.layout.recycler_content_end1};                                        //胸部结束布局集合


        jRecyclerView = (JRecyclerView)v.findViewById(R.id.recyclerView);
        jRecyclerView.setBody_end(layout_content_end_list);
        jRecyclerView.setBody_start(layout_content_start_list);
        holder = new RecommendViewHolder(getContext());
        jRecyclerView.setViewHolder(holder);

        jRecyclerView.startToShow();
        jRecyclerView.addItemDecoration(new RecyclerItemDecoration(30));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        JLog.default_print(TAG + " onActivityCreated");

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JLog.default_print(TAG + "   onCreate");
    }

    @Override
    public void onPause() {
        super.onPause();

        JLog.default_print(TAG+ "   onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        JLog.default_print(TAG + "   onStop");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        JLog.default_print(TAG + "    onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JLog.default_print(TAG + "    onDestroy");

    }

    /**
     * RecyclerView间隔
     */
    public class RecyclerItemDecoration extends RecyclerView.ItemDecoration{
        private int space;

        public RecyclerItemDecoration(int space){
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if(parent.getChildPosition(view) != 0){
                outRect.top = space;
                outRect.left = space;
                outRect.right = space;
            }
        }
    }

}
