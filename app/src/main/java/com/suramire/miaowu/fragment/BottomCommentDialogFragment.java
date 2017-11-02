package com.suramire.miaowu.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.suramire.miaowu.R;
import com.suramire.miaowu.contract.CommonContract;

/**
 * Created by Suramire on 2017/11/2.
 */

public class BottomCommentDialogFragment extends DialogFragment implements CommonContract.View {

    private View view;
    private Context mContext;
    private ProgressDialog mProgressDialog;



    public static BottomCommentDialogFragment newInstance() {
        Bundle args = new Bundle();
        BottomCommentDialogFragment fragment = new BottomCommentDialogFragment();
        fragment.setArguments(args);
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
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                showLoading();
            }
        });
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
        Toast.makeText(mContext, "发表成功！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String failureMessage) {
        Toast.makeText(mContext, "发表失败！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(mContext, "发表过程出现错误！", Toast.LENGTH_SHORT).show();
    }
}
