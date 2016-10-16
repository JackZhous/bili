package com.jack.zhou.bili.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jack.zhou.bili.R;
import com.jack.zhou.bili.bean.ImageUrlBean;
import com.jack.zhou.bili.inter.BiliCallback;
import com.jack.zhou.bili.inter.HttpListener;
import com.jack.zhou.bili.network.IOManager;
import com.jack.zhou.bili.network.NetworkHelper;
import com.jack.zhou.bili.network.Task;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;
import com.jack.zhou.jrecyclerview.adapter.JViewHolder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/***********
 * author: jackzhous
 * file: RecommendViewHolder.java
 * create date: 2016/8/8 14:28
 * desc:
 ************/
public class RecommendViewHolder implements JViewHolder{

    private Context context;
    private LinearLayout head_Dot;
    private ViewPager    head_viewpager;
    private ImageView    body_image;
    private TextView     body_info;
    private TextView     body_time_play;
    private TextView     body_time_ding;
    private TextView tv_recommend;
    private TextView tv_rank;

    private ArrayList<ImageView> head_image_list;
    private ArrayList<String>  body_image_list;
    private ArrayList<Map<String, String>> body_info_list;

    private Drawable unSelectDot;
    private Drawable selectDot;

    public static final String TV_INFO = "video_info";
    public static final String TV_TIME_PLAY = "time_play";
    public static final String TV_TIME_DING = "time_ding";

    public RecommendViewHolder(Context context){
        this.context = context;
        head_image_list = new ArrayList<>();
        body_image_list = new ArrayList<>();
        body_info_list  = new ArrayList<>();

        initDisplayData();
    }

    @Override
    public void findHead(View v) {
        head_Dot = (LinearLayout)v.findViewById(R.id.dot);
        head_viewpager = (ViewPager)v.findViewById(R.id.photo_viewpager);
    }

    @Override
    public void findBody(View v) {
        body_image = (ImageView)v.findViewById(R.id.image_view);
        body_info = (TextView)v.findViewById(R.id.tv_info);
        body_time_play = (TextView)v.findViewById(R.id.time_play);
        body_time_ding = (TextView)v.findViewById(R.id.time_dian);
    }

    @Override
    public void setHead() {

        if(head_Dot != null && head_Dot.getChildAt(0) != null){
            return;
        }

        unSelectDot = context.getDrawable(R.drawable.ic_circle_white);
        Drawable.ConstantState state_user = unSelectDot.getConstantState();
        selectDot = (state_user == null) ? unSelectDot : state_user.newDrawable().mutate();
        DrawableCompat.setTint(selectDot, ContextCompat.getColor(context, R.color.colorPrimary));

        ImageView dot = new ImageView(context);
        dot.setImageDrawable(selectDot);
        LinearLayout.LayoutParams dot_params = new LinearLayout.LayoutParams(20,20);
        dot_params.setMarginEnd(10);
        dot.setLayoutParams(dot_params);

        head_Dot.addView(dot, 0);

        for(int i = 1; i < head_image_list.size(); i++){

            dot = new ImageView(context);
            dot.setImageDrawable(unSelectDot);
            dot.setLayoutParams(dot_params);
            head_Dot.addView(dot, i);
        }

        head_viewpager.setAdapter(new MyAdapter());
        head_viewpager.setOnPageChangeListener(new MyPageListener());
        head_viewpager.setCurrentItem(100 * head_image_list.size());
        head_viewpager.setPageMargin(20);
    }

