package com.jack.zhou.bili.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jack.zhou.bili.R;

/**
 * author: jackzhous
 * file : SearchFragment.java
 * create time: 16-9-24 上午11:29
 * desc:
 * 搜索的fragment
 **/
public class SearchFragment extends Fragment{
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.bili_search_frag, null);
        init_layout_resource(view);
        return view;
    }


    private void init_layout_resource(View view){

    }


    private void play_video(){

    }
}
