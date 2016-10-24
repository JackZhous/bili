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

/**
 * author: jackzhous
 * file : LiveShowBean.java
 * create time: 16-10-24 下午11:16
 * desc:
 **/
public class LiveShowBean extends ParentBean {
    private String nickname;
    private String url;

    @Override
    public String getValue() {
        return nickname;
    }

    public String getUrl() {
        return url;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
