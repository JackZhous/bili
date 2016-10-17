package com.jack.contr;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.PortableInterceptor.SUCCESSFUL;

import com.jack.bean.ImageUrlBean;
import com.jack.data.MysqlUtil;
import com.jack.zhou.bili.util.Constant;
import com.jack.zhou.bili.util.MD5Util;
import com.jack.zhou.bili.util.ServletUtil;
import com.mysql.jdbc.MySQLConnection;

/**
 * Servlet implementation class GetImageServlet
 */
@WebServlet("/refreshImage")
public class GetImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("refreshImage do get");
		ImageUrlBean bean = new ImageUrlBean();
		bean.setImage_url("image/img_tips_live_room_locked.png");
		bean.setFirst_moudle("recommend_module");
		bean.setSencond_module("live_play");
		bean.setVideo_info("over do not click");
		bean.setPlay_times("12233");
		bean.setUp_times("655");
		
		try {
			boolean success = MysqlUtil.getInstance().insert_image_url(bean);
			if(!success){
				System.out.println("插入失败");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("插入失败");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("get Image doPost");
		String flag = request.getParameter("task_flag");
		String result = "fail";
		String msg = "不明确你的任务";
		
		ArrayList<ImageUrlBean> list = null;
		if(Constant.TASK_GET_IMAGE.equals(flag)){				//获取图像
			String key = request.getParameter("module");
			try {
				list = MysqlUtil.getInstance().query_image_url(key);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			ServletUtil.getInstance().responseToClient(list, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
