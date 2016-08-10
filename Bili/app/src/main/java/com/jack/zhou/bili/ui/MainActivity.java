package com.jack.zhou.bili.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.adapter.TabFragmentAdapter;
import com.jack.zhou.bili.bean.ClassFragment;
import com.jack.zhou.bili.bean.LiveFragment;
import com.jack.zhou.bili.bean.RecommendFragment;
import com.jack.zhou.bili.inter.BiliCallback;
import com.jack.zhou.bili.inter.HttpListener;
import com.jack.zhou.bili.network.IOManager;
import com.jack.zhou.bili.network.Task;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;
import com.jack.zhou.bili.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , BiliCallback{

    private long exit_time;                                 //退出时间
    private Toolbar toolbar;
    private SharedPreferenceUtil util;
    private ImageView user_icon;
    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppUtil.integrationNotifcationBar(this);
        initLayoutResource();

        util = SharedPreferenceUtil.getInstance(this.getApplicationContext());
        util.init();
    }

    /**
     * 初始化布局文件
     */
    private void initLayoutResource(){
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("未登录");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        user_icon = (ImageView)headView.findViewById(R.id.user_icon);
        JLog.default_print("user_icon is --- " + user_icon);                                    //这种方式才能将左侧的view获取到

        navigationView.setNavigationItemSelectedListener(this);

        ArrayList<String> tabList = new ArrayList<>();
        tabList.add("直播");
        tabList.add("推荐");
        tabList.add("番剧");
        tabList.add("分区");
        tabList.add("关注");
        tabList.add("发现");

        viewPager = (ViewPager)findViewById(R.id.main_viewpager);
        tabLayout = (TabLayout)findViewById(R.id.tab);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);                                                            //设置所有的选项卡都填充在屏幕的宽度之内
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);                                                       //每个选项卡都填充自身布局
        tabLayout.addTab(tabLayout.newTab().setText("直播"));
        tabLayout.addTab(tabLayout.newTab().setText("推荐"));
        tabLayout.addTab(tabLayout.newTab().setText("番剧"));
        tabLayout.addTab(tabLayout.newTab().setText("分区"));
        tabLayout.addTab(tabLayout.newTab().setText("关注"));
        tabLayout.addTab(tabLayout.newTab().setText("发现"));

        ArrayList<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Fragment f1;
            if(i == 1){
                f1 = new RecommendFragment();
            }else if(i == 3){
                f1 = new ClassFragment();
            }else{
                f1 = new LiveFragment();
            }

            Bundle bundle = new Bundle();
            bundle.putString("content", "http://blog.csdn.net/feiduclear_up \n CSDN 废墟的树");
            f1.setArguments(bundle);
            fragmentList.add(f1);
        }

        TabFragmentAdapter fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, tabList);
        viewPager.setAdapter(fragmentAdapter);                                                          //给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPager);                                                        //将TabLayout和ViewPager关联起来。
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);                                             //给Tabs设置适配器
        viewPager.setCurrentItem(1);                                                                    //设置启动默认在哪个fragment
    }

    /**
     * activity_main里面的组件点击事件
     * @param
     */
    public void onViewClick(View v) {

        switch(v.getId()){
            case R.id.user_icon:
                click_icon_button();
                break;
            default:
                break;
        }
    }


    public void click_icon_button(){
        /**
         * 如果token不为空 那就要把token拿去验证了
         */
        String token = util.getString("token");
        if(!(TextUtils.isEmpty(token))){
            HashMap<String , String> data = new HashMap<String, String>();
            data.put("task_flag", "token_verify");                              //token验证
            data.put("token", token);
            HttpListener listener = new HttpListener(this);
            Task task = new Task(AppUtil.VERIFY_TOKEN, data, listener);
            IOManager.getInstance(this).add_task_start(task);
            return;
        }else{
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(System.currentTimeMillis() - exit_time > 2000){
                Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            }else{
                MainActivity.this.finish();
            }
            exit_time = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        user_icon = (ImageView)findViewById(R.id.user_icon);
        JLog.default_print("ovCreate user icon " + user_icon);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home_page) {
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        JLog.default_print("onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        JLog.default_print("onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        JLog.default_print("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JLog.default_print("onDestroy");

        util.putString("login_flag", "false");
    }



    @Override
    protected void onResume() {
        super.onResume();
        JLog.default_print("onResume");
        checkLogin();
    }

    /**
     * 检查是否登录
     */
    private void checkLogin(){
        String login_flag = util.getString("login_flag");
        JLog.default_print("login_flag" + login_flag);
        if(TextUtils.isEmpty(login_flag)){
            return;
        }

        //如果两次都是同一个用户就不用在请求的  因为请求会出现图标变化
        if(!(toolbar.getTitle().equals(util.getString("nickname")))){
            setUser_icon();
        }

    }

    private void setUser_icon(){
        String nickname = util.getString("nickname");
        String icon_url = util.getString("icon_url");
        //if(nickname.equals(toolbar.getTitle())){}
        toolbar.setTitle(nickname);
        JLog.default_print("nickname " + nickname + " icon_url " + icon_url);
        if("未登录".equals(nickname)){
            JLog.default_print("user_icon is null");
            user_icon.setImageResource(R.drawable.bili_default_avatar);
            return;
        }
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(user_icon, R.drawable.bili_default_avatar, R.drawable.bili_default_avatar);

        IOManager.getInstance(this).startImageTask(icon_url, listener);
    }


    @Override
    public void onResponse(int code, Object msg) {
        Intent intent = null;
        if(code == AppUtil.REQUEST_SUCCESS){
            intent = new Intent(this, BiliUserInfo.class);
        }else{
            intent = new Intent(this, LoginActivity.class);
        }
        this.startActivity(intent);
    }

    @Override
    public void onError(int code, Object obj) {
        JLog.default_print("http error " + obj);
    }

    /**
     * 排行榜监听
     * @param v
     */
    public void on_text_rank(View v){
        Toast.makeText(this, "你点击了排行榜", Toast.LENGTH_SHORT).show();
    }
}
