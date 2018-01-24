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

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.squareup.picasso.Picasso;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.contract.PublishContract;
import com.suramire.miaowu.presenter.PublishPresenter;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.CommonUtil;
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

public class NewPublishActivity extends BaseActivity<PublishPresenter> implements PublishContract.View {
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
    private boolean isCatInfoOk;
    private Catinfo catInfo;
    private boolean needcat;


    @Override
    public int bindLayout() {
        return R.layout.activity_newpublish;
    }

    @Override
    public void createPresenter() {
        mPresenter = new PublishPresenter();
    }

    @Override
    public void initView() {
        setSupportActionBar(mToolbar);
        //根据帖子类型判断是否需要填写猫咪信息
        needcat = getIntent().getBooleanExtra("needcat",false);
        setTitle("发表帖子");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("发布中，请稍候……");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(needcat){
            menu.add(0, 0x11, 0, "填表").setIcon(R.drawable.ic_format_align_left_black_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        menu.add(0, 0x12, 1, "发表").setIcon(R.drawable.ic_send_black_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
        if(requestCode == ApiConfig.REQUESTCODE && resultCode == Activity.RESULT_OK){
           isCatInfoOk = true;
            //为帖子设置猫咪信息
            catInfo = (Catinfo) data.getSerializableExtra("catinfo");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == 0x11){
            Intent intent = new Intent(this, ExtendedInformationActivity.class);
            startActivityForResult(intent, ApiConfig.REQUESTCODE);
//            startActivity(ExtendedInformationActivity.class);
        }
        if(item.getItemId() == 0x12){
            //这里执行发帖操作
            //发送帖子对象（文本）
            //将帖子里的图片传至服务器
            if(needcat){
                if(isCatInfoOk){
                    if(!isPublish){
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("提示")
                                .setMessage("是否发布该帖子")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mPresenter.publish();
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .setCancelable(true)
                                .show();
                    }else{

                        CommonUtil.snackBar(this,"请不要频繁发帖","确定",new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }
                }else{
                    CommonUtil.snackBar(this, "请先完善猫咪的信息", "去完善", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(NewPublishActivity.this, ExtendedInformationActivity.class);
                            startActivityForResult(intent, ApiConfig.REQUESTCODE);
                        }
                    });
                }

            }else{
                if(!isPublish){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("提示")
                            .setMessage("是否发布该帖子")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mPresenter.publish();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .setCancelable(true)
                            .show();
                }else{

                    CommonUtil.snackBar(this,"请不要频繁发帖","确定",new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            }


        }
        return super.onOptionsItemSelected(item);
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
    public Catinfo getCatinfo() {
        return catInfo;
    }

    @Override
    public List<String> getPicturePaths() {
        return mPhotos;
    }

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void cancelLoading() {
        mProgressDialog.dismiss();
    }

    @Override
    public void onSuccess(Object data) {
        isPublish = true;
        Snackbar.make(findViewById(android.R.id.content),"帖子发布成功，请等待审核",Snackbar.LENGTH_INDEFINITE)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).show();
    }
}
