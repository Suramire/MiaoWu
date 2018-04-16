package com.suramire.miaowu.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
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
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
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


/**
 * Created by Suramire on 2017/10/26.
 */

public class NewPublishActivity extends BaseActivity<PublishPresenter> implements PublishContract.View {
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
//    private boolean needcat;


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
//        needcat = getIntent().getBooleanExtra("needcat", false);
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
        menu.add(0, 0x12, 1, "发表").setIcon(R.drawable.ic_send_black_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS && requestCode == ApiConfig.REQUESTCODE_PHOTO) {
            if (data != null) {
                mPhotos = new ArrayList<>();
                mHscroll.setVisibility(View.VISIBLE);
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mGridviewPicture.setAdapter(new CommonAdapter<ImageItem>(this, R.layout.item_picture, images) {
                    @Override
                    public void onUpdate(BaseAdapterHelper helper, ImageItem item, int position) {
                        PicassoUtil.show(new File(item.path),(ImageView) helper.getView(R.id.imageView15));
                    }
                });
                for (int i = 0; i < images.size(); i++) {
                    mPhotos.add(images.get(i).path);
                }
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
        if (item.getItemId() == 0x12) {
            //这里执行发帖操作
            //发送帖子对象（文本）
            //将帖子里的图片传至服务器
//            if (needcat) {
//                if (isCatInfoOk) {
//                    if (!isPublish) {
//                        if(mPhotos!=null && mPhotos.size()>0){
//                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                            builder.setTitle("提示")
//                                    .setMessage("是否发布该帖子")
//                                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            mPresenter.publishCat();
//                                        }
//                                    })
//                                    .setNegativeButton("取消", null)
//                                    .setCancelable(true)
//                                    .show();
//                        }else{
//                            ToastUtil.showShortToastCenter("帖子配图不能为空");
//                        }
//
//                    } else {
//
//                        CommonUtil.snackBar(this, "请不要频繁发帖", "确定", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                            }
//                        });
//                    }
//                } else {
//                    CommonUtil.snackBar(this, "请先完善猫咪的信息", "去完善", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(NewPublishActivity.this, CatInfoActivity.class);
//                            startActivityForResult(intent, ApiConfig.REQUESTCODE);
//                        }
//                    });
//                }
//
//            } else {
//
//            }
            if (!isPublish) {
                if(mPhotos!=null && mPhotos.size()>0){
                    String title = mEditTitle.getText().toString().trim();
                    String content = mEditContent.getText().toString().trim();
                    L.e("content.length():" + content);
                    if(TextUtils.isEmpty(title)|| TextUtils.isEmpty(content)){
                        ToastUtil.showShortToastCenter("请输入帖子标题和内容");
                    }else if(content.length()>32767){
                        ToastUtil.showShortToastCenter("输入的内容长度超出限制");
                    }else{
                        CommonUtil.showDialog(mContext, "提示", "是否发布该帖子？",
                                "确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mPresenter.publishNote();
                                    }
                                },"取消",null);
                    }
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
        isPublish = true;//发帖标志位，防止重复发帖 true=已经发布过了
        CommonUtil.snackBar(mContext,
                "帖子发布成功，请等待审核","确定",new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }


    @OnClick({R.id.toolbar_image_left, R.id.toolbar_text_right,R.id.imageView17, R.id.imageView19})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_image_left:
                finish();
                break;
            case R.id.toolbar_text_right:

                break;
            case R.id.imageView19: {
//                if(needcat){
//                    Intent intent = new Intent(NewPublishActivity.this, CatInfoActivity.class);
//                    startActivityForResult(intent, ApiConfig.REQUESTCODE);
//                }else{
//                    ToastUtil.showShortToastCenter("无需填写猫咪信息");
//                }

            }
            break;

            case R.id.imageView17: {
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setSelectLimit(9);    //选中数量限制
                Intent intent = new Intent(mContext, ImageGridActivity.class);
                startActivityForResult(intent,ApiConfig.REQUESTCODE_PHOTO);
            }
            break;
        }
    }
}
