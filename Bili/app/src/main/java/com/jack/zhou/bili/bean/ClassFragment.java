package com.jack.zhou.bili.bean;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.adapter.ClassRecyclerAdapter;

/**
 * author: jackzhous
 * file : ClassFragment.java
 * create time: 16-8-10 下午10:03
 * desc:
 * 视频分类fragment
 **/
public class ClassFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.class_fragment, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler);

        recyclerView.setAdapter(new ClassRecyclerAdapter(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        return v;
    }
}
