package com.jack.zhou.bili.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by jackzhous on 16-7-7.
 */
public class Country {

    private String country_name;
    private Drawable icon_id;
    private String country_phone;       //  区号

    public String getCountry_phone() {
        return country_phone;
    }

    public void setCountry_phone(String country_phone) {
        this.country_phone = country_phone;
    }

    public Drawable getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(Drawable icon_id) {
        this.icon_id = icon_id;
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
}
