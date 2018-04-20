package com.suramire.miaowu.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/30.
 */

public class SdcardLogTools {

    public static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS");

    public static String saveCrashInfo2File(String logInfo) {

        StringBuffer sb = new StringBuffer();


        String result = logInfo + "\n";
        sb.append(SdcardLogTools.formatter.format(new Date()) + "   ");
        sb.append(result);
        sb.append('\n');
        try {
            String fileName = "log.txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//				String path = "/sdcard/crash/";
                String path = Environment.getExternalStorageDirectory().getPath()
                        + "/maiowuLog/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName,true);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e("SdcardLogTools", "an error occured while writing file...", e);
        }
        return null;
    }

    public static String saveCrashTime2File(String logInfo) {

        StringBuffer sb = new StringBuffer();


        String result = logInfo + "\n";
        sb.append(SdcardLogTools.formatter.format(new Date()) + "   ");
        sb.append(result);
        sb.append('\n');
        try {
            String fileName = "time.txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//				String path = "/sdcard/crash/";
                String path = Environment.getExternalStorageDirectory().getPath()
                        + "/LiveFace/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName,true);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e("SdcardLogTools", "an error occured while writing file...", e);
        }
        return null;
    }
}
