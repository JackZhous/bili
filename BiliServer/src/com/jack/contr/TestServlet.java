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

import com.jack.data.MysqlUtil;
import com.jack.zhou.bili.util.JNIClass;

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
		System.out.println(request.getParameter("login") + "  this is login");
		System.out.println("---------------------");
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		Map<String ,String> map =new HashMap<String ,String>();
		map.put("result", "ok");
		map.put("reult", "欧克");
		
		System.out.println("sadfjkl" + System.getProperty("java.library.path"));
		JSONObject sjon = new JSONObject(map);
		System.out.println("sjon " + sjon.toString());
		out.write(sjon.toString());
		out.flush();
		out.close();
		
		
		String tt = request.getParameter("login");
		System.out.println("primary data is " + tt);
		byte[] b = tt.getBytes();
		System.out.println("primary byte data is " + b);
		String str = JNIClass.decoding(b);
		System.out.println("string is "+ str);
	//	MysqlUtil.getInstance().init();
	}

}

