package com.jack.zhou.bili.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class MD5Util {
	
	private static char hexDigits[] = {'a', '1', '2', '3', '4', '5', 'b', 'e', '8', '9', '0', '6', 'c', 'd', '7', 'f'};
	private static final int avalible_time = 10 * 24 * 60 * 60 * 1000;					//token的最长时间
	
	/**
     * token列表    装载了每一个用户的token/phone/时间戳
     * token和时间戳  用于维持客户端的登陆状态
     * phone相当于用户的唯一标志，区分每一个用户
     */
    private static ArrayList<HashMap<String, String>> tokenList = new ArrayList<>();								//
    
    /**
     * 获取用户的token,token算法为 MD5(用户电话号码 + "时间戳")
     * @param uid
     * @return
     */
    public static String getUserToken(String phone){
    	HashMap<String, String> map = new HashMap<>();
    	
    	long time_stamp = System.currentTimeMillis();
    	
    	String str = phone + "jackzhous" + time_stamp;
    	byte[] b = str.getBytes();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(b);

            byte[] sec_words = digest.digest();                         //获取密文

            //转换密文为16进制  一个byte占一个字节8bit  所以存放的字符串密文是原来的2倍
            int length = sec_words.length;
            char final_words[] = new char[2 * length];
            int k = 0;
            for(int i = 0 ; i < length; i++){
                final_words[k++] = hexDigits[sec_words[i] >>> 4 & 0x0f];                    // >>> 无符号右移运算符，去高八位
                final_words[k++] = hexDigits[sec_words[i] & 0x0f];
            }
            
            String token = new String(final_words);
            map.put("time", ""+time_stamp);
        	map.put("uid", phone);
        	map.put("token", token);
        	tokenList.add(map);
        	showTokenList();
            return  token;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public static void showTokenList(){
    	int i = 0;
    	for(HashMap<String, String> map : tokenList){
    		System.out.println("======== 用户 " + i + "=========");
    		System.out.println("uid "  + map.get("phone"));
    		System.out.println("token  "  + map.get("token"));
    		System.out.println("time "  + map.get("time"));
    		System.out.println("-------------------------------------------------------------------------------------");
    	}
    }
    
    /**
     * 检查token的时效性
     * @param token
     * @return
     */
    public static boolean checkToken(String token){
    	long time_now = System.currentTimeMillis();
    	int index = 0;
    	for(HashMap<String, String> map : tokenList){
    		String token0 = map.get("token");
    		if(token0.equals(token)){
    			long time_create = Long.parseLong(map.get("time"));
    			if(time_now - time_create < avalible_time){
    				return true;
    			}
    			tokenList.remove(index);
    			break;
    		}
    		index++;
    	}
    	return false;
    }
    
    /**
     * 注销并且把token去除掉
     * @param token
     * @return
     */
    public static boolean LogoutClearToken(String token){
    	int index = 0;
    	if(tokenList.isEmpty()){
    		return true;
    	}
    	
    	for(HashMap<String, String> map : tokenList){
    		String token0 = map.get("token");
    		if(token0.equals(token)){
    			tokenList.remove(index);
    			return true;
    		}
    	}
    	
    	return false;
    }
    	
    
}
