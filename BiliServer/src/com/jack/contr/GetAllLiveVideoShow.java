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

import com.jack.bean.ImageUrlBean;
import com.jack.data.MysqlUtil;
import com.jack.zhou.bili.util.Constant;
import com.jack.zhou.bili.util.ServletUtil;

/**
 * Servlet implementation class GetAllLiveVideoShow
 */
@WebServlet("/GetAllLiveVideoShow")
public class GetAllLiveVideoShow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllLiveVideoShow() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("GetAllLiveVideoShow doGet");
		ArrayList<HashMap<String,String>> data = MysqlUtil.getInstance().query_live_video_url();
		
		for(HashMap<String,String> map: data){
			System.out.println("url " + map.get("video_url") + "  nickname " + map.get("nickname"));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
