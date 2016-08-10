package com.jack.zhou.bili.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jack.zhou.bili.R;

import java.util.ArrayList;

/**
 * author: jackzhous
 * file : ClassRecyclerAdapter.java
 * create time: 16-8-10 下午10:13
 * desc:
 **/
public class ClassRecyclerAdapter extends RecyclerView.Adapter<ClassRecyclerAdapter.Holder> {
    private ArrayList<Drawable> image_list;
    private ArrayList<String> tv_list;

    public ClassRecyclerAdapter(Context context){
        image_list = new ArrayList<>();
        tv_list = new ArrayList<>();
        tv_list.add("直播");
        tv_list.add("番剧");
        tv_list.add("动画");
        tv_list.add("音乐");
        tv_list.add("舞蹈");
        tv_list.add("游戏");
        tv_list.add("科技");
        tv_list.add("生活");
        tv_list.add("鬼畜");
        tv_list.add("时尚");
        tv_list.add("娱乐");
        tv_list.add("电影");
        tv_list.add("电视剧");
        tv_list.add("游戏中心");

        Drawable drawable = context.getDrawable(R.drawable.ic_head_live);
        image_list.add(drawable);
        image_list.add(drawable);
        drawable = context.getDrawable(R.drawable.bili_default_image_tv);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.colorPrimary));
        image_list.add(drawable);
        image_list.add(drawable);
        image_list.add(drawable);
        image_list.add(drawable);
        image_list.add(drawable);
        image_list.add(drawable);
        image_list.add(drawable);
        image_list.add(drawable);
        image_list.add(drawable);
        image_list.add(drawable);
        image_list.add(drawable);
        drawable = context.getDrawable(R.drawable.ic_menu_top_game_center);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.colorPrimary));
        image_list.add(drawable);



    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_class_fragment, null);

        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.imageView.setImageDrawable(image_list.get(position));
        holder.tv.setText(tv_list.get(position));
    }

    @Override
    public int getItemCount() {
        return image_list.size();
    }

    public  class Holder extends  RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView tv;
        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.image);
            tv = (TextView)itemView.findViewById(R.id.tv);
        }
    }
}
