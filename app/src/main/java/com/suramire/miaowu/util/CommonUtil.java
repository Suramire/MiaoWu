package com.suramire.miaowu.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.suramire.miaowu.base.App;

import java.io.IOException;

/**
 * Created by Suramire on 2017/10/16.
 * 包含一些常用方法
 */

public class CommonUtil {

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
            bitmap = BitmapFactory.decodeStream(App.getApp().getAssets().open(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
