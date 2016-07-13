package com.jack.contr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class HeartServlet
 */
@WebServlet("/heart")
public class HeartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HeartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet -----------");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("dopost ------receiveing -----------------");
		System.out.println("params time -- " + request.getParameter("time"));
		System.out.println("params token -- " + request.getParameter("token"));
		
		response.setCharacterEncoding("UTF-8");
		System.out.println("dopost ------sending -----------------");
		PrintWriter out = response.getWriter();
		Map<String ,String> map =new HashMap<String ,String>();
		map.put("heart_result", "ok");
		JSONObject sjon = new JSONObject(map);
		
		out.write(sjon.toString());
		out.flush();
		out.close();
		
	}

}
