package com.suramire.miaowu.ui;

import android.view.View;
import android.widget.ImageView;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.util.PicassoUtil;
import com.suramire.miaowu.wiget.MyToolbar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2018/1/29.
 */

public class HDPictureActivity extends BaseActivity {
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.toolbar)
    MyToolbar toolbar;
    @Bind(R.id.toolbar_image_left)
    ImageView toolbarImageLeft;

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_hdpicture;
    }

    @Override
    public void createPresenter() {

    }

    @Override
    public void initView() {
        toolbar.setTitle("大图");
        toolbar.setStyle(MyToolbar.STYLE_LEFT_AND_TITLE);
        toolbarImageLeft.setImageResource(R.drawable.ic_arrow_back_black);
        String path = getIntent().getStringExtra("path");
        PicassoUtil.show(path,imageView,R.drawable.ic_loading,R.drawable.ic_loading_error);
    }

    @OnClick({R.id.toolbar_image_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_image_left:finish();break;
        }
    }
}
