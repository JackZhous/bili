package com.jack.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		pStmt.setString(5, user.getIcon_url());
		
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
	
	
	
	
}
