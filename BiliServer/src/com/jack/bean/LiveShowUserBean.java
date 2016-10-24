package com.jack.bean;

import org.json.JSONObject;

public class LiveShowUserBean {
	private String nickname;
	private String live_show_url;
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setLive_show_url(String live_show_url) {
		this.live_show_url = live_show_url;
	}
	public String getNickname() {
		return nickname;
	}
	public String getLive_show_url() {
		return live_show_url;
	}
	
	
	public String toString() {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("video_show_url", getLive_show_url());
		jsonObject.put("nickname", getNickname());
		
		return jsonObject.toString();
	}
	
}
