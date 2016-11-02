package com.jack.zhou.bili.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.ui.view.HotSearchView;

import java.util.ArrayList;

/**
 * author: jackzhous
 * file : SearchFragment.java
 * create time: 16-9-24 上午11:29
 * desc:
 * 搜索的fragment
 **/
public class SearchFragment extends Fragment{
    private Context mContext;
    private HotSearchView hotSearchView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.bili_search_frag, null);
        init_layout_resource(view);
        return view;
    }


    private void init_layout_resource(View view){
        hotSearchView = (HotSearchView)view.findViewById(R.id.hot_search);
        ArrayList<String> list = new ArrayList<>();
        list.add("极乐净土");
        list.add("ppap");
        list.add("法医秦明");
        list.add("一年生");
        list.add("舞动采访间");
        list.add("吃货木下");
        list.add("不可抗力");
        list.add("暴走大事件");
        list.add("守望先锋");
        list.add("夏目友人帐");
        list.add("你的名字");
        list.add("张继科");
        list.add("蓝瘦香菇");
        list.add("主播炸了");
        list.add("主播真会玩");
        list.add("西部世界");
        list.add("蜡笔小新");
        list.add("刺客列传");

        list.add("麻雀");
        list.add("阴阳师");
        list.add("大胃王密子君");
        list.add("敖厂长");
        list.add("谷阿莫");
        list.add("起小点");
        list.add("釜山行");
        list.add("火隐忍者");
        list.add("唔冻");
        list.add("逗鱼时刻");
        list.add("镇魂街");
        list.add("日剧");
        list.add("snh48");
        list.add("杨洋");
        list.add("错生");
        list.add("老e");
        list.add("fate");
        list.add("徐老师来巡山");
        int index = 0;
        for (String str : list){
            TextView textView = new TextView(mContext);
            textView.setText(str);
            textView.setTextSize(15);

            textView.setBackground(getResources().getDrawable(R.drawable.text_view_bg));
            textView.setTag(str);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


            hotSearchView.addView(textView, index);
            index++;
        }


        final TextView tv = (TextView)view.findViewById(R.id.more);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotSearchView.expandOrClose();
                if(hotSearchView.isExpand()){
                    tv.setText("收起");
                }else{
                    tv.setText("查看更多");
                }
            }
        });
    }


    private void play_video(){

    }
}
