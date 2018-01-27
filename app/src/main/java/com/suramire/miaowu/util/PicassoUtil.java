package com.suramire.miaowu.util;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.suramire.miaowu.base.App;

/**
 * Created by Suramire on 2018/1/25.
 */

public class PicassoUtil {

    public static void show(String path, ImageView target){
        Picasso.with(App.getInstance())
                .load(path)
                .into(target);
    }
}
