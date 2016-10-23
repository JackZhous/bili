package com.jack.zhou.bili.util;

import android.util.Log;

/**
 * Created by "jackzhosu" on 2016/6/14.
 */
public class JLog {

    private static boolean DEBUG_MODE = true;

    public static final String Default_TAG = "jackzhous";

    public static void default_print(String msg){
        if(DEBUG_MODE){
            Log.i(Default_TAG, "--- " + msg + " ---");
        }
    }

    public static void print_info(String TAG, String msg){
        if(DEBUG_MODE){
            Log.i(TAG, "--- " + msg + " ---");
        }
    }


    public static void print_error(String tag, String msg, Throwable ex){
        if(DEBUG_MODE){
            Log.e(tag, msg, ex);
        }
    }

}
