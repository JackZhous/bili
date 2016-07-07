package com.jack.zhou.bili.ui;

import android.app.AlertDialog;

import android.content.DialogInterface;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;

import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.ListView;
import android.widget.TextView;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * 用户注册界面
 * Created by "jackzhous" on 2016/7/5.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_verify;      //获取验证码按钮
    private EditText ed_phone;      //电话
    private String appkey = "14ad7047d60ee";
    private String appsecrect = "4f8a62e8e4d3595b27a1ddc31ea3be47";

    private ListView listCountry;
    private int selectedListId = 0;
    private MyListApapter lisAdapter;
    private Drawable selectedIcon;
    private Drawable unselectedIcon;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtil.integrationNotifcationBar(this);
        initLayoutResouce();

        initSMSSDK();
    }

    /**
     * 初始化短信sdk
     */
    private void initSMSSDK(){
        SMSSDK.initSDK(this, appkey, appsecrect);


        SMSSDK.registerEventHandler(handler);

        SMSSDK.getSupportedCountries();
    }


    private  EventHandler handler = new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            if(SMSSDK.RESULT_COMPLETE == result){
                JLog.default_print("短信回调完成");
                if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                    JLog.default_print("提交短信验证码成功");
                }else if(event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    JLog.default_print("获取短信验证码成功");
                }else if(event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    JLog.default_print("获取短信支持的国家列表");
                }
            }else{
                JLog.default_print("短信sdk程序异常");
                ((Throwable)data).printStackTrace();
            }
        }
    };

    /**
     * 初始化布局资源
     */
    private void initLayoutResouce(){

        this.setContentView(R.layout.activity_register);
        Toolbar login_bar = (Toolbar) this.findViewById(R.id.login_bar);
        setSupportActionBar(login_bar);
        login_bar.setTitle("注册账号");

        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);       //使用toolbar左上角图标可点击可显示
            supportActionBar.setTitle("注册账号");
        }
        //点击返回  回到主页
        login_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_verify = (Button)this.findViewById(R.id.btn_verify);
        ed_phone = (EditText)this.findViewById(R.id.ed_phone);
        btn_verify.setOnClickListener(this);

        //检测edtext内容有变化，就变化btn_verify的样式
        ed_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                JLog.default_print("beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                JLog.default_print("onTextChanged");
                if (TextUtils.isEmpty(ed_phone.getText())) {
                    btn_verify.setEnabled(false);
                } else {
                    btn_verify.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                JLog.default_print("afterTextChanged");
            }
        });

        TextView tv = (TextView)this.findViewById(R.id.show_verify_des);
        SpannableStringBuilder style = new SpannableStringBuilder(getResources().getString(R.string.tv_verify_des));
        style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(RegisterActivity.this, R.color.colorPrimary)), 15, 19, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv.setText(style);                                                                                                                                       //设置部分字体颜色

        //listview icon图标
        unselectedIcon = ContextCompat.getDrawable(RegisterActivity.this, R.drawable.abc_btn_radio_to_on_mtrl_000);
        DrawableCompat.setTint(unselectedIcon, ContextCompat.getColor(RegisterActivity.this, R.color.gray));
        selectedIcon = ContextCompat.getDrawable(RegisterActivity.this, R.drawable.abc_btn_radio_to_on_mtrl_015);
        DrawableCompat.setTint(selectedIcon, ContextCompat.getColor(RegisterActivity.this, R.color.colorPrimary));
    }


    /**
     * 选择country监听  弹出对话框
     * @param v
     */
    public void onSelectCountry(View v){

        if(dialog != null){
            dialog.show();
            return;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("地区选择");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        if(listCountry == null){
            listCountry = initListCountryView();
        }

        builder.setView(listCountry);
        builder.create();
        dialog = builder.show();
    }

    /**
     * 获取一个ListView,ListView显示国家名
     * @return
     */
    private ListView initListCountryView(){

        //ArrayList<country> list = initListViewData(selectedListId);
        listCountry = new ListView(this);
       // lisAdapter = new MyListApapter(list);
      //  listCountry.setAdapter(lisAdapter);
        listCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JLog.default_print("item " + position + "was click");
                //第一次和第二次选择的item不一样，要修改vieew的状态
                if(position != selectedListId){
                    lisAdapter.mItem[selectedListId] = lisAdapter.makeItemView("unslected", unselectedIcon);
                    lisAdapter.mItem[position] = lisAdapter.makeItemView("slected", selectedIcon);
                    selectedListId = position;
                }
                dialog.cancel();
            }
        });

        listCountry.setDividerHeight(0);
        return listCountry;
    }


    /**
     * 初始化一个List  用于ListView的数据
     * @return
     */
    private ArrayList<country> initListViewData(int id, HashMap<String, Object> map){
        ArrayList<country> list = new ArrayList<country>();

        /**
         * 设置ListView选中和未选中的两种drawable及其tint图片
         */
        for(Map.Entry<String, Object> m : map.entrySet()){
            JLog.default_print(" " + m.getKey() + " " + m.getValue());
        }

        for(int i = 0; i < 20; i++){
            country c = new country();
            c.setCountry_name("国家" + i);
            c.setCountry_phone("电话" + i);
            c.setIcon_id(unselectedIcon);
            if(i == id)
                c.setIcon_id(selectedIcon);
            list.add(c);
        }
        return list;
    }

    /**
     * 自定义内部类，List显示的数据模型
     */
    public class country{
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
    }

    /**
     * ListView适配器
     */
    private class MyListApapter extends BaseAdapter{

        View[] mItem;

        public MyListApapter(ArrayList<country> list){
            mItem = new View[list.size()];
            //将试图添加到view里面去
            for(int i = 0 ; i < mItem.length; i++){
                country c = list.get(i);
                mItem[i] = makeItemView(c.getCountry_name(), c.getIcon_id());
            }
        }


        private View makeItemView(String name, Drawable drawable){
            LayoutInflater inflater = RegisterActivity.this.getLayoutInflater();

            View item = inflater.inflate(R.layout.list_country, null);
            ImageView image = (ImageView)item.findViewById(R.id.icon);
            image.setImageDrawable(drawable);

            TextView tv = (TextView)item.findViewById(R.id.country);
            tv.setText(name);

            return item;
        }

        @Override
        public int getCount() {
            return mItem.length;
        }

        @Override
        public Object getItem(int position) {
            return mItem[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * getView  产生view后显示条目到界面上去， convertview是
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                return mItem[position];

        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        SMSSDK.unregisterEventHandler(handler);
    }


    @Override
    public void onClick(View v) {

    }
}
