package com.suramire.miaowu.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseSwipeActivity;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.contract.PublishContract;
import com.suramire.miaowu.presenter.PublishPresenter;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.FileUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.PicassoUtil;
import com.suramire.miaowu.util.ToastUtil;
import com.suramire.miaowu.wiget.MyToolbar;

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

public class NewPublishActivity extends BaseSwipeActivity<PublishPresenter> implements PublishContract.View {
    @Bind(R.id.toolbar)
    MyToolbar mToolbar;
    @Bind(R.id.gridview_picture)
    GridView mGridviewPicture;
    @Bind(R.id.edit_title)
    EditText mEditTitle;
    @Bind(R.id.edit_content)
    EditText mEditContent;
    @Bind(R.id.hscroll)
    HorizontalScrollView mHscroll;
    @Bind(R.id.toolbar_text_center)
    TextView toolbarTextCenter;
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
        //根据帖子类型判断是否需要填写猫咪信息
        setSupportActionBar(mToolbar);
        needcat = getIntent().getBooleanExtra("needcat", false);
        mToolbar.setTitle("发表帖子");
        mToolbar.setLeftImage(R.drawable.ic_close_black_24dp);
        mToolbar.setLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("发布中，请稍候……");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (needcat) {
            menu.add(0, 0x11, 0, "填表").setIcon(R.drawable.ic_format_align_left_black_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        menu.add(0, 0x12, 1, "发表").setIcon(R.drawable.ic_send_black_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
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
                    L.e("NewPublishActivity:"+ FileUtil.getFileMD5(new File(s)));
                }
                mGridviewPicture.setAdapter(new CommonAdapter<String>(this, R.layout.item_picture, mPhotos) {
                    @Override
                    public void onUpdate(BaseAdapterHelper helper, String item, int position) {
                        PicassoUtil.show(new File(item),(ImageView) helper.getView(R.id.imageView15));
                    }
                });
            }
        }
        if (requestCode == ApiConfig.REQUESTCODE && resultCode == Activity.RESULT_OK) {
            isCatInfoOk = true;
            //为帖子设置猫咪信息
            catInfo = (Catinfo) data.getSerializableExtra("catinfo");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0x11) {
            Intent intent = new Intent(this, ExtendedInformationActivity.class);
            startActivityForResult(intent, ApiConfig.REQUESTCODE);
//            startActivity(ExtendedInformationActivity.class);
        }
        if (item.getItemId() == 0x12) {
            //这里执行发帖操作
            //发送帖子对象（文本）
            //将帖子里的图片传至服务器
            if (needcat) {
                if (isCatInfoOk) {
                    if (!isPublish) {
                        if(mPhotos!=null && mPhotos.size()>0){
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("提示")
                                    .setMessage("是否发布该帖子")
                                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mPresenter.publishCat();
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .setCancelable(true)
                                    .show();
                        }else{
                            ToastUtil.showShortToastCenter("帖子配图不能为空");
                        }

                    } else {

                        CommonUtil.snackBar(this, "请不要频繁发帖", "确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }
                } else {
                    CommonUtil.snackBar(this, "请先完善猫咪的信息", "去完善", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(NewPublishActivity.this, ExtendedInformationActivity.class);
                            startActivityForResult(intent, ApiConfig.REQUESTCODE);
                        }
                    });
                }

            } else {
                if (!isPublish) {
                    if(mPhotos!=null && mPhotos.size()>0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("提示")
                                .setMessage("是否发布该帖子")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mPresenter.publishNote(1, 0);
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .setCancelable(true)
                                .show();
                    }else{
                        ToastUtil.showShortToastCenter("帖子配图不能为空");
                    }

                } else {

                    CommonUtil.snackBar(this, "请不要频繁发帖", "确定", new View.OnClickListener() {
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
    public Note getNoteInfo() {
        Note note = new Note();
        note.setTitle(mEditTitle.getText().toString().trim());
        note.setContent(mEditContent.getText().toString().trim());
        note.setUid(CommonUtil.getCurrentUid());
        return note;
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
        Snackbar.make(findViewById(android.R.id.content), "帖子发布成功，请等待审核", Snackbar.LENGTH_INDEFINITE)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).show();
    }


    @OnClick({R.id.toolbar_image_left, R.id.toolbar_text_right,R.id.imageView17, R.id.imageView19, R.id.imageView18})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_image_left:
                finish();
                break;
            case R.id.toolbar_text_right:

                break;
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
}
