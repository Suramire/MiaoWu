package com.suramire.miaowu.ui.fragment;

import android.app.DialogFragment;
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
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.contract.RepleyContract;
import com.suramire.miaowu.presenter.ReplyPresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.SPUtils;

/**
 * Created by Suramire on 2017/11/2.
 */

public class BottomCommentDialogFragment extends DialogFragment implements RepleyContract.View {

    private View view;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private EditText mEditComment;
    private  int nid,replyuid;
    private ReplyPresenter mReplyPresenter;
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
        mReplyPresenter = new ReplyPresenter(this);
        nid = getArguments().getInt("nid");
        replyuid = getArguments().getInt("replyuid");
        floorId = getArguments().getInt("floorId");
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
                mReplyPresenter.postReply();
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
        mReplyListener.onSucess();
    }

    @Override
    public void onFailure(String failureMessage) {
        mReplyListener.onFailure(failureMessage);
    }

    @Override
    public void onError(String errorMessage) {
        mReplyListener.onError(errorMessage);
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
