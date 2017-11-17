package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseSwipeActivity;
import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.ReplyDetailContract;
import com.suramire.miaowu.presenter.ReplyDetailPresenter;
import com.suramire.miaowu.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.suramire.miaowu.util.Constant.BASUSERPICEURL;

/**
 * Created by Suramire on 2017/11/17.
 */

public class ReplyDetailActivity extends BaseSwipeActivity implements ReplyDetailContract.View {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.reply_user_icon)
    RoundedImageView mReplyUserIcon;
    @Bind(R.id.reply_user_nickname)
    TextView mReplyUserNickname;
    @Bind(R.id.reply_date)
    TextView mReplyDate;
    @Bind(R.id.list_reply_child)
    ListView mListReplyChild;
    @Bind(R.id.reply_content)
    TextView mReplyContent;
    private ProgressDialog mProgressDialog;
    private Integer mFloorid;

    @Override
    protected String getTitleString() {
        return "评论详情";
    }

    @Override
    public int bindLayout() {
        return R.layout.acticity_repleydetail;
    }

    @Override
    public void initView(View view) {
        setSupportActionBar(mToolbar);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("请稍候……");
        Multi multi = (Multi) getIntent().getSerializableExtra("multi");
        Reply reply = multi.getmReply();
        mFloorid = reply.getFloorid();
        User user = multi.getmUser();
        mReplyUserNickname.setText(user.getNickname());
        String icon = user.getIcon();
        if (icon != null) {
            Picasso.with(mContext)
                    .load(BASUSERPICEURL + icon)
                    .into(mReplyUserIcon);
        }
        mReplyDate.setText(CommonUtil.timeStampToDateString(reply.getReplytime()));
        mReplyContent.setText(reply.getReplycontent());
        ReplyDetailPresenter replyDetailPresenter = new ReplyDetailPresenter(this);
        replyDetailPresenter.getReplyList();
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
    public void onGetReplyListSuccess(Object object) {
        List<Reply> replyList = (List<Reply>) object;
        List<Reply> mReplyList = new ArrayList<>();
        for (Reply reply :
                replyList) {
            if(reply.getReplyuid()!=0){
                mReplyList.add(reply);
            }
        }

        if(object!=null){
            mListReplyChild.setAdapter(new CommonAdapter<Reply>(mContext,R.layout.item_reply_child,mReplyList) {
                @Override
                public void onUpdate(BaseAdapterHelper helper, Reply item, int position) {
                    helper.setText(R.id.reply_child,item.getReplycontent());
                }
            });
        }else{
            Toast.makeText(mContext, "暂无回复信息", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetReplyListFaiure(String failureMessage) {
        Toast.makeText(mContext, "获取回复列表失败：" + failureMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetReplyListError(String errorMessage) {
        Toast.makeText(mContext, "获取回复列表出错：" + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getFloorId() {
        return mFloorid;
    }
}
