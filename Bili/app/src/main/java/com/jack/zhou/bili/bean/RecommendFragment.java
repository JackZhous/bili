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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.util.JLog;

import java.util.ArrayList;

public class RecommendFragment extends Fragment {

    private View v;
    private ViewPager photo_viewpager;
    private ArrayList<ImageView> mView = new ArrayList<>();
    private ArrayList<ImageView> mDot = new ArrayList<>();
    private Drawable selectedDot,unselectDot;                   //选中和没被选中的小圆点
    private LinearLayout mDotLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.recommend_layout,container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        photo_viewpager = (ViewPager)v.findViewById(R.id.photo_viewpager);
        mDotLayout = (LinearLayout)v.findViewById(R.id.dot);
        initImageView();
        photo_viewpager.setAdapter(new Adapter());
        photo_viewpager.setOnPageChangeListener(new PageListener());
        photo_viewpager.setCurrentItem(mView.size() * 100);
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
        for(int i = 0; i < 3; i++){
            ImageView image = new ImageView(getActivity());
            image.setImageResource(R.drawable.ic_answer_banner);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            mView.add(image);

            ImageView dot = new ImageView(getActivity());
            if(i == 0){
                dot.setImageDrawable(selectedDot);
            }else{
                dot.setImageDrawable(unselectDot);
            }

            dot.setLayoutParams(params);

            mDotLayout.addView(dot);
        }
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
            position = position % mView.size();
            if(cachView != null){
                cachView.setImageDrawable(unselectDot);
            }

            ImageView v = (ImageView)mDotLayout.getChildAt(position);
            v.setImageDrawable(selectedDot);
            cachView = v;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //============viewPager相关=====================================================================
}
