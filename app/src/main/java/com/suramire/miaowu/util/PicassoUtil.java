package com.suramire.miaowu.util;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.suramire.miaowu.base.App;

/**
 * Created by Suramire on 2018/1/25.
 */

public class PicassoUtil {

    public static RequestCreator load(String path) {
        return Picasso.with(App.getInstance())
        .load(path);
    }

}
