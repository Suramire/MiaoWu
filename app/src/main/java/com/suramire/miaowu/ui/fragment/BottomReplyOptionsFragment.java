package com.suramire.miaowu.ui.fragment;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.widget.Toast;

import com.suramire.miaowu.R;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.contract.RepleyContract;
import com.suramire.miaowu.presenter.ReplyPresenter;

/**
 * Created by Suramire on 2017/11/21.
 */

public class BottomReplyOptionsFragment extends DialogFragment implements View.OnClickListener,RepleyContract.View {
    private View view;
    private Context mContext;
    private String mContent;
    private Reply mReply;
    private ProgressDialog mProgressDialog;
    private OnDeleteListener mOnDeleteListener;

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        mOnDeleteListener = onDeleteListener;
    }



    public interface OnDeleteListener{
        void onSuccess();

        void onError(String errorMessage);
    }



    public static BottomReplyOptionsFragment newInstance() {
        BottomReplyOptionsFragment fragment = new BottomReplyOptionsFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        Bundle arguments = getArguments();
        mContent = arguments.getString("content");
        mReply = (Reply) arguments.getSerializable("reply");
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("请稍候……");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(view==null){
            view = inflater.inflate(R.layout.popup_replyoptions, container, false);
        }
        view.findViewById(R.id.rp_copy).setOnClickListener(this);
        view.findViewById(R.id.rp_delete).setOnClickListener(this);
        return  view;
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rp_copy:{
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("content", mContent);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(mContext, "复制成功！", Toast.LENGTH_SHORT).show();
                dismiss();
            }break;
            case R.id.rp_delete:{
                Toast.makeText(mContext, "响应删除操作", Toast.LENGTH_SHORT).show();
                new ReplyPresenter(this).deleteReply();
                dismiss();
            }break;

        }
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
        mOnDeleteListener.onSuccess();
    }

    @Override
    public void onFailure(String failureMessage) {

    }

    @Override
    public void onError(String errorMessage) {
        mOnDeleteListener.onError(errorMessage);
    }

    @Override
    public Reply getReplyInfo() {
        return mReply;
    }
}
