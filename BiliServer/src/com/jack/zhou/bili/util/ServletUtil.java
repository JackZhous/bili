package com.jack.zhou.bili.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class ServletUtil {
	private static ServletUtil instance;
	
	private ServletUtil(){}
	
	
	public static ServletUtil getInstance(){
		if(instance == null){
			instance = new ServletUtil();
		}
		
		return instance;
	}
	
	public void responseToClient(Map data, HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		JSONObject sjon = new JSONObject(data);
		System.out.println("sjon " + sjon.toString());
		out.write(sjon.toString());
		out.flush();
		out.close();
	}
	
}
