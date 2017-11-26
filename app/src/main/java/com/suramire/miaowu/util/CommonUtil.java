package com.suramire.miaowu.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.suramire.miaowu.base.App;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import static com.makeramen.roundedimageview.RoundedImageView.TAG;

/**
 * Created by Suramire on 2017/10/16.
 * 包含一些常用方法
 */

public class CommonUtil {
    private static Context mContext = App.getContext();


    public static void snackBar(Context context,String message,String actionMessage, View.OnClickListener listener){
        Snackbar.make(((Activity)context).findViewById(android.R.id.content),message,Snackbar.LENGTH_INDEFINITE)
                .setAction(actionMessage,listener).show();
    }


    /**
     * 设置输入框的错误提示
     * @param textInputLayout 目标输入框的父TextInputLayout
     * @param errorString 错误提示文本
     */
    public static void showError(TextInputLayout textInputLayout, String errorString) {
        textInputLayout.setError(errorString);
        EditText editText = textInputLayout.getEditText();
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    /**
     * 加载图片
     * @param fileName 图片文件名
     * @return Bitmap
     */
    public static Bitmap getBitmap(String fileName){
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(mContext.getAssets().open(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 控制控件显示与隐藏
     * @param view 目标控件
     * @param views 目标控件的数组集合
     */
    public static void switchVisiable(ViewGroup view, ViewGroup[] views){
        for (int i = 0; i < views.length; i++) {
            ViewGroup tView = views[i];
            if(tView.getVisibility()!=View.GONE){
                tView.setVisibility(View.GONE);
                tView.setAnimation(AnimationUtils.makeOutAnimation(mContext, true));
            }
            if(view.getVisibility()!=View.VISIBLE){
                view.setVisibility(View.VISIBLE);
                view.setAnimation(AnimationUtils.makeInAnimation(mContext, true));
            }
        }
    }

    /**
     * 切换view的可见性
     * @param view
     */
    public static void toggleVisiable(View view){
        int visibility = view.getVisibility();
        if(visibility ==View.GONE ||visibility == View.INVISIBLE){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }
    }

    public static boolean isLogined(){
        return (int)SPUtils.get("uid",0) !=0;
    }

    /**
     * 判断字符串是否为手机号码格式
     * @param mobiles
     * @return
     */
    public static boolean isMobileNumber(String mobiles) {
        return Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[^1^4,\\D]))\\d{8}").matcher(mobiles).matches();
    }

    /**
     * 通过Uri获取对应文件
     * @param uri
     * @return
     */
    public static File getFileByUri(Uri uri) {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = mContext.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query(Images.Media.EXTERNAL_CONTENT_URI, new String[] { Images.ImageColumns._ID, Images.ImageColumns.DATA }, buff.toString(), null, null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex(Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index == 0) {
                } else {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // 4.2.2以后
            String[] proj = { Images.Media.DATA };
            Cursor cursor = mContext.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();

            return new File(path);
        } else {
            Log.i(TAG, "Uri Scheme:" + uri.getScheme());
        }
        return null;
    }

    /**
     * 得到当前时间戳
     * @return
     */
    public static Timestamp getTimeStamp(){
        return new Timestamp(new Date().getTime());
    }

    /**
     * 将时间戳转成日期字符串
     * @param timestamp
     * @return
     */
    public static String timeStampToDateString(Timestamp timestamp){
        SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sSimpleDateFormat.format(timestamp);
    }

    public static String getHowLongAgo(Timestamp timestamp){
        long l = getTimeStamp().getTime() - timestamp.getTime();
        long l1 = l / 1000 / 60 / 60 / 24 / 30;
        if(l1>3){
            return "3个月前";
        }else if(l1>0){
            return l1+"个月前";
        }else {
            long l2 = l / 1000 / 60 / 60 / 24;
            if(l2>0){
                return l2+"天前";
            }else{
                long l3 = l / 1000 / 60 / 60;
                if(l3>0){
                    return l3+"小时前";
                }else{
                    long l4 = l / 1000 / 60;
                    if(l4>0){
                        return l4+"分钟前";
                    }else{
                        return "刚刚";
                    }
                }
            }
        }
    }

    public static int getCurrentUid(){
        return (int) SPUtils.get("uid",0);
    }

}
