package com.suramire.miaowu.ui.dialog;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseDialogFragment;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.contract.ReplyContract;
import com.suramire.miaowu.presenter.ReplyPresenter;
import com.suramire.miaowu.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Suramire on 2017/11/21.
 */

public class BottomReplyOptionsFragment extends BaseDialogFragment<ReplyPresenter> implements ReplyContract.View {
    @Bind(R.id.rp_delete)
    Button rpDelete;
    @Bind(R.id.rp_copy)
    Button rpCopy;
    private String mContent;//选中复制的内容
    private Reply mReply;
    private ProgressDialog mProgressDialog;
    private OnDeleteListener mOnDeleteListener;

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        mOnDeleteListener = onDeleteListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.rp_delete, R.id.rp_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rp_delete:{
                Toast.makeText(mContext, "响应删除操作", Toast.LENGTH_SHORT).show();
                mPresenter.deleteReply();
                dismiss();
            }break;
            case R.id.rp_copy:{
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("content", mContent);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(mContext, "复制成功！", Toast.LENGTH_SHORT).show();
                dismiss();
            }break;
        }
    }


    public interface OnDeleteListener {
        void onSuccess();

        void onError(String errorMessage);
    }


    public static BottomReplyOptionsFragment newInstance() {
        BottomReplyOptionsFragment fragment = new BottomReplyOptionsFragment();
        return fragment;
    }


    @Override
    public void createPresenter() {
        mPresenter = new ReplyPresenter();
    }

    @Override
    public void initView() {
        Bundle arguments = getArguments();
        mContent = arguments.getString("content");
        mReply = (Reply) arguments.getSerializable("reply");
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("请稍候……");
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
    public int bindLayout() {
        return R.layout.popup_replyoptions;
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
//        mOnDeleteListener.onSuccess();
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
        return mReply;
    }
}
