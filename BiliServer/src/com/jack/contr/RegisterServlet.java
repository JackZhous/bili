package com.jack.contr;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.TextUI;

import com.jack.bean.UserFactory;
import com.jack.bean.UserInfo;
import com.jack.data.MysqlUtil;
import com.jack.zhou.bili.util.ServletUtil;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet -----------");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("do post -------------------  register servlet");
		String flag = request.getParameter("task_flag");
		HashMap<String, String> data = new HashMap<>();
		String result = null;
		String msg = null;
		if(flag == null || flag.equals("")){
			System.out.println("请求任务类型不明确，不知道你要干嘛");
			data.put("result", "fail");
			data.put("message", "无法知道请求任务类型， do not know your request");
			ServletUtil.getInstance().responseToClient(data, response);
			return;
		}
		
		
		if(MysqlUtil.TASK_CHECK_PHONE.equals(flag)){						//查询手机号是否被注册过
			
			System.out.println("请求任务类型  === 查询手机号是否被注册过");
			System.out.println("phone no " + request.getParameter("phone"));
			String phone = request.getParameter("phone");
			
			ResultSet set = MysqlUtil.getInstance().queryData("bili_user", "phone", phone);
			try {
				if(set.next()){
					result = "fail";
					msg = "该电话号码已被注册了";
				}else{
					result = "ok";
					msg = "该电话号码可以注册";
				}
			} catch (SQLException e) {
				result = "fail";
				msg = "数据库查询出错";
			}
		}else if(MysqlUtil.TASK_REGISTER.equals(flag)){						//注册账号
			System.out.println("请求任务类型  === 用户注册");
			UserInfo user = UserFactory.newUerInfo(request);
			try {
				MysqlUtil.getInstance().insertUser(user);
				result = "ok";
				msg = "注册成功";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				result = "fail";
				msg = "注册失败，服务器抛了一个异常" + e.getMessage();
			}
		}
		
		
		data.put("result", result);
		data.put("message", msg);
		System.out.println("result " + result + " message " + msg);
		ServletUtil.getInstance().responseToClient(data, response);
	}

}