    @Override
    public void setBody(int position) {

        String url = body_image_list.get(position);
        Glide.with(context).load(NetworkHelper.HTTP_BASE_URL +url).centerCrop().placeholder(R.drawable.bili_default_image_tv).crossFade().into(body_image);
        final Map<String, String> map = body_info_list.get(position);
        body_info.setText(map.get(TV_INFO));
        body_time_play.setText(map.get(TV_TIME_PLAY));
        body_time_ding.setText(map.get(TV_TIME_DING));
        body_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = NetworkHelper.HTTP_BASE_URL + map.get(ImageUrlBean.VIDEO_URL);
                String name = map.get(ImageUrlBean.VIDEO_INFO);
                //VideoActivity.intentTo(context, url, name);
            }
        });
    }

    @Override
    public int size() {
        int size = body_image_list.size();
        if(body_image_list.size() != 0){
            if(size % 4 == 0){
                size = size + 1 + size / 2;
            }else{
                size = size + (size / 4 + 1) * 2;
            }
        }
        JLog.default_print("size " + size);
        return size;
    }

    @Override
    public void setBodyStart(int position) {

    }

    @Override
    public void setBodyEnd(int position) {

    }

    @Override
    public void findBodyStart(View v, int position) {
        if(position == 0){                                                                          //第0个推荐布局
            tv_rank = (TextView)v.findViewById(R.id.tv_rank);
            tv_recommend = (TextView)v.findViewById(R.id.tv_recommend);
            Drawable draw = context.getDrawable(R.drawable.ic_header_hot);
            draw.setBounds(0, 0, 60, 60);
            tv_recommend.setCompoundDrawables(draw, null, null, null);

            draw = context.getDrawable(R.drawable.ic_bangumi_rank);
            draw.setBounds(0, 0, 60, 60);
            tv_rank.setCompoundDrawables(draw, null, null, null);
        }else if(position == 1){                                                                    //第1个直播开头布局
            TextView live = (TextView)v.findViewById(R.id.tv_live);
            live.setText("  正在直播");
            Drawable draw = context.getDrawable(R.drawable.ic_head_live);
            draw.setBounds(0, 0, 60, 60);
            live.setCompoundDrawables(draw, null, null, null);

            TextView tv_live_info = (TextView)v.findViewById(R.id.tv_live_info);
            SpannableStringBuilder style = new SpannableStringBuilder("当前1800个直播 >");
            style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)), 2, 6, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            tv_live_info.setText(style);
        }

    }

    @Override
    public void findBodyEnd(View v, int position) {
        if(position == 0){
            TextView rechange = (TextView)v.findViewById(R.id.fresh);
            ImageView imageView = (ImageView)v.findViewById(R.id.fresh_icon);
            Drawable draw = context.getDrawable(R.drawable.ic_category_more_refresh);
            DrawableCompat.setTint(draw, ContextCompat.getColor(context, R.color.colorPrimary));
            imageView.setImageDrawable(draw);
        }

    }

    private void initDisplayData(){

        int[] draw = new int[]{R.drawable.ic_answer_banner, R.drawable.ic_certified_id, R.drawable.ic_group_header_bg};

        //将图片其添加到集合
        for(int i = 0; i < draw.length; i++){
            ImageView v = new ImageView(context);
            LinearLayout.LayoutParams image_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            v.setImageDrawable(context.getResources().getDrawable(draw[i]));
            v.setLayoutParams(image_params);
            v.setScaleType(ImageView.ScaleType.FIT_XY);
            head_image_list.add(v);
        }
        JLog.default_print("initDisplayData head size " + head_image_list.size());

        //胸部需要显示的Item图片  并添加到集合
        /*draw = new int[]{R.drawable.img_tips_error_banner_tv, R.drawable.img_tips_error_load_error, R.drawable.img_tips_error_no_permission, R.drawable.img_tips_error_not_foud, R.drawable.img_tips_error_not_loin, R.drawable.img_tips_error_space_no_data, R.drawable.img_tips_error_space_no_permission, R.drawable.img_tips_live_room_locked};
        for(int i = 0; i < draw.length; i++){
            body_image_list.add(context.getDrawable(draw[i]));
            HashMap<String, String> map = new HashMap<>();
            map.put(TV_INFO, "视频简介 " + i + " ^_^");
            map.put(TV_TIME_DING, i+"");
            map.put(TV_TIME_PLAY, i + "");
            body_info_list.add(map);
        }*/
    }


    /**
     * ViewPager监听内部类
     */
    private class MyPageListener implements ViewPager.OnPageChangeListener{
        ImageView cachView;
        int length;
        public MyPageListener(){
            length = head_image_list.size();
        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(null != cachView){
                cachView.setImageDrawable(unSelectDot);
            }
            position = position % length;
            ImageView image = (ImageView)head_Dot.getChildAt(position);
            if(null != image){
                image.setImageDrawable(selectDot);
            }

            cachView = image;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * viewpager适配器
     */
    private class MyAdapter extends PagerAdapter{

        private int length;

        public MyAdapter(){
            length = head_image_list.size();
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;               //这是为了让viewpager的画面可以循环播放
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            position = position % length;
            if(position < 0){
                position = position + length;
            }

            ImageView imageView = head_image_list.get(position);
            ViewParent parent = imageView.getParent();
            if(null != parent){
                ((ViewGroup)parent).removeView(imageView);
            }

            container.addView(imageView);


            return imageView;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }
    }

    public void setBody_image_list(ArrayList<String> body_image_list) {
        this.body_image_list = body_image_list;
    }

    public void setBody_info_list(ArrayList<Map<String, String>> body_info_list) {
        this.body_info_list = body_info_list;
    }
}
