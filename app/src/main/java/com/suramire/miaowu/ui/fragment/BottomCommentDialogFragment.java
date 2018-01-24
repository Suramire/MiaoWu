package com.suramire.miaowu.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseDialogFragment;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.contract.ReplyContract;
import com.suramire.miaowu.presenter.ReplyPresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.SPUtils;
import com.suramire.miaowu.util.ToastUtil;

/**
 * Created by Suramire on 2017/11/2.
 */

public class BottomCommentDialogFragment extends BaseDialogFragment<ReplyPresenter> implements ReplyContract.View {

    private View view;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private EditText mEditComment;
    private  int nid,replyuid;
    private OnReplyListener mReplyListener;
    private int floorId;

    public OnReplyListener getReplyListener() {
        return mReplyListener;
    }

    public void setReplyListener(OnReplyListener replyListener) {
        mReplyListener = replyListener;
    }

    public interface OnReplyListener{

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
        replyuid = getArguments().getInt("replyuid");
        floorId = getArguments().getInt("floorId");
    }



    @Override
    public void createPresenter() {
        mPresenter = new ReplyPresenter();
    }

    @Override
    public void initView() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(view==null){
            view = inflater.inflate(R.layout.popup_comment, container, false);
        }
        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.postReply();
            }
        });
        mEditComment = view.findViewById(R.id.edittext_comment);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("请稍候……");
        return view;
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
    }

    @Override
    public Reply getReplyInfo() {
        Reply reply = new Reply();
        reply.setNid(nid);
        reply.setReplytime(CommonUtil.getTimeStamp());
        reply.setUid((int)SPUtils.get("uid",0));
        reply.setReplyuid(replyuid);
        reply.setFloorid(floorId);
        reply.setReplycontent(mEditComment.getText().toString().trim());
        return reply;
    }
}
