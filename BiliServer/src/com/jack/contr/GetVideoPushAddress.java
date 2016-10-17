package com.jack.contr;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jack.zhou.bili.util.Constant;
import com.jack.zhou.bili.util.MD5Util;
import com.jack.zhou.bili.util.ServletUtil;

/**
 * Servlet implementation class GetVideoPushAddress
 * 获取直播推流地址；
 * 一个用户 -- 对应一个直播地址
 * 客户端会传上一个token值，服务端会根据token值查找对应用户，在去对应用户去查找是否有直播地址，如果没有为他创建，有就直接取地址
 */
@WebServlet("/GetVideoPushAddress")
public class GetVideoPushAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetVideoPushAddress() {
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
		String flag = request.getParameter("task_flag");
		String result = "fail";
		String msg = "不明确你的任务";
		
		HashMap<String, String> data = new HashMap<>();
		
		System.out.println("task_flag "+ flag);
		data.put(Constant.RESULT, Constant.FAILED);
		if(Constant.FLAG_PUSH_ADDRESS.equals(flag)){
			String token = request.getParameter("token");
			boolean isTokenAvaliable = MD5Util.checkToken(token);
			if(isTokenAvaliable){
				String uid = MD5Util.getUid(token);
				if(uid != null){
					msg = "操作成功";
					data.put(Constant.RESULT, Constant.SUCCESS);
					String url = Constant.PUSH_DIR+uid;
					data.put(Constant.URL, url);
				}
			}else{
				msg = "token失效，请重新登陆";
			}
			
			data.put(Constant.MESSAGE, msg);
			ServletUtil.getInstance().responseToClient(data, response);
		}
		
	}

}
