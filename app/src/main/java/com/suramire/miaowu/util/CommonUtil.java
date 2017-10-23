package com.suramire.miaowu.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.suramire.miaowu.base.App;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by Suramire on 2017/10/16.
 * 包含一些常用方法
 */

public class CommonUtil {
    private static Context mContext = App.getContext();

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
        if((int)SPUtils.get("uid",0)!=0 && (int)SPUtils.get("autologin",0)==1){
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为手机号码格式
     * @param mobiles
     * @return
     */
    public static boolean isMobileNumber(String mobiles) {
        return Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[^1^4,\\D]))\\d{8}").matcher(mobiles).matches();
    }

}
