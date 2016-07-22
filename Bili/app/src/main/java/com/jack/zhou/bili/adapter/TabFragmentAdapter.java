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

package com.jack.zhou.bili.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by "jackzhous" on 2016/7/22.
 */
public class TabFragmentAdapter extends FragmentStatePagerAdapter{

    private ArrayList<Fragment> listFragment;
    private ArrayList<String>  title;

    public TabFragmentAdapter(FragmentManager fm, ArrayList<Fragment> listF, ArrayList<String> title){
        super(fm);
        this.listFragment = listF;
        this.title = title;
    }



    @Override
    public Fragment getItem(int position) {

        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
