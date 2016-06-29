package com.jack.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * 数据库工具类
 * 连接数据区
 * @author Jackzhous
 *
 */
public class MysqlUtil {
	
	private static MysqlUtil instance = null;

	private static String driver = "com.mysql.jdbc.driver";
	private static String url = "jdbc:mysql://127.0.0.1:3306/test";
	private static String username = "jackzhous";
	private static String password = "wsdyi100";
	
	private MysqlUtil(){
		
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
			
			Connection conn = DriverManager.getConnection(url);
			
			if(null == conn || conn.isClosed()){
				System.out.println("open mysql database error");
				return;
			}
			Statement stmt = conn.createStatement();
			String sql = "select * from bili_user";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next()){
				System.out.println(res.getString(1));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
