package com.jack.zhou.bili.util;

/**
 * Created by jackzhous on 16-7-2.
 */
public class JNIClass {

    static{
        System.loadLibrary("myjni");
    }

    public native  static String encoding(String str);              //加密

    public native static  String decoding(String str);              //解密

}
