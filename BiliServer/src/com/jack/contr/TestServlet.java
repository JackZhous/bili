package com.jack.contr;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.jack.data.MysqlUtil;
import com.jack.zhou.bili.util.MD5Util;
import com.jack.zhou.bili.util.ServletUtil;

/**
 * 登录的servlet
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TestServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		    System.out.println("doGet TestServlet");
			
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost TestServlet");
		String result = null;
		String msg = null;
		String flag = request.getParameter("task_flag");
		HashMap<String, String> data = new HashMap<>();
		System.out.println("请求任务类型  ===>>  " + flag);
		if(MysqlUtil.TASK_LOGIN.equals(flag)){
			String account = request.getParameter("username");
			String password = request.getParameter("password");
			System.out.println("password == " + password);
			
			ResultSet resultS = MysqlUtil.getInstance().queryData("bili_user", "account", account);
			try {
				if(resultS.next()){
					String passwd = resultS.getString(3);
					System.out.println("password == " + passwd);
					if(password.equals(passwd)){
						result = "ok";
						msg = "登录成功";
						data.put("token", MD5Util.getUserToken(resultS.getString(1)));
						data.put("icon_url", resultS.getString(5));
						data.put("nickname", resultS.getString(4));
						System.out.println("icon_url " +  resultS.getString(5));
					}else{
						result = "fail";
						msg = "用户名或密码错误";
					}
				}else{
					result = "fail";
					msg = "用户名或密码错误";
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = "fail";
				msg = "服务端错误了，正在修复中...";
			}
			
		}else{
			result = "fail";
			msg = "你请求的姿势好像不对...";
		}
		data.put("result", result);
		data.put("message", msg);
		ServletUtil.getInstance().responseToClient(data, response);
	}

}

