package com.jack.zhou.bili.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jack.bean.ImageUrlBean;
import com.jack.bean.LiveShowUserBean;

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
	
	
	public void responseToClient(ArrayList<ImageUrlBean> data, HttpServletResponse response) throws IOException{
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		JSONArray jArray = new JSONArray();
		int count = 0;
		for(ImageUrlBean bean : data){
			System.out.println("bean " + bean.toString());
			jArray.put(bean.toString());
			count++;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "ok");
		jsonObject.put("count", count);
		jsonObject.put("data", jArray);
		
		System.out.println("sjon " + jsonObject.toString());
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	
	public void responseToClient1(ArrayList<LiveShowUserBean> data, HttpServletResponse response) throws IOException {
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		JSONArray jArray = new JSONArray();
		int count = 0;
		for(LiveShowUserBean bean : data){
			System.out.println("bean " + bean.toString());
			jArray.put(bean.toString());
			count++;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(Constant.RESPONSE, Constant.TASK_GET_ALL_LIVE_SHOW);
		jsonObject.put("result", "ok");
		jsonObject.put("count", count);
		jsonObject.put("data", jArray);
		
		System.out.println("sjon " + jsonObject.toString());
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}
}
