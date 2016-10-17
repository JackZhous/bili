package com.jack.zhou.bili.util;

public class Constant {
	public static final String TASK_FLAG = "task_flag";
	
	public static final String FLAG_PUSH_ADDRESS = "push_address";
	public static final String PUSH_DIR = "jackL";														//直播流地址： 直播服务器nginx地址  + PUSH_DIR + 用户uid
	
	/**
	 * 数据库任务类型
	 */
	public static final String TASK_CHECK_PHONE = "check_phone_register";				//查询账号注册任务
	public static final String TASK_REGISTER = "register_user";							//注册用户
	public static final String TASK_LOGIN = "login";									//登录
	public static final String TASK_TOKEN_VERIFY = "token_verify";
	public static final String TASK_GET_IMAGE = "refreshImage";
	public static final String TASK_LOGOUT = "logout";
	
	
	
	public static final String RESULT = "result";
	public static final String MESSAGE = "message";
	
	public static final String SUCCESS = "ok";
	public static final String FAILED = "fail";
	public static final String URL = "url";
}
