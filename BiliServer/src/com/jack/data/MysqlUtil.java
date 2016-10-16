package com.jack.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jack.bean.ImageUrlBean;
import com.jack.bean.UserInfo;
import com.mysql.jdbc.PreparedStatement;



/**
 * 数据库工具类
 * 连接数据区
 * @author Jackzhous
 *
 */
public class MysqlUtil {
	
	private static MysqlUtil instance = null;

	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://127.0.0.1:3306/bilidata?user=root&password=wsdyi100&useSSL=false";
	
	/**
	 * 数据库任务类型
	 */
	public static final String TASK_CHECK_PHONE = "check_phone_register";				//查询账号注册任务
	public static final String TASK_REGISTER = "register_user";							//注册用户
	public static final String TASK_LOGIN = "login";									//登录
	public static final String TASK_TOKEN_VERIFY = "token_verify";
	public static final String TASK_GET_IMAGE = "refreshImage";
	public static final String TASK_LOGOUT = "logout";
	
	private Connection conn;
	private Statement stmt;
	
	private MysqlUtil(){
		init();
	}
	
	public static MysqlUtil getInstance(){
		if(null == instance){
			instance = new MysqlUtil();
		}
		
		return instance;
	}
	
	
	public void init(){
		try {
			Class.forName(driver);
			
			conn = DriverManager.getConnection(url);
			
			if(null == conn || conn.isClosed()){
				System.out.println("open mysql database error");
				return;
			}
			stmt = conn.createStatement();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//查找某张表里面的内容
	public ResultSet queryData(String table_name, String key, String value){
		ResultSet result = null;
		String sql = "select * from " + table_name + " where " + key + "='" + value + "';";
		System.out.println("mysql select --- " + sql);
		
		try {
			if(null != stmt){
				result = stmt.executeQuery(sql);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("执行sql 查询出错，错误信息" + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 注册用户,插入用户信息
	 * @param user
	 * @throws SQLException 
	 */
	public boolean insertUser(UserInfo user) throws SQLException{
		String sql = "insert into bili_user (account, password, nickname, phone, register_time, icon_url) value(?, ?, ?, ?, ?, ?)";
		PreparedStatement pStmt = (PreparedStatement) conn.prepareStatement(sql);
		
		pStmt.setString(1, user.getAccount());
		pStmt.setString(2, user.getPasswd());
		pStmt.setString(3, user.getNickname());
		pStmt.setString(4, user.getPhone());
		pStmt.setString(5, getNowTime());
		pStmt.setString(6, user.getIcon_url());
		
		int result = pStmt.executeUpdate();
		pStmt.close();
		if(result > 0){
			return true;
		}
		
		return false;
	}
	
	
	private String getNowTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		String date = format.format(new Date());
		return date;
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		
		stmt.close();
		conn.close();
	}
	
	/**
	 * 初始化表image_url数据
	 * insert into image_url(image_url, first_module, sencond_module, video_info, play_times, up_times) value
	 *  ("image/img_tips_error_banner_tv.png", "recommend_module", "hot_modlue", "王宝强", "100", "50");
	 * @throws SQLException 
	 */
	public boolean insert_image_url(ImageUrlBean image_bean) throws SQLException{
		String sql = "insert into image_url(image_url, first_module, sencond_module, video_info, play_times, up_times) value (?, ?, ? ,? ,?, ?)";
		
		PreparedStatement pStmt = (PreparedStatement) conn.prepareStatement(sql);
		
		pStmt.setString(1, image_bean.getImage_url());
		pStmt.setString(2, image_bean.getFirst_moudle());
		pStmt.setString(3, image_bean.getSencond_module());
		pStmt.setString(4, image_bean.getVideo_info());
		pStmt.setString(5, image_bean.getPlay_times());
		pStmt.setString(6, image_bean.getUp_times());
		
		int result = pStmt.executeUpdate();
		pStmt.close();
		if(result > 0){
			return true;
		}
		
		return false;
	}
	
	
	public ArrayList<ImageUrlBean> query_image_url(String module) throws SQLException{
		
		ArrayList<ImageUrlBean>  aList = new ArrayList<>();
		String sql = "select * from image_url where first_module = ?";
		
		PreparedStatement pStmt = (PreparedStatement)conn.prepareStatement(sql);
		pStmt.setString(1, module);
		
		ResultSet result = pStmt.executeQuery();
		while(result.next()){
			ImageUrlBean bean = new ImageUrlBean();
			bean.setImage_url(result.getString("image_url"));
			bean.setFirst_moudle(result.getString("first_module"));
			bean.setSencond_module(result.getString("sencond_module"));
			bean.setVideo_info(result.getString("video_info"));
			bean.setPlay_times(result.getString("play_times"));
			bean.setUp_times(result.getString("up_times"));
			bean.setVideo_url(result.getString("video_url"));
			
			aList.add(bean);
		}
		
		return aList;
	}
}
