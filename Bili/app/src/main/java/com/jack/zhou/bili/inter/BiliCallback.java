package com.jack.zhou.bili.inter;

/**
 * Created by jackzhous on 16-6-22.
 */
public interface BiliCallback {

    public void onResponse(int code,Object msg);            //返回码  和 返回体
    public void onError(int code,String msg);            //返回码  和 返回体
}
