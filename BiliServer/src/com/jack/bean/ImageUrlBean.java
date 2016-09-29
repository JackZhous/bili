package com.jack.bean;

import org.json.JSONObject;

public class ImageUrlBean {
	private String image_url;					//图片地址
	private String first_moudle;				//该图片属于那个大module  在客户端模块下显示
	private String sencond_module;
	private String video_info;					//该图片的视频介绍信息
	private String video_url;					//视频地址
	private String play_times;				//视频播放次数
	private String up_times;					//顶赞次数
	private String extra;							//额外参数
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getFirst_moudle() {
		return first_moudle;
	}
	public void setFirst_moudle(String first_moudle) {
		this.first_moudle = first_moudle;
	}
	public String getSencond_module() {
		return sencond_module;
	}
	public void setSencond_module(String sencond_module) {
		this.sencond_module = sencond_module;
	}
	public String getVideo_info() {
		return video_info;
	}
	public void setVideo_info(String video_info) {
		this.video_info = video_info;
	}
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	public String getPlay_times() {
		return play_times;
	}
	public void setPlay_times(String play_times) {
		this.play_times = play_times;
	}
	public String getUp_times() {
		return up_times;
	}
	public void setUp_times(String up_times) {
		this.up_times = up_times;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("image_url", getImage_url());
		jsonObject.put("first_moudle", getFirst_moudle());
		jsonObject.put("sencond_module", getSencond_module());
		jsonObject.put("video_info", getVideo_info());
		jsonObject.put("video_url", getVideo_url());
		jsonObject.put("play_times", getPlay_times());
		jsonObject.put("up_times", getUp_times());
		jsonObject.put("extra", getExtra());
		
		return jsonObject.toString();
	}
	
	
	
}
