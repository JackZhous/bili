package com.jack.zhou.bili.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jack.zhou.bili.exception.CrashHandler;
import com.jack.zhou.bili.util.AppUtil;
import com.jack.zhou.bili.util.JLog;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by "jackzhous" on 2016/6/14.
 */
public class SplashActivity extends Activity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        AppUtil.integrationNotifcationBar(this);
        WindowManager.LayoutParams wparams = window.getAttributes();
        wparams.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        window.setAttributes(wparams);

        initView();
        CrashHandler.getInstance();
    }


    /**
     * 开启主类
     */
    private  void startMainActivity(){
        //RegisterActivity.class
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 从assets拿到闪屏资源， 组合在一个RelativeLayout里面
     * 实现动画闪屏
     */
    private void initView(){
        AssetManager asset = getResources().getAssets();
        Bitmap splash;

        layout = new RelativeLayout(this);
        ImageView image1 = new ImageView(this);
        ImageView image2 = new ImageView(this);
        RelativeLayout.LayoutParams params;
        InputStream stream;

        try {
            stream = asset.open("splash/ic_splash_copy.png");
            splash = BitmapFactory.decodeStream(stream);
            image2.setImageBitmap(splash);
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params.bottomMargin = 20;
            image2.setId(View.generateViewId());
            image2.setLayoutParams(params);
            layout.addView(image2);
            stream.close();

            stream = asset.open("splash/ic_splash_default.png");
            splash = BitmapFactory.decodeStream(stream);
            image1.setImageBitmap(splash);
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            image1.setLayoutParams(params);
            image1.setId(View.generateViewId());
            layout.addView(image1);
            stream.close();


        } catch (IOException e) {
            JLog.default_print("闪屏资源找不到了，无法闪屏");
            e.printStackTrace();
        }

        layout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        AlphaAnimation animation = new AlphaAnimation(1, 0);
        animation.setRepeatCount(0);
        animation.setDuration(2000);
        animation.setFillAfter(true);
        animation.setAnimationListener(new AnimationSplash(this));

        setContentView(layout);

        layout.setAnimation(animation);
        animation.startNow();
    }


    private static class AnimationSplash implements Animation.AnimationListener{
        WeakReference<SplashActivity> weakReference;
        public AnimationSplash(SplashActivity activity){
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            SplashActivity activity = weakReference.get();
            if(activity != null){
                activity.startMainActivity();
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
