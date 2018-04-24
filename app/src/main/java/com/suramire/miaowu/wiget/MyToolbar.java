package com.suramire.miaowu.wiget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.suramire.miaowu.R;


/**
 * Created by Suramire on 2018/1/21.
 * 自定义Toolbar
 * 实现标题居中以及响应左右图标文字点击事件
 */

public class MyToolbar extends Toolbar {

    public static final int STYLE_TITLE_ONLY = 0;
    public static final int STYLE_LEFT_AND_TITLE = 1;
    public static final int STYLE_RIGHT_AND_TITLE = 2;


    private ImageView toolbarImageLeft;
    private TextView toolbarTextCenter;
    private TextView toolbarTextRight;

    public MyToolbar(Context context) {
        super(context);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        toolbarImageLeft = (ImageView) findViewById(R.id.toolbar_image_left);
        toolbarTextCenter = (TextView) findViewById(R.id.toolbar_text_center);
        toolbarTextRight = (TextView) findViewById(R.id.toolbar_text_right);
    }


    /**
     * 设置标题栏标题文字
     * @param title 标题文字
     */
    @Override
    public void setTitle(CharSequence title) {
        if(!TextUtils.isEmpty(title)){
            toolbarTextCenter.setText(title);
        }else{
            toolbarTextCenter.setText("");
        }
    }

    /**
     * 设置右边文本
     * @param textRight 文本内容
     */
    public void setRightText(CharSequence textRight){
        if(!TextUtils.isEmpty(textRight)){
            toolbarTextRight.setText(textRight);
        }else{
            toolbarTextRight.setText("");
        }
    }

    /**
     * 设置左边图标
     * @param resId 图标资源编号
     */
    public void setLeftImage(int resId){
        toolbarImageLeft.setImageResource(resId);
    }


    public void setStyle(int style){
            switch (style){
                case STYLE_TITLE_ONLY:{
                    toolbarImageLeft.setVisibility(GONE);
                    toolbarTextRight.setVisibility(GONE);
                }break;
                case STYLE_LEFT_AND_TITLE:{
                    toolbarTextRight.setVisibility(GONE);
                }break;
                case STYLE_RIGHT_AND_TITLE:{
                    toolbarImageLeft.setVisibility(GONE);
                }break;
                default:break;
        }

    }


}
