package com.suramire.miaowu.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.squareup.picasso.Picasso;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

import static com.suramire.miaowu.util.Constant.BASEURL;

/**
 * Created by Suramire on 2017/10/26.
 */
// TODO: 2017/10/27 字数限制

public class NewPublishActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.gridview_picture)
    GridView mGridviewPicture;
    @Bind(R.id.edit_title)
    EditText mEditTitle;
    @Bind(R.id.edit_content)
    EditText mEditContent;
    @Bind(R.id.hscroll)
    HorizontalScrollView mHscroll;

    @Override
    protected String getTitleString() {
        return "发表帖子";
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_newpublish;
    }

    @Override
    public void initView(View view) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        ArrayList<String> list = new ArrayList<>();
        list.add(BASEURL + "upload/cat9.jpg");
        list.add(BASEURL + "upload/cat9.jpg");
        list.add(BASEURL + "upload/cat9.jpg");
        list.add(BASEURL + "upload/cat9.jpg");
        list.add(BASEURL + "upload/cat9.jpg");
        list.add(BASEURL + "upload/cat9.jpg");
        list.add(BASEURL + "upload/cat9.jpg");
        list.add(BASEURL + "upload/cat9.jpg");
        list.add(BASEURL + "upload/cat9.jpg");
        mGridviewPicture.setAdapter(new CommonAdapter<String>(this, R.layout.item_picture, list) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, String item, int position) {
                Picasso.with(mContext)
                        .load(item)
                        .placeholder(R.drawable.newphoto)
                        .resize(50, 50)
                        .centerCrop()
                        .into((ImageView) helper.getView(R.id.imageView15));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0x12, 0, "发表").setIcon(R.drawable.ic_send_black_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }


    @OnClick({R.id.imageView17, R.id.imageView19, R.id.imageView18})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView19: {
                mEditContent.setText(mEditTitle.getText());
            }
            break;
            case R.id.imageView18: {
                Log.d("NewPublishActivity", mEditContent.getText().toString().trim());
            }
            break;
            case R.id.imageView17: {
                PhotoPicker.builder()
                        .setPhotoCount(9)
                        .setGridColumnCount(4)
                        .start(this);
            }
            break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {
            if (data != null) {
                mHscroll.setVisibility(View.VISIBLE);
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                mGridviewPicture.setAdapter(new CommonAdapter<String>(this, R.layout.item_picture, photos) {
                    @Override
                    public void onUpdate(BaseAdapterHelper helper, String item, int position) {
                        Picasso.with(mContext)
                                .load(new File(item))
                                .placeholder(R.drawable.ic_loading)
                                .into((ImageView) helper.getView(R.id.imageView15));
                    }
                });
            }
        }
    }
}
