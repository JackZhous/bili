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

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
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
import android.widget.Toast;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.adapter.RecyclerViewAdapter;
import com.jack.zhou.bili.util.JLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendFragment extends Fragment {

    private static final String TAG = "RecommendFragment";
    private View v;
    private ViewPager photo_viewpager;
    private ArrayList<ImageView> mView = new ArrayList<>();
    private ArrayList<ImageView> mDot = new ArrayList<>();
    private Drawable selectedDot,unselectDot;                   //选中和没被选中的小圆点
    private LinearLayout mDotLayout;
    private TextView tv_recommend, tv_rank;
    private RecyclerView recyclerView;
    private int selected_poisition = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.recommend_layout,container, false);
        JLog.default_print(TAG + " onCreateView");
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        JLog.default_print(TAG + " onActivityCreated");
        photo_viewpager = (ViewPager)v.findViewById(R.id.photo_viewpager);
        mDotLayout = (LinearLayout)v.findViewById(R.id.dot);
        initImageView();
        photo_viewpager.setAdapter(new Adapter());
        photo_viewpager.setOnPageChangeListener(new PageListener());
        photo_viewpager.setPageMargin(20);                                  //设置图片与图片之间的间隔
        if(0 == selected_poisition){
            selected_poisition = mView.size() * 100;
        }
        photo_viewpager.setCurrentItem(selected_poisition);
    }


    private void initImageView(){
        unselectDot = getActivity().getDrawable(R.drawable.abc_btn_switch_to_on_mtrl_00001);
        Drawable.ConstantState state_user = unselectDot.getConstantState();
        selectedDot = (state_user == null) ? unselectDot : state_user.newDrawable().mutate();
        DrawableCompat.setTint(selectedDot, ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));                   //着色和不着色



        /**
         * 添加小圆点和显示师徒到布局文件
         */
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(30,30);
        ViewGroup.LayoutParams params_image = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for(int i = 0; i < 3; i++){
            ImageView image = new ImageView(getActivity());
            image.setImageResource(R.drawable.ic_answer_banner);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setLayoutParams(params_image);
            mView.add(image);

            ImageView dot = new ImageView(getActivity());
            if(i == selected_poisition%3){
                dot.setImageDrawable(selectedDot);
            }else{
                dot.setImageDrawable(unselectDot);
            }

            dot.setLayoutParams(params);

            mDotLayout.addView(dot,i);
        }

        tv_rank = (TextView)v.findViewById(R.id.tv_rank);
        tv_recommend = (TextView)v.findViewById(R.id.tv_recommend);
        Drawable tv_draw = ContextCompat.getDrawable(getActivity(),R.drawable.ic_header_hot);
        tv_draw.setBounds(0, 0, 60, 60);

        tv_recommend.setCompoundDrawables(tv_draw, null, null, null);
        tv_draw =  ContextCompat.getDrawable(getActivity(),R.drawable.ic_bangumi_rank);
        tv_draw.setBounds(0, 0, 60, 60);
        tv_rank.setCompoundDrawables(tv_draw, null, null, null);

        initRecyclerView();

    }


    /**
     * 初始化recyclerView
     */
    private void initRecyclerView(){

        List<Map<String,String>> list_st = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            HashMap<String,String> map = new HashMap<>();
            map.put("tv_info", "tv_info" + i);
            map.put("play_time","play_time" + i);
            map.put("up_time", "up_time" + i);
            list_st.add(map);

        }
        List<Drawable> list_image = new ArrayList<>();
        Drawable drawable = getActivity().getDrawable(R.drawable.test);
        list_image.add(drawable);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), list_st, list_image);

        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    //============viewPager相关=====================================================================

    /**
     * viewpager 的position是从0到length-1
     * 每次它会缓冲当前位置的前后一个item，举个栗子：
     *  假如当前位置是2，一个有5个长度，此时我向左滑动
     *  他会先显示 position = 3的item   实例化缓存数据 position=4的数据  最后在去销毁destroy位置为position=1的数据
     */
    private class Adapter extends PagerAdapter{
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            JLog.default_print("instantiateItem position == " + position);
            position = position % mView.size();
            if(position < 0){
                position = mView.size() + position;
            }

            ImageView view = mView.get(position);
            ViewParent parent = view.getParent();
            if(null != parent){                                                 //父组件如果不为空  就说明之前有添加过师徒
                ViewGroup vg = (ViewGroup)parent;
                vg.removeView(view);
            }

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            JLog.default_print("destroy position == " + position);
            //container.removeView((View)object);  不能再这儿移动
        }
    }

    private class PageListener implements ViewPager.OnPageChangeListener{

        ImageView cachView;         //缓冲试图，存放上一视图的引用

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            JLog.default_print(TAG + " onPageSelected " + position);

            selected_poisition = position;
            JLog.default_print(TAG + "mView.size " + mView.size());
            position = position % mView.size();

            if(cachView != null){
                cachView.setImageDrawable(unselectDot);
            }

            ImageView v = (ImageView)mDotLayout.getChildAt(position);
            if(null != v){
                v.setImageDrawable(selectedDot);
                cachView = v;
                JLog.default_print(TAG + " not null " + position);
            }else{
                cachView = null;
                JLog.default_print(TAG + " null " + position);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //============viewPager相关=====================================================================


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

        /**
         * 清除掉里面的内容，否则再次创建里面的数据仍会存在
         */
        mView.clear();
        mDot.clear();
        mDotLayout.removeAllViews();
    }
}
