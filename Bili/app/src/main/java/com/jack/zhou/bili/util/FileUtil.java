package com.jack.zhou.bili.util;

import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 文件操作工具类，进行写入和读取
 * Created by jackzhous on 16-7-5.
 */
public class FileUtil {

    private static FileUtil instance;
    private Context context;
    private boolean isWriteable = false;            //对文件系统是否有操作的权限

    private FileUtil(Context context){
        this.context = context;
    }

    public static FileUtil getInstance(Context context){
        if(null == instance){
            instance = new FileUtil(context);
        }

        return instance;
    }

    /**
     * 查看是否有文件操作的权限
     * @return
     */
    private boolean checkFileable(){
        String status = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(status)){
            isWriteable = true;

        }
        JLog.default_print("isWriteable " + isWriteable);
        return isWriteable;
    }

    /**
     * 写入文件
     * @param filename
     * @param content
     */
    public void saveFile(String filename, String content){
        //判断是否有权限
        if((!isWriteable) && (!checkFileable())){

            JLog.default_print("no file operation");
            return;
        }

        String path = Environment.getExternalStorageDirectory() + File.separator + "bili";
        File fPath = new File(path);
        if(!fPath.exists()){
            JLog.default_print("create file failed");
            fPath.mkdir();
        }
        FileWriter writer = null;
        String file = fPath.getPath() + File.separator + filename;
        try {
            writer = new FileWriter(file, true);

            writer.write(content);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        JLog.default_print("create file success, file: " + fPath.getPath());

    }

}
