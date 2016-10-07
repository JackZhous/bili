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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendFragment extends Fragment implements BiliCallback{

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

        initNetworkImage();
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

        JLog.default_print(TAG + "   onPause");
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



    private void initNetworkImage(){

        HashMap<String, String> map = new HashMap<>();
        map.put("task_flag", "refreshImage");
        map.put("module", "recommend_module");

        Task task = new Task(AppUtil.GET_IMAGE, map, new HttpListener(this));
        IOManager.getInstance(getContext()).add_task_start(task);
    }


    /**
     * 网络回调接口
     * @param code  成功或失败
     * @param msg   返回数据 形如：{"result":"ok","count":10,"data":
     *              ["{\"sencond_module\":\"hot_modlue\",\"play_times\":\"18902\",
     *              \"image_url\":\"image\/img_tips_error_banner_tv.png\",
     *              \"video_info\":\"video info1\",\"up_times\":\"100\",
     *              \"first_moudle\":\"recommend_module\"}"
     *              count表示有多少个数组  data里面是具体的数据
     */
    @Override
    public void onResponse(int code, Object msg) {


        if(code != AppUtil.REQUEST_SUCCESS || TextUtils.isEmpty(msg.toString())){
            return;
        }
        ArrayList<String>  body_image_list = new ArrayList<>();
        ArrayList<Map<String, String>> body_info_list = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(String.valueOf(msg));
            JSONArray jsonArray = jsonObject.optJSONArray("data");
            int count = jsonObject.optInt("count");
            for(int i = 0; i < count; i++){
                JSONObject object = new JSONObject(jsonArray.optString(i));
                ImageUrlBean bean = ImageUrlBean.toBean(object);
                body_image_list.add(bean.getImage_url());
                HashMap<String,String> map = new HashMap<>();
                map.put(RecommendViewHolder.TV_INFO, bean.getVideo_info());
                map.put(RecommendViewHolder.TV_TIME_PLAY, bean.getPlay_times());
                map.put(RecommendViewHolder.TV_TIME_DING, bean.getUp_times());
                body_info_list.add(map);
                JLog.default_print("imageurlbean == " + bean.toString());
            }

            ((RecommendViewHolder)jRecyclerView.getViewHolder()).setBody_image_list(body_image_list);
            ((RecommendViewHolder)jRecyclerView.getViewHolder()).setBody_info_list(body_info_list);
            jRecyclerView.getAdapter().notifyDataSetChanged();
        }catch (Exception e){
            JLog.default_print("has a Exception");
            e.printStackTrace();
        }
    }

    @Override
    public void onError(int code, Object obj) {

    }

}
