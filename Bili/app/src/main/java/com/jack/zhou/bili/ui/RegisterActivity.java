package com.jack.zhou.bili.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;

import android.content.DialogInterface;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
import com.jack.zhou.bili.bean.Country;
import com.jack.zhou.bili.inter.SMSModule;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;
import com.jack.zhou.bili.util.XMLUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * 用户注册界面
 * Created by "jackzhous" on 2016/7/5.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_verify;      //获取验证码按钮
    private EditText ed_phone;      //电话
    private TextView phone_zhui;    //电话号码前缀
    private TextView country;       //国家
    private TextView tv_phone_show; //提示语句


    private ListView listCountry;
    private int selectedListId = 0;
    private MyListAdapter lisAdapter;
    private AlertDialog dialog;
    private ArrayList<Country> countryList;


    private SMSModule smsModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtil.integrationNotifcationBar(this);

        XMLUtil.getInstance(this).init();               //初始化数据模块
        countryList = XMLUtil.getInstance(this).getCountryData();                   //显示数据
        smsModule = SMSModule.getInstance(this);
        smsModule.setMhandler(mHandler);
        smsModule.registerHandler();

        initLayoutResouce();

    }



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
        phone_zhui = (TextView)this.findViewById(R.id.phone_zhui);
        country = (TextView)this.findViewById(R.id.country);
        tv_phone_show = (TextView)findViewById(R.id.phone_show);
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

        countryList.get(0).setisSelectId(true);                                     //默认显示第一个
        listCountry = new ListView(this);                                           //listview
        lisAdapter = new MyListAdapter(countryList);
        listCountry.setAdapter(lisAdapter);
        listCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JLog.default_print("item " + position + "was click");
                //第一次和第二次选择的item不一样，要修改listview的数据里面的icon状态
                if (position != selectedListId) {
                    countryList.get(position).setisSelectId(true);
                    countryList.get(selectedListId).setisSelectId(false);               //交换icon颜色
                    //显示的文字国家名和区号
                    phone_zhui.setText("+ " + countryList.get(position).getCountry_phone());
                    country.setText(countryList.get(position).getCountry_name());
                    selectedListId = position;
                }
                dialog.cancel();
            }
        });

        listCountry.setDividerHeight(0);
        return listCountry;
    }

    static class ViewHolder{
        public ImageView image;
        public TextView tv;
    }

    /**
     * ListView适配器
     */
    private class MyListAdapter extends BaseAdapter{

        private LayoutInflater inflater;
        private ArrayList<Country> arrayList;

        public MyListAdapter(ArrayList<Country> list){

           inflater = RegisterActivity.this.getLayoutInflater();
           arrayList = list;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * getView  产生view后显示条目到界面上去， convertview是缓存的视图，可以重复利用
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            //如果缓冲试图convert为null就需要创建新的视图显示
            if(convertView == null){
                JLog.default_print("pos " + position + " null " +" -- ");
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.list_country, null);
                holder.image = (ImageView)convertView.findViewById(R.id.icon);
                holder.tv = (TextView)convertView.findViewById(R.id.country);
                convertView.setTag(holder);
            }else{
                JLog.default_print("pos " + position + " not null " +" -- ");
                holder = (ViewHolder)convertView.getTag();
            }
            Drawable icon = arrayList.get(position).getisSelectId() == true ? Country.selectedIcon : Country.unselectedIcon;
            holder.image.setImageDrawable(icon);
            holder.tv.setText(arrayList.get(position).getCountry_name());

            return convertView;
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        smsModule.unregisterSMSHandler();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_verify:       //验证
                getPhoneVerify();
                break;
            default:
                break;
        }
    }

    /**
     * 按键监听  用于发送验证码
     */
    public void getPhoneVerify(){
        String phone_no = ed_phone.getText().toString().trim();
        JLog.default_print("phone -- " + phone_no);
        if(TextUtils.isEmpty(phone_no) || phone_no.length() != 11){
            error_phone_animation(null);
            return;
        }


        SMSSDK.getVerificationCode(countryList.get(selectedListId).getCountry_phone(), phone_no);
    }

    /**
     * 错误电话提示，一个横向动画
     */
    private void error_phone_animation(String errormsg){
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(ed_phone,"translationX", 0F, -10F,20F,-20F,10F).setDuration(300);
        set.play(objectAnimator1);
        set.start();
        if(null != errormsg){
            tv_phone_show.setText(errormsg);
        }else{
            tv_phone_show.setText("请输入正确的手机号");

        }
        tv_phone_show.setTextColor(Color.RED);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        JLog.default_print("requestCode = " + requestCode);
        switch (requestCode){
            case AppUtil.FLAG_ACTIVITY:    //  Activity之间传递的信息量
                if(null == data){
                    return;
                }
                boolean closed = data.getBooleanExtra(AppUtil.CLOSED_ACTIVTY, false);

                if(closed){
                    this.finish();
                }
        }
    }

    /**
     * 用于接收短信模块发回来的信息
     */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case AppUtil.SMS_GET_VERIFICATION_CODE:             //获取短信验证码成功
                    String phone_no = ed_phone.getText().toString().trim();
                    Intent intent = new Intent(RegisterActivity.this, VerifySMS.class);
                    intent.putExtra("country", countryList.get(selectedListId).getCountry_phone());
                    intent.putExtra("phone_no",phone_no);
                    startActivityForResult(intent, AppUtil.FLAG_ACTIVITY);
                    break;
                case AppUtil.SMS_ERROR:                             //错误了
                    String str = (String)msg.obj;
                    error_phone_animation(str);
                    break;
            }
        }
    };


}
