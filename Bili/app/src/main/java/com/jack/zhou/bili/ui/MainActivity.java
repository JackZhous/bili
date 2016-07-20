package com.jack.zhou.bili.ui;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jack.zhou.bili.R;
import com.jack.zhou.bili.exception.CrashHandler;
import com.jack.zhou.bili.network.HeartBreakService;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.FileUtil;
import com.jack.zhou.bili.util.JLog;
import com.jack.zhou.bili.util.SharedPreferenceUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private long exit_time;                                 //退出时间
    private Toolbar toolbar;
    private SharedPreferenceUtil util;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        AppUtil.integrationNotifcationBar(this);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        navigationView.setNavigationItemSelectedListener(this);

        util = SharedPreferenceUtil.getInstance(this.getApplicationContext());
        util.init();
    }

    /**
     * activity_main里面的组件点击事件
     * @param
     */
    public void onViewClick(View v) {

        switch(v.getId()){
            case R.id.user_icon:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;
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
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            exit_time = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        if(TextUtils.isEmpty(util.getString("login_flag"))){
            return;
        }
        String nickname = util.getString("nickname");
        String icon_url = util.getString("icon_url");
        //if(nickname.equals(toolbar.getTitle())){}
        toolbar.setTitle(nickname);
        JLog.default_print("nickname " + nickname + " icon_url " + icon_url);
    }


}
