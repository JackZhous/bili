package com.jack.bean;

public class UserInfo {
	private String account;				//登录的账户
	private int uid;					//用户id  唯一标识
	private String passwd;				//密码
	private String nickname;			//昵称
	private String icon_url = "";			//icon地址
	private String sex = "S";					//性别
	private String birthday = "1980-01-01";			//出生年月日
	private String phone;				//电话
	private String email = "";				//电子邮箱
	private String show_self = "";			//个人简介
	private String extra = "";				//额外参数
	
	
	
	public void setAccount(String account) {
		this.account = account;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public void setPasswd(String passwd) {
		
		this.passwd = passwd;
	}
	public void setNickname(String nickname) {
		
		this.nickname = nickname;
	}
	public void setIcon_url(String icon_url) {
		
		this.icon_url = icon_url;
	}
	public void setSex(String sex) {
		
		this.sex = sex;
	}
	public void setBirthday(String birthday) {
		
		this.birthday = birthday;
	}
	public void setPhone(String phone) {
		
		this.phone = phone;
	}
	public void setEmail(String email) {
		
		this.email = email;
	}
	public void setShow_self(String show_self) {
		
		this.show_self = show_self;
	}
	public void setExtra(String extra) {
		
		this.extra = extra;
	}
	public String getAccount() {
		return account;
	}
	public int getUid() {
		return uid;
	}
	public String getPasswd() {
		return passwd;
	}
	public String getNickname() {
		return nickname;
	}
	public String getIcon_url() {
		return icon_url;
	}
	public String getSex() {
		return sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public String getShow_self() {
		return show_self;
	}
	public String getExtra() {
		return extra;
	}
	
}
