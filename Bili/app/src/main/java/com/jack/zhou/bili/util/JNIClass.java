package com.jack.zhou.bili.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密部分和JNI代码部分
 * Created by jackzhous on 16-7-2.
 */
public class JNIClass {

    static{
        System.loadLibrary("myjni");
    }

    private static char hexDigits[] = {'a', '1', '2', '3', '4', '5', 'b', 'e', '8', '9', '0', '6', 'c', 'd', '7', 'f'};

    public native static byte[] encoding(String str);              //加密

    public native static String decoding(byte[] str);              //解密

    public static String getMD5Char(String str){
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

            return  new String(final_words);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            JLog.default_print("md5 error " + e.getMessage());
            return null;
        }
    }
}
