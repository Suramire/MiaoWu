package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseSwipeActivity;
import com.suramire.miaowu.bean.Apply;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.NoteDetailContract;
import com.suramire.miaowu.presenter.NoteDetailPresenter;
import com.suramire.miaowu.util.A;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.PicassoUtil;
import com.suramire.miaowu.wiget.MyToolbar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Suramire on 2018/4/9.
 */

public class ApplyDetailActivity extends BaseSwipeActivity<NoteDetailPresenter> implements NoteDetailContract.View {
    @Bind(R.id.toolbar_image_left)
    ImageView toolbarImageLeft;
    @Bind(R.id.toolbar_text_center)
    TextView toolbarTextCenter;
    @Bind(R.id.toolbar)
    MyToolbar toolbar;
    @Bind(R.id.img_nt_profile)
    RoundedImageView imgNtProfile;
    @Bind(R.id.tv_nt_nickname)
    TextView tvNtNickname;
    @Bind(R.id.ll_profile)
    LinearLayout llProfile;
    @Bind(R.id.notetitle)
    TextView notetitle;
    @Bind(R.id.notecontent)
    TextView notecontent;
    @Bind(R.id.note)
    LinearLayout llnote;
    @Bind(R.id.btn_agree)
    Button btnAgree;
    @Bind(R.id.btn_disagree)
    Button btnDisagree;
    @Bind(R.id.ll_button)
    LinearLayout llButton;
    @Bind(R.id.ll_result)
    LinearLayout llResult;
    @Bind(R.id.tv_result)
    TextView tvResult;
    private int choiceFlag;
    private ProgressDialog mProgressDialog;
    private int aid;
    private Integer nid;
    private Integer uid;
    private Integer verify;
    private int showResult;

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void cancelLoading() {
        mProgressDialog.dismiss();
    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_applydetail;
    }

    @Override
    public void createPresenter() {
        mPresenter = new NoteDetailPresenter();
    }

    @Override
    public void initView() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("请稍候……");
        setSupportActionBar(toolbar);
        toolbar.setTitle("申请详情");
        toolbar.setLeftImage(R.drawable.ic_arrow_back_black);
        showResult = getIntent().getIntExtra("req", 0);
        aid = getIntent().getIntExtra("aid", 0);
        //根据申请单编号查询出对应的 申请者编号 帖子编号以及申请时间
        mPresenter.getApplyInfo();


    }

    @Override
    public int getUserId() {
        return uid;
    }

    @Override
    public int getNoteId() {
        return nid;
    }

    @Override
    public void onThumbSuccess() {

    }

    @Override
    public void onGetNoteInfoSuccess(final Note note) {
        notetitle.setText(note.getTitle());
        notecontent.setText(note.getContent());
        llnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NoteDetailActivity.class);
                intent.putExtra("noteId", note.getId());
                intent.putExtra("userId", note.getUid());
                startActivity(intent);
            }
        });
        if(showResult ==1){
            llResult.setVisibility(View.VISIBLE);
            llButton.setVisibility(View.GONE);
        }else{
            llResult.setVisibility(View.GONE);
            llButton.setVisibility(View.VISIBLE);
        }
        if(verify==1){
            tvResult.setText("恭喜你，你的请求被作者接受");
        }else{
            tvResult.setText("很遗憾，你的请求被作者拒绝");
        }


    }

    @Override
    public void onGetUserInfoSuccess(final User user) {
        tvNtNickname.setText(user.getNickname());
        PicassoUtil.show(user.getIcon(),imgNtProfile);
        llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra("uid",user.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onOnGetPictureSuccess(List<String> paths) {

    }

    @Override
    public void onGetCatInfoSuccess(Catinfo catinfo) {

    }

    @Override
    public void onApplySuccess() {

    }

    @Override
    public void onPassSuccess() {

    }

    @Override
    public void onLockSuccess() {

    }

    @Override
    public void onUnlockSuccess() {

    }

    @Override
    public void onDeleteSuccess() {

    }

    @Override
    public void onGetApplySuccess(Apply apply) {
        nid = apply.getNid();
        uid = apply.getUid();
        verify = apply.getVerify();
        mPresenter.getUserInfo();
    }

    @Override
    public Apply getApply() {
        Apply apply = new Apply();
        apply.setId(aid);
        apply.setCid(choiceFlag);//标识是否同意请求
        return apply;
    }

    @Override
    public void onChoiceDone() {
        CommonUtil.showDialog(mContext, "提示", "操作成功", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO: 2018/4/9  关闭后刷新主页通知栏
                setResult(ApiConfig.RESULTCODE_NOTIFICATION);
                finish();
            }
        }, "关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(ApiConfig.RESULTCODE_NOTIFICATION);
                finish();
            }
        });
    }


    @OnClick({R.id.toolbar_image_left, R.id.ll_profile, R.id.note, R.id.btn_agree, R.id.btn_disagree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_image_left:
                setResult(ApiConfig.RESULTCODE_NOTIFICATION);
                finish();
                break;
            case R.id.btn_agree:
                choiceFlag = 1;
                mPresenter.makeChoice();
                break;
            case R.id.btn_disagree:
                choiceFlag = 0;
                mPresenter.makeChoice();
                break;
        }
    }
}
