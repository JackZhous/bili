package com.jack.zhou.bili.bean;

import org.json.JSONObject;

/**
 * author: jackzhous
 * file : ImageUrlBean.java
 * create time: 16-8-30 下午9:26
 * desc:
 **/
public class ImageUrlBean {
    private String image_url;					//图片地址
    private String first_moudle;				//该图片属于那个大module  在客户端模块下显示
    private String sencond_module;
    private String video_info;					//该图片的视频介绍信息
    private String video_url;					//视频地址
    private String play_times;				//视频播放次数
    private String up_times;					//顶赞次数
    private String extra;							//额外参数


    public static ImageUrlBean toBean(JSONObject jsonObject){
        ImageUrlBean bean = new ImageUrlBean();

        bean.image_url = jsonObject.optString("image_url");
        bean.first_moudle = jsonObject.optString("first_moudle");
        bean.sencond_module = jsonObject.optString("sencond_module");
        bean.video_info = jsonObject.optString("video_info");
        bean.video_url = jsonObject.optString("video_url");
        bean.play_times = jsonObject.optString("play_times");
        bean.up_times = jsonObject.optString("up_times");
        bean.extra = jsonObject.optString("extra");

        return bean;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setFirst_moudle(String first_moudle) {
        this.first_moudle = first_moudle;
    }

    public void setSencond_module(String sencond_module) {
        this.sencond_module = sencond_module;
    }

    public void setVideo_info(String video_info) {
        this.video_info = video_info;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public void setPlay_times(String play_times) {
        this.play_times = play_times;
    }

    public void setUp_times(String up_times) {
        this.up_times = up_times;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getFirst_moudle() {
        return first_moudle;
    }

    public String getSencond_module() {
        return sencond_module;
    }

    public String getVideo_info() {
        return video_info;
    }

    public String getVideo_url() {
        return video_url;
    }

    public String getPlay_times() {
        return play_times;
    }

    public String getUp_times() {
        return up_times;
    }

    public String getExtra() {
        return extra;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("image_url="+image_url);
        builder.append("first_module=" + first_moudle);
        builder.append("second_module=" + sencond_module);
        builder.append("video_info="+ video_info);
        builder.append("video_url=" + video_url);
        builder.append("play_times=" + play_times);
        builder.append("up_times=" + up_times);
        builder.append("extra=" + extra);

        return builder.toString();
    }
}
