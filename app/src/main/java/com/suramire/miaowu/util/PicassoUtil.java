package com.suramire.miaowu.util;

import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.App;

import java.io.File;

/**
 * Picasso工具类
 * 简化图片加载操作
 */

public class PicassoUtil {


    /**
     * 加载图片默认占位图片
     * @param path 图片路径
     * @param target 目标imageview
     */
    public static void show(String path, ImageView target){
        Picasso.with(App.getInstance())
                .load(path)
                .placeholder(R.mipmap.ic_loading)
                .error(R.mipmap.ic_loading_error)
                .resize(500, 360)
                .centerCrop()
                .into(target);
    }

    /**
     * 加载头像图片 防止圆形头像变形
     * @param path
     * @param target
     */
    public static void showIcon(String path, ImageView target){
        Picasso.with(App.getInstance())
                .load(path)
                .placeholder(R.mipmap.ic_cat_icon)
                .error(R.mipmap.ic_loading_error)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .resize(100,100)
                .centerCrop()
                .into(target);

    }

    /**
     * 加载头像图片 不缓存图片 用于头像修改后显示即时显示新头像
     * @param path
     * @param target
     */
    public static void showIconNoCache(String path, ImageView target){
        Picasso.with(App.getInstance())
                .load(path)
                .placeholder(R.mipmap.ic_cat_icon)
                .error(R.mipmap.ic_loading_error)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .resize(100,100)
                .centerCrop()
                .into(target);

    }



    /**
     * 加载图片默认占位图片
     * @param file 图片文件
     * @param target 目标imageview
     */
    public static void show(File file, ImageView target){
        Picasso.with(App.getInstance())
                .load(file)
                .placeholder(R.mipmap.ic_loading)
                .error(R.mipmap.ic_loading_error)
                .resize(500, 360)
                .centerCrop()
                .into(target);
    }

    /**
     * 加载图片 自定义占位图片与错误提示图片
     * @param path 图片路径
     * @param target 目标imageview
     * @param placeholder 自定义占位图片
     * @param errorholder 自定义错误提示图片
     */
    public static void show(String path, ImageView target,int placeholder,int errorholder){
        Picasso.with(App.getInstance())
                .load(path)
                .placeholder(placeholder)
                .error(errorholder)
                .resize(500, 360)
                .centerCrop()
                .into(target);
    }


    /**
     * 加载图片 自定义占位图片与错误提示图片
     * @param file 图片文件
     * @param target 目标imageview
     * @param placeholder 自定义占位图片
     * @param errorholder 自定义错误提示图片
     */
    public static void show(File file, ImageView target,int placeholder,int errorholder){
        Picasso.with(App.getInstance())
                .load(file)
                .placeholder(placeholder)
                .error(errorholder)
                .resize(500, 360)
                .centerCrop()
                .into(target);
    }
}
