package com.jack.contr;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jack.data.MysqlUtil;
import com.jack.zhou.bili.util.MD5Util;
import com.jack.zhou.bili.util.ServletUtil;

/**
 * Servlet implementation class VerifyToken
 */
@WebServlet("/VerifyToken")
public class VerifyToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyToken() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.print("doPost -------------------");
		String flag = request.getParameter("task_flag");
		String result = "fail";
		String msg = "不明确你的任务";
		
		String token = request.getParameter("token");
		if(MysqlUtil.TASK_TOKEN_VERIFY.equals(flag)){
			
			if(MD5Util.checkToken(token)){
				result = "ok";
				msg = "token正常";
			}else{
				result = "fail";
				msg = "token不存在或者已经失效";
			}
		}else if(MysqlUtil.TASK_LOGOUT.equals(flag)){
			if(MD5Util.LogoutClearToken(token)){
				result = "ok";
				msg = "注销成功";
			}else{
				result = "fail";
				msg = "注销失效";
			}
		}
		HashMap<String, String> data = new HashMap<>();
		data.put("result", result);
		data.put("message", msg);
		
		ServletUtil.getInstance().responseToClient(data, response);
	}

}
