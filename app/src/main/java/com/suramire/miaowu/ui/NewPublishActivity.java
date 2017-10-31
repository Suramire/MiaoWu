package com.suramire.miaowu.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.squareup.picasso.Picasso;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.contract.PublishContract;
import com.suramire.miaowu.presenter.PublishPresenter;
import com.suramire.miaowu.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;


/**
 * Created by Suramire on 2017/10/26.
 */
// TODO: 2017/10/27 字数限制

public class NewPublishActivity extends BaseActivity implements PublishContract.View {
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
    private ProgressDialog mProgressDialog;
    private boolean isPublish;
    private ArrayList<String> mPhotos;
    private PublishPresenter mPublishPresenter;

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
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("发布中，请稍候……");
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
                mPhotos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                for (String s : mPhotos
                        ) {
                    Log.d("NewPublishActivity", FileUtil.getFileMD5(new File(s)));
                }
                mGridviewPicture.setAdapter(new CommonAdapter<String>(this, R.layout.item_picture, mPhotos) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == 0x12){
            //这里执行发帖操作
            // TODO: 2017/10/28 信息完整性判断
            //发送帖子对象（文本）
            //将帖子里的图片传至服务器
            if(!isPublish){
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("提示")
                        .setMessage("是否发布该帖子")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPublishPresenter = new PublishPresenter(NewPublishActivity.this);
                                mPublishPresenter.publish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .setCancelable(true)
                        .show();
            }else{
                Snackbar.make(findViewById(android.R.id.content),"请不要频繁发帖",Snackbar.LENGTH_INDEFINITE).setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startPublishing() {
        mProgressDialog.show();
    }

    @Override
    public void stopPublishing() {
        mProgressDialog.dismiss();
    }

    @Override
    public void onPublishSuccess() {
        isPublish = true;
        Snackbar.make(findViewById(android.R.id.content),"帖子发布成功，请等待审核",Snackbar.LENGTH_INDEFINITE)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).show();
    }

    @Override
    public void onPublishFailure(String failureMessage) {
        Toast.makeText(mContext, "发布失败：" + failureMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPublishError(String errorMessage) {
        Toast.makeText(mContext, "发布帖子出错：" + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getNoteTitle() {
        return mEditTitle.getText().toString().trim();
    }

    @Override
    public String getNoteContent() {
        return mEditContent.getText().toString().trim();
    }

    @Override
    public List<String> getPicturePaths() {
        return mPhotos;
    }
}
