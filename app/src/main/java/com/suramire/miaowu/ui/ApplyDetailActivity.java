package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.ReviewApplyContract;
import com.suramire.miaowu.presenter.ReviewApplyPresenter;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.PicassoUtil;
import com.suramire.miaowu.util.ToastUtil;
import com.suramire.miaowu.wiget.MyToolbar;

import butterknife.Bind;
import butterknife.OnClick;

import static com.suramire.miaowu.util.ApiConfig.BASUSERPICEURL;

/**
 * Created by Suramire on 2018/4/9.
 */

public class ApplyDetailActivity extends BaseActivity<ReviewApplyPresenter> implements ReviewApplyContract.View {

    @Bind(R.id.toolbar)
    MyToolbar toolbar;
    @Bind(R.id.img_nt_profile)
    RoundedImageView imgNtProfile;
    @Bind(R.id.tv_nt_nickname)
    TextView tvNtNickname;
    @Bind(R.id.ll_profile)
    LinearLayout llProfile;
    @Bind(R.id.tv_)
    TextView tvId;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.tv_neutering)
    TextView tvNeutering;
    @Bind(R.id.tv_insecticide)
    TextView tvInsecticide;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.tv_conditions)
    TextView tvConditions;
    private ProgressDialog mProgressDialog;
    private int cid;
    private Integer uid;
    private int flag;
    private boolean clicked;

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

        CommonUtil.showDialog(mContext, "操作成功", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_applydetail;
    }

    @Override
    public void createPresenter() {
        mPresenter = new ReviewApplyPresenter();
    }

    @Override
    public void initView() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("请稍候……");
        toolbar.setTitle("申请详情");
        toolbar.setLeftImage(R.drawable.ic_arrow_back_black);
        flag = 0;
        cid = getIntent().getIntExtra("aid", 0);
        if(cid!=0){
            mPresenter.getCatInfo();
        }
    }



    @OnClick({R.id.btn_agree, R.id.btn_disagree,R.id.toolbar_image_left,R.id.ll_profile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_image_left:
                setResult(ApiConfig.RESULTCODE_NOTIFICATION);
                finish();
                break;
            case R.id.btn_agree:
                flag = 1;
                if(!clicked){
                    clicked = true;
                    mPresenter.reviewApply();
                }
                break;
            case R.id.btn_disagree:
                flag = 0;
                if(!clicked){
                    mPresenter.reviewApply();
                    clicked = true;
                }
                // TODO: 2018/4/13 添加确认对话框
                break;
            case R.id.ll_profile:
                Bundle bundle = new Bundle();
                bundle.putInt("uid",uid);
                startActivity(UserActivity.class, bundle);
                break;
        }
    }

    @Override
    public int getFlag() {
        return flag;
    }

    @Override
    public int getUid() {
        return uid;
    }

    @Override
    public int getCid() {
        return cid;
    }

    @Override
    public void onGetUserSuccess(User user) {
        tvNtNickname.setText(user.getNickname());
        String icon = user.getIcon();
        PicassoUtil.showIcon(icon==null?null:BASUSERPICEURL+icon,imgNtProfile);
    }

    @Override
    public void onGetCatSuccess(Catinfo catinfo) {
        uid = catinfo.getUid();
        tvId.setText(catinfo.getId()+"");
        tvSex.setText(catinfo.getSex()==0?"未知":(catinfo.getSex()==1)?"公":"母");
        tvAge.setText(catinfo.getAge()==0?"未知":catinfo.getAge()+"");
        tvNeutering.setText(catinfo.getNeutering()==0?"未知":(catinfo.getNeutering()==1)?"是":"否");
        tvInsecticide.setText(catinfo.getInsecticide()==0?"未知":(catinfo.getInsecticide()==1)?"是":"否");
        tvType.setText(catinfo.getType() == null ? "未知" : catinfo.getType());
        tvConditions.setText(catinfo.getConditions());
        mPresenter.getUserInfo();
    }
}
