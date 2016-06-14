package com.jack.zhou.bili.ui;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.common.api.GoogleApiClient;
import com.jack.zhou.bili.util.JLog;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by "jackzhous" on 2016/6/14.
 */
public class SplashActivity extends Activity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = getWindow();
        WindowManager.LayoutParams wparams = window.getAttributes();
        wparams.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        window.setAttributes(wparams);

        AssetManager asset = getResources().getAssets();
        Bitmap splash = null;

        RelativeLayout layout = new RelativeLayout(this);
        ImageView image1 = new ImageView(this);
        ImageView image2 = new ImageView(this);
        RelativeLayout.LayoutParams params = null;
        InputStream stream = null;

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


        } catch (IOException e) {
            JLog.default_print("闪屏资源找不到了，无法闪屏");
            e.printStackTrace();
        }

        layout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(layout);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

}
