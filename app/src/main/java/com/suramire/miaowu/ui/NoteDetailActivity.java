package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.contract.NoteDetailContract;
import com.suramire.miaowu.presenter.NoteDetailPresenter;
import com.suramire.miaowu.util.GlideImageLoader;
import com.suramire.miaowu.util.L;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Suramire on 2017/10/17.
 */

public class NoteDetailActivity extends BaseActivity implements NoteDetailContract.View {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.list_notedetail)
    ListView mListNotedetail;
    private ProgressDialog mProgressDialog;
    private NoteDetailPresenter mNoteDetailPresenter;
    private int mNoteId;


    @Override
    public int bindLayout() {
        return R.layout.activity_notedetail;
    }

    @Override
    public void initView(View view) {
        setSupportActionBar(mToolbar);
        mNoteId = getIntent().getIntExtra("noteId", 0);
        Toast.makeText(mContext, "noteId:" + mNoteId, Toast.LENGTH_SHORT).show();
        //查询帖子信息
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在读取帖子信息，请稍候……");
        mNoteDetailPresenter = new NoteDetailPresenter(this);
        mNoteDetailPresenter.getData();


    }


    @Override
    protected String getTitleString() {
        return "帖子详情";
    }

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void cancelLoading() {
        mProgressDialog.cancel();
    }

    @Override
    public int getNoteId() {
        return mNoteId;
    }

    @Override
    public void onGetSuccess(Note note) {
        mNoteDetailPresenter.getPicture();
        List<Note> notes = new ArrayList<>();
        notes.add(note);
        mListNotedetail.setAdapter(new CommonAdapter<Note>(mContext, R.layout.item_notedetail, notes) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, Note item, int position) {
                helper.setText(R.id.notedetail_content, item.getContent())
                        .setText(R.id.notedetail_title, item.getTitle());
            }
        });

    }

    @Override
    public void onGetFailure(String failureMessage) {
        Snackbar.make(findViewById(android.R.id.content), "读取帖子失败：" + failureMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNoteDetailPresenter.getData();
                    }
                }).show();
    }

    @Override
    public void onGetError(String errorMessage) {
        Snackbar.make(findViewById(android.R.id.content), "读取帖子出错：" + errorMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("返回", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).show();
    }

    @Override
    public void onGetPictureSuccess(List<String> pictures) {
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_notedetail, null, false);
        Banner mBanner = headerView.findViewById(R.id.banner);
        if (mListNotedetail.getHeaderViewsCount() == 0) {
            mListNotedetail.addHeaderView(mBanner);
        }
        mBanner.setImageLoader(new GlideImageLoader())
                .setImages(pictures)
                .isAutoPlay(false)
                .setBannerStyle(BannerConfig.NUM_INDICATOR)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start();


    }

    @Override
    public void onGetPictureFailure(String failureMessage) {
        L.e("获取帖子配图失败：" + failureMessage);
    }

    @Override
    public void onGetPictureError(String errorMessage) {
        L.e("获取帖子配图出错：" + errorMessage);

    }

}
