package com.suramire.miaowu.ui;


import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseSwipeActivity;
import com.suramire.miaowu.bean.Multi0;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.ReplyDetailContract;
import com.suramire.miaowu.presenter.ReplyDetailPresenter;
import com.suramire.miaowu.ui.dialog.BottomCommentDialogFragment;
import com.suramire.miaowu.ui.dialog.BottomReplyOptionsFragment;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.wiget.MyToolbar;

import java.util.List;

import butterknife.Bind;

import static com.suramire.miaowu.util.ApiConfig.BASUSERPICEURL;

/**
 * Created by Suramire on 2017/11/17.
 */

public class ReplyDetailActivity extends BaseSwipeActivity<ReplyDetailPresenter> implements ReplyDetailContract.View {
    @Bind(R.id.toolbar)
    MyToolbar mToolbar;
    @Bind(R.id.reply_user_icon)
    RoundedImageView mReplyUserIcon;
    @Bind(R.id.reply_user_nickname)
    TextView mReplyUserNickname;
    @Bind(R.id.reply_date)
    TextView mReplyDate;
    @Bind(R.id.list_reply_child)
    RecyclerView mListReplyChild;
    @Bind(R.id.reply_content)
    TextView mReplyContent;
    private ProgressDialog mProgressDialog;
    private Integer mFloorid;
    private Integer nId;


    @Override
    public int bindLayout() {
        return R.layout.acticity_repleydetail;
    }

    @Override
    public void createPresenter() {
        mPresenter = new ReplyDetailPresenter();
    }


    @Override
    public void initView() {
        mToolbar.setTitle("评论详情");
        mToolbar.setStyle(MyToolbar.STYLE_LEFT_AND_TITLE);
        mToolbar.setLeftImage(R.drawable.ic_arrow_back_black);
        mToolbar.setLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("请稍候……");
        Multi0 multi = (Multi0) getIntent().getSerializableExtra("multi");
        Reply reply = multi.getReply();
        mFloorid = reply.getFloorid();
        nId = reply.getNid();
        User user = multi.getUser();
        mReplyUserNickname.setText(user.getNickname());
        String icon = user.getIcon();
        if (icon != null) {
            Picasso.with(mContext)
                    .load(BASUSERPICEURL + icon)
                    .into(mReplyUserIcon);
        }
        mReplyDate.setText(CommonUtil.timeStampToDateString(reply.getReplytime()));
        mReplyContent.setText(reply.getReplycontent());
//        mPresenter.getReplyList();
        mListReplyChild.setLayoutManager(new LinearLayoutManager(mContext));
        mPresenter.listReplyDetail();
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
    public void onSuccess(Object object) {
        final List<Multi0> multiList = (List<Multi0>) object;
        multiList.remove(0);//移除回复主题


        if(object!=null){

            mListReplyChild.setAdapter(new CommonRecyclerAdapter<Multi0>(mContext,R.layout.item_reply_child,multiList) {

                @Override
                public void onUpdate(BaseAdapterHelper helper, final Multi0 multi, int position) {
                    final Reply item = multi.getReply();
                    User user = multi.getUser();
                    User rUser = multi.getUser2();
                    if(rUser!=null){
                        helper.setText(R.id.reply_child, user.getNickname()+"回复@"+rUser.getNickname()+": "+item.getReplycontent());
                    }else{
                        helper.setText(R.id.reply_child, user.getNickname()+" : "+item.getReplycontent());
                    }

                    int currentUid = CommonUtil.getCurrentUid();
                    if(currentUid!=0){
                        if(item.getUid()==currentUid){
                            //用户可删除自己的评论
                            helper.setOnClickListener(R.id.ll_replydetail_content, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final FragmentTransaction mFragTransaction = getFragmentManager().beginTransaction();
                                    BottomReplyOptionsFragment bottomReplyOptionsFragment = BottomReplyOptionsFragment.newInstance();
                                    Bundle bundle1 = new Bundle();
                                    bundle1.putString("content",item.getReplycontent());
                                    bundle1.putSerializable("reply",item);
                                    bottomReplyOptionsFragment.setArguments(bundle1);
                                    bottomReplyOptionsFragment.show(mFragTransaction, "000");
                                    bottomReplyOptionsFragment.setOnDeleteListener(new BottomReplyOptionsFragment.OnDeleteListener() {
                                        @Override
                                        public void onSuccess() {
                                            Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                                            remove(multi);
                                        }

                                        @Override
                                        public void onError(String errorMessage) {
                                            Toast.makeText(mContext, "删除时出现错误：" + errorMessage, Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }
                            });
                        }else{
                            helper.setOnClickListener(R.id.ll_replydetail_content, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO: 2017/11/21 登录判断 先登录才能回复
                                    final FragmentTransaction mFragTransaction = getFragmentManager().beginTransaction();
                                    final BottomCommentDialogFragment bottomCommentDialogFragment = BottomCommentDialogFragment.newInstance();
                                    bottomCommentDialogFragment.setReplyListener(new BottomCommentDialogFragment.OnReplyListener() {
                                        @Override
                                        public void onSucess() {
                                            Toast.makeText(mContext, "发表成功！", Toast.LENGTH_SHORT).show();
                                            // TODO: 2017/11/21 自己不能回复自己
                                            mPresenter.listReplyDetail();//刷新评论

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
                                    bundle.putInt("nid", item.getNid());
                                    bundle.putInt("replyuid",item.getUid());
                                    bundle.putInt("floorId",getFloorId());
                                    bottomCommentDialogFragment.setArguments(bundle);
                                    bottomCommentDialogFragment.show(mFragTransaction, "111");
                                }
                            });
                        }
                    }

                }

            });
        }else{
            Toast.makeText(mContext, "暂无回复信息", Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public int getFloorId() {
        return mFloorid;
    }
}
