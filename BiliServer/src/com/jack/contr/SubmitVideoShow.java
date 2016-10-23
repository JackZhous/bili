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

/**
 * Servlet implementation class SubmitVideoShow
 * 提交直播信息
 */
@WebServlet("/SubmitVideoShow")
public class SubmitVideoShow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitVideoShow() {
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
		System.out.println("getvideo push post");
		String flag = request.getParameter("task_flag");
		String result = "fail";
		String msg = "不明确你的任务";
		
		HashMap<String, String> data = new HashMap<>();
		
		System.out.println("task_flag "+ flag);
		data.put(Constant.RESULT, Constant.FAILED);
		if(Constant.TASK_SUBMIT_VIDEO_SHOW.equals(flag)){
			String uid = request.getParameter("uid");
			String show_flag = request.getParameter("is_video_show");
			
			try {
				MysqlUtil.getInstance().update_biliuser_table_is_video_show(show_flag, Integer.parseInt(uid) );
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
