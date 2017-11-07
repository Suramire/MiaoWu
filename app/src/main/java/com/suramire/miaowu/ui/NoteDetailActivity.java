package com.suramire.miaowu.ui;


import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.suramire.miaowu.R;
import com.suramire.miaowu.adapter.MultiItemAdapter;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.contract.NoteDetailContract;
import com.suramire.miaowu.fragment.BottomCommentDialogFragment;
import com.suramire.miaowu.presenter.NoteDetailPresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

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
    private MultiItemAdapter mAdapter;
    private List<Object> mObjects;


    @Override
    public int bindLayout() {
        return R.layout.activity_notedetail;
    }

    @Override
    public void initView(View view) {
        setSupportActionBar(mToolbar);
        mNoteId = getIntent().getIntExtra("noteId", 0);
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
        mObjects = new ArrayList<>();
        mObjects.add(note);
        mAdapter = new MultiItemAdapter(this, mObjects);
        mListNotedetail.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mNoteDetailPresenter.getPicture();

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
        mObjects.add(pictures);
        Collections.reverse(mObjects);
        mAdapter.notifyDataSetChanged();
        mNoteDetailPresenter.getReply();
    }

    @Override
    public void onGetPictureFailure(String failureMessage) {
        L.e("获取帖子配图失败：" + failureMessage);
    }

    @Override
    public void onGetPictureError(String errorMessage) {
        L.e("获取帖子配图出错：" + errorMessage);

    }

    @Override
    public void onGetReplySuccess(Object object) {
        List<Reply> replies = (List<Reply>) object;
        for (Reply reply: replies
             ) {
            mObjects.add(reply);
        }
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onGetReplyFailure(String failureMessage) {
    }

    @Override
    public void onGetReplyError(String errorMessage) {
    }


    @OnClick({R.id.btn_comment, R.id.btn_like, R.id.btn_share,R.id.editText3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_comment:{
                // TODO: 2017/11/7 跳转到评论列表
            }
                break;
            case R.id.btn_like:
                break;
            case R.id.btn_share:
                break;
            case R.id.editText3:{
                if (!CommonUtil.isLogined()) {
                    Snackbar.make(findViewById(android.R.id.content), "您还未登录", Snackbar.LENGTH_INDEFINITE)
                            .setAction("登录", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(LoginActivity.class);
                                }
                            }).show();
                } else {
                    final FragmentTransaction mFragTransaction = getFragmentManager().beginTransaction();
                    final BottomCommentDialogFragment bottomCommentDialogFragment = BottomCommentDialogFragment.newInstance();
                    bottomCommentDialogFragment.setReplyListener(new BottomCommentDialogFragment.OnReplyListener() {
                        @Override
                        public void onSucess() {
                            Toast.makeText(mContext, "发表成功！", Toast.LENGTH_SHORT).show();
                            bottomCommentDialogFragment.dismiss();
                        }

                        @Override
                        public void onFailure(String failureMessage) {
                            Toast.makeText(mContext, "发表失败:" + failureMessage, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Toast.makeText(mContext, "发表过程出现错误:" + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                    Bundle bundle = new Bundle();
                    bundle.putInt("nid", getNoteId());
                    bottomCommentDialogFragment.setArguments(bundle);
                    bottomCommentDialogFragment.show(mFragTransaction, "111");
                }
            }
                break;
        }
    }
}
