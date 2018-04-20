package com.suramire.miaowu.util;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;
import com.squareup.picasso.Picasso;
import com.suramire.miaowu.R;

import java.io.File;

public class MyImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Picasso.with(activity)
                .load(Uri.fromFile(new File(path)))
                .resize(200,200)
                .centerCrop()
                .placeholder(R.mipmap.ic_loading_small)
                .error(R.mipmap.ic_loading_error_small)
                .into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {

    }

    @Override
    public void clearMemoryCache() {

    }

}