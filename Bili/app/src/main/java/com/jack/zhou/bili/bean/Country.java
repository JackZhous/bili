package com.jack.zhou.bili.bean;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.jack.zhou.bili.R;

/**
 * Created by jackzhous on 16-7-7.
 */
public class Country extends ParentBean{


    private String country_name;
    private String country_phone;       //  区号



    public String getCountry_phone() {
        return country_phone;
    }

    public void setCountry_phone(String country_phone) {
        this.country_phone = country_phone;
    }



    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    @Override
    public String toString() {
        return "country -- " + country_name + " | country_phone -- " + country_phone;
    }

    @Override
    public String getValue() {
        return country_name;
    }
}
