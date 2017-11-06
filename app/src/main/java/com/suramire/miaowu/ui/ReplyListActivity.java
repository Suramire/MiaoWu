package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.contract.NoteDetailContract;
import com.suramire.miaowu.presenter.NoteDetailPresenter;
import com.suramire.miaowu.util.CommonUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Suramire on 2017/11/6.
 */

public class ReplyListActivity extends BaseActivity implements NoteDetailContract.View {
    @Bind(R.id.list_reply)
    ListView mListReply;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.textView2)
    TextView mTextView2;
    private ProgressDialog mProgressDialog;

    @Override
    protected String getTitleString() {
        return "评论详情";
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_relpy_list;
    }

    @Override
    public void initView(View view) {
        setSupportActionBar(mToolbar);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在读取帖子信息，请稍候……");
        NoteDetailPresenter noteDetailPresenter = new NoteDetailPresenter(this);
        noteDetailPresenter.getReply();
        mListReply.setEmptyView(mTextView2);
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
    public int getNoteId() {
        return getIntent().getExtras().getInt("nid", 0);
    }

    @Override
    public void onGetSuccess(Note note) {

    }

    @Override
    public void onGetFailure(String failureMessage) {

    }

    @Override
    public void onGetError(String errorMessage) {

    }

    @Override
    public void onGetPictureSuccess(List<String> pictures) {

    }

    @Override
    public void onGetPictureFailure(String failureMessage) {

    }

    @Override
    public void onGetPictureError(String errorMessage) {

    }

    @Override
    public void onGetReplySuccess(Object object) {
        List<Reply> replies = (List<Reply>) object;
        if (replies != null) {
            CommonAdapter<Reply> adapter = new CommonAdapter<Reply>(mContext, R.layout.item_reply, replies) {
                @Override
                public void onUpdate(BaseAdapterHelper helper, Reply item, int position) {
                    helper.setText(R.id.reply_date, CommonUtil.timeStampToDateString(item.getReplytime()))
                            .setText(R.id.reply_nickname, item.getUid() + "")
                            .setText(R.id.reply_content, item.getReplycontent());
                }
            };
            mListReply.setAdapter(adapter);
        }
    }

    @Override
    public void onGetReplyFailure(String failureMessage) {
//        Toast.makeText(mContext, "获取评论失败，" + failureMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetReplyError(String errorMessage) {
        Toast.makeText(mContext, "获取评论出错，" + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
