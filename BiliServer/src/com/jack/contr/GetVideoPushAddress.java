package com.jack.contr;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jack.data.MysqlUtil;
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
		System.out.println("getPushAddress Get");
		
	}

	/**
	 * 获取用户的推流直播地址： 
	 * 1. 检查用户传上来的token是否失效
	 * 2. 失效提示她重新登陆
	 * 3. 没有失效就返回用户昵称和uid，实质推流地址 == 用户昵称 + uid保证地址的唯一性
	 * 4. 由于直播一般时间都比较长，需要将它直播状态写入到用户表的is_video_show字段里面去， 1表示在直播，0表示没有直播
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("getvideo push post");
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
					String name = null;
					try {
						name = MysqlUtil.getInstance().query_you_want("bili_user", "uid", Integer.parseInt(uid), "nickname");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					data.put("nickname", name);
					data.put(Constant.RESULT, Constant.SUCCESS);
					data.put(Constant.UID, uid);
				}
			}else{
				msg = "token失效，请重新登陆";
			}
			
			data.put(Constant.MESSAGE, msg);
			ServletUtil.getInstance().responseToClient(data, response);
		}
		
	}

}
