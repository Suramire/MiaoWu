package com.suramire.miaowu.ui.dialog;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseDialogFragment;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.contract.ReplyContract;
import com.suramire.miaowu.presenter.ReplyPresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.SPUtils;
import com.suramire.miaowu.util.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2017/11/2.
 */

public class BottomCommentDialogFragment extends BaseDialogFragment<ReplyPresenter> implements ReplyContract.View {

    @Bind(R.id.edittext_comment)
    EditText edittextComment;
    @Bind(R.id.imageView9)
    ImageView imageView9;
    @Bind(R.id.imageView13)
    ImageView imageView13;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.ll_popup)
    LinearLayout llPopup;
    private View view;
    private ProgressDialog mProgressDialog;
    private int nid, replyuid;
    private OnReplyListener mReplyListener;
    private int floorId;
    private int unpass;

    public OnReplyListener getReplyListener() {
        return mReplyListener;
    }

    public void setReplyListener(OnReplyListener replyListener) {
        mReplyListener = replyListener;
    }


    @OnClick(R.id.button2)
    public void onViewClicked() {
        if(unpass==1){
            // TODO: 2018/1/31 这里生成驳回帖子的通知消息
            mPresenter.unPassNote();
        }else{
            mPresenter.postReply();

        }
    }

    public interface OnReplyListener {

        void onSucess();

        void onFailure(String failureMessage);

        void onError(String errorMessage);
    }

    public static BottomCommentDialogFragment newInstance() {
        BottomCommentDialogFragment fragment = new BottomCommentDialogFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        nid = getArguments().getInt("nid");
        //是否是驳回帖子操作的标志位
        unpass = getArguments().getInt("unpass");
        replyuid = getArguments().getInt("replyuid");
        floorId = getArguments().getInt("floorId");
    }


    @Override
    public void createPresenter() {
        mPresenter = new ReplyPresenter();
    }

    @Override
    public void initView() {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("请稍候……");
        if(unpass==1){
            edittextComment.setHint("请输入驳回帖子的理由");
            button2.setText("驳回");
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.popup_comment;
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
//        mReplyListener.onSucess();
    }


    @Override
    public void onDeleteSuccess() {
        ToastUtil.showShortToastCenter("删除评论成功");
    }

    @Override
    public void onAddSuccess() {
        ToastUtil.showShortToastCenter("发布评论成功");
        mReplyListener.onSucess();
    }

    @Override
    public Reply getReplyInfo() {
        Reply reply = new Reply();
        reply.setNid(nid);
        reply.setReplytime(CommonUtil.getTimeStamp());
        reply.setUid((int) SPUtils.get("uid", 0));
        reply.setReplyuid(replyuid);
        reply.setFloorid(floorId);
        reply.setReplycontent(edittextComment.getText().toString().trim());
        return reply;
    }

    @Override
    public Note getUnPassInfo() {
        Note note = new Note();
        note.setId(nid);
        note.setContent(edittextComment.getText().toString().trim());
        return note;
    }

    @Override
    public void onUnpassSuccess() {
        mReplyListener.onSucess();
    }
}
