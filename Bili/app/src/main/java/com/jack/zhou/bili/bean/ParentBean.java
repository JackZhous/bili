/*
 *         Copyright (C) 2016-2017 宙斯
 *         All rights reserved
 *
 *        filename :Class4
 *        description :
 *
 *         created by jackzhous at  11/07/2016 12:12:12
 *         http://blog.csdn.net/jackzhouyu
 */

package com.jack.zhou.bili.bean;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.jack.zhou.bili.R;

/**
 * author: jackzhous
 * file : ParentBean.java
 * create time: 16-10-24 下午11:09
 * desc:
 **/
public abstract class ParentBean {


    private boolean isSelectId = false;


    public boolean getisSelectId() {
        return isSelectId;
    }

    public void setisSelectId(boolean icon_id) {
        this.isSelectId = icon_id;
    }


    //返回你想要的东西
    public abstract String getValue();

}
