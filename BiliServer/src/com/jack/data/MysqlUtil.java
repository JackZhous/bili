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
import com.jack.bean.LiveShowUserBean;
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
	
	/**
	 * 查询在直播的用户
	 * @return 直播用户地址
	 */
	public ArrayList<LiveShowUserBean> query_live_video_url(){
		String sql = "select * from bili_user where is_video_show='true'";
		ArrayList<LiveShowUserBean> data = new ArrayList<>();
		
		try {
			PreparedStatement pStmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet result = pStmt.executeQuery();
			while(result.next()){
				LiveShowUserBean bean = new LiveShowUserBean();
				String nickname = result.getString("nickname");
				String url = result.getString("video_url");
				bean.setLive_show_url(url);
				bean.setNickname(nickname);
				data.add(bean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
		
	}
	
	
	/**
	 * @param table 表名
	 * @param select_key 查找的关键值字段名称
	 * @param select_value 查找关键值字段值
	 * @param result_key 被查找字段名称
	 * @return 被查找字段的值
	 * 例子： select result_key from table where slect_key=select_value
	 * @throws SQLException 
	 */
	public String query_you_want(String table, String select_key, int select_value, String result_key) throws SQLException{
		String sql = "select * from bili_user where uid=?";
		PreparedStatement pStmt = (PreparedStatement)conn.prepareStatement(sql);
		//pStmt.setString(1, result_key);
//		pStmt.setString(1, table);
//		pStmt.setString(2, select_key);
		pStmt.setInt(1, select_value);
		
		String result_vlue = null;
		ResultSet result = pStmt.executeQuery();
		while(result.next()){
			result_vlue = result.getString("nickname") ;
		}
		System.out.println("result value " + result_vlue);
		return result_vlue;
	}
	
	/**
	 * 更新表里面的内容
	 * @param table
	 * @param set_key
	 * @param set_value
	 * @param select_key
	 * @param select_value
	 * @throws SQLException 
	 */
	public void update_biliuser_table_is_video_show( String set_value,int select_value) throws SQLException{
		String sql = "update bili_user set is_video_show=? where uid=?";
		PreparedStatement pStmt = (PreparedStatement)conn.prepareStatement(sql);
		pStmt.setString(1, set_value);
		pStmt.setInt(2, select_value);
		
		pStmt.executeUpdate();
		
	}
	
	
	 
	
}
