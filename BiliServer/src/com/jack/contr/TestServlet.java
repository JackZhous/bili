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

import com.google.gson.JsonObject;

/**
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
		    System.out.println("++++++++++++++++===");
			System.out.println(request.getParameter("params"));
			System.out.println("---------------------");
			doPost(request,response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("postpost");
		System.out.println("++++++++++++++++===");
		System.out.println(request.getParameter("JSON"));
		System.out.println(request.getParameter("username") + request.toString());
		System.out.println(request.getParameter("password"));
		System.out.println("---------------------");
		PrintWriter out = response.getWriter();
		
		Map<String ,String> map =new HashMap<String ,String>();
		map.put("result", "ok");
		map.put("结果", "正确");
		
		JsonObject sJson = new JsonObject();
		sJson.addProperty("result", "ok");
		sJson.addProperty("结果", "正确");
		out.write(sJson.toString());
		out.flush();
		out.close();
		
	}

}
