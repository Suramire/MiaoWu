package com.suramire.miaowu.ui.dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.DialogFragment;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.suramire.miaowu.R;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.contract.ReplyContract;
import com.suramire.miaowu.presenter.ReplyPresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Suramire on 2017/11/2.
 */

public class BottomCommentDialogFragment extends DialogFragment implements ReplyContract.View {

    @Bind(R.id.edittext_comment)
    EditText edittextComment;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.ll_popup)
    LinearLayout llPopup;
    private ProgressDialog mProgressDialog;
    private int nid, replyuid;
    private OnReplyListener mReplyListener;
    private int floorId;
    private int unpass;
    private ReplyPresenter replyPresenter;

    public void setReplyListener(OnReplyListener replyListener) {
        mReplyListener = replyListener;
    }


    @OnClick(R.id.button2)
    public void onViewClicked() {
        if(unpass==1){
            replyPresenter.unPassNote();
        }else{
            replyPresenter.postReply();

        }
    }

    public interface OnReplyListener {

        void onSucess();


        void onError(String errorMessage);
    }

    public static BottomCommentDialogFragment newInstance() {
        return new BottomCommentDialogFragment();
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Activity mContext = getActivity();
        View view = inflater.inflate(R.layout.popup_comment, null);
        ButterKnife.bind(this, view);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("请稍候……");
        replyPresenter = new ReplyPresenter();
        replyPresenter.attachView(this);
        if(unpass==1){
            edittextComment.setHint("请输入驳回帖子的理由");
            button2.setText("驳回");
        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nid = getArguments().getInt("nid");
        //是否是驳回帖子操作的标志位
        unpass = getArguments().getInt("unpass");
        replyuid = getArguments().getInt("replyuid");
        floorId = getArguments().getInt("floorId");
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
    }




    @Override
    public void onAddSuccess() {
        ToastUtil.showLongToastCenter("发布评论成功");
        mReplyListener.onSucess();
    }

    @Override
    public Reply getReplyInfo() {
        Reply reply = new Reply();
        reply.setNid(nid);
        reply.setReplytime(CommonUtil.getTimeStamp());
        reply.setUid(CommonUtil.getCurrentUid());
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
