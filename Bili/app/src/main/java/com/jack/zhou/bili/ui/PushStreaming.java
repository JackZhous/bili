package com.jack.zhou.bili.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jack.zhou.bili.R;
import com.jack.zhou.jrecyclerview.util.JLog;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author: jackzhous
 * file : PushStreaming.java
 * create time: 16-10-7 下午9:38
 * desc:
 * 推流activity
 **/
public class PushStreaming extends Activity {
    private static final String TAG = "PushStreaming";
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init_resource_layout();
    }

    private void init_resource_layout(){
        setContentView(R.layout.activity_push_streaming);
        btn = (Button)findViewById(R.id.start);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String retStream = PushStreaming.this.requestStreamJson();
                        
                    }
                }).start();
            }
        });
    }



    private String requestStreamJson(){
        try{
            HttpURLConnection httpConn = (HttpURLConnection)new URL("rtmp://localhost:1395/mylive/home").openConnection();
            httpConn.setConnectTimeout(5000);
            httpConn.setReadTimeout(10000);
            int responseCode = httpConn.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_OK){
                return null;
            }

            int length = httpConn.getContentLength();
            if(length <= 0){
                return null;
            }

            InputStream inputStream = httpConn.getInputStream();
            byte[] buffer = new byte[length];
            int read = inputStream.read(buffer);
            inputStream.close();
            if(read <= 0){
                return  null;
            }

            return new String(buffer, 0, read);
        }catch (Exception e){
            e.printStackTrace();
            JLog.print(TAG, "network error, " + e.getMessage());
            return null;
        }
    }
}
