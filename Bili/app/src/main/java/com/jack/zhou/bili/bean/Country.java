package com.jack.zhou.bili.bean;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.jack.zhou.bili.R;

/**
 * Created by jackzhous on 16-7-7.
 */
public class Country {

    public static  Drawable unselectedIcon;
    public static  Drawable selectedIcon;
    private String country_name;
    private boolean isSelectId = false;
    private String country_phone;       //  区号

    static {

    }

    public String getCountry_phone() {
        return country_phone;
    }

    public void setCountry_phone(String country_phone) {
        this.country_phone = country_phone;
    }

    public boolean getisSelectId() {
        return isSelectId;
    }

    public void setisSelectId(boolean icon_id) {
        this.isSelectId = icon_id;
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


    public static void initSelectedIcon(Context context){
        unselectedIcon = ContextCompat.getDrawable(context, R.drawable.abc_btn_radio_to_on_mtrl_000);
        DrawableCompat.setTint(unselectedIcon, ContextCompat.getColor(context, R.color.gray));
        selectedIcon = ContextCompat.getDrawable(context, R.drawable.abc_btn_radio_to_on_mtrl_015);
        DrawableCompat.setTint(selectedIcon, ContextCompat.getColor(context, R.color.colorPrimary));

    }

}
