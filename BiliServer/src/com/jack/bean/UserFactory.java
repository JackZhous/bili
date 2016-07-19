package com.jack.bean;

import javax.servlet.http.HttpServletRequest;

public class UserFactory {
	
	public static UserInfo newUerInfo(HttpServletRequest request){
		UserInfo user = new UserInfo();
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		
		user.setAccount(phone);
		user.setNickname(nickname);
		user.setPasswd(password);
		user.setPhone(phone);
		
		return user;
	}
}
