package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.contract.CatContract;
import com.suramire.miaowu.presenter.CatPresenter;
import com.suramire.miaowu.wiget.MyToolbar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CatDetailActivity extends BaseActivity<CatPresenter> implements CatContract.View {

    @Bind(R.id.toolbar)
    MyToolbar toolbar;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.tv_neutering)
    TextView tvNeutering;
    @Bind(R.id.tv_insecticide)
    TextView tvInsecticide;
    @Bind(R.id.edt_type)
    TextView edtType;
    @Bind(R.id.tv_conditions)
    TextView tvConditions;
    private ProgressDialog progressDialog;
    private int cid;

    @Override
    public int bindLayout() {
        return R.layout.activity_cat_detail;
    }

    @Override
    public void createPresenter() {
        mPresenter = new CatPresenter();
    }

    @Override
    public void initView() {
        cid = getIntent().getIntExtra("cid", 0);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("请稍候……");
        mPresenter.getCat();
    }

    @Override
    public Catinfo getCatInfo() {
        return null;
    }

    @Override
    public int getCatId() {
        return cid;
    }

    @Override
    public void onAddCatSuccess(int cid) {

    }

    @Override
    public void onGetCatListSuccess(List<Catinfo> catinfos) {

    }

    @Override
    public List<String> getStringPaths() {
        return null;
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void cancelLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void onSuccess(Object data) {
        if (data != null) {
            Catinfo catinfo = (Catinfo) data;
            tvSex.setText(catinfo.getSex()==0?"未知":(catinfo.getSex()==1)?"公":"母");

        }
    }


    @OnClick({R.id.toolbar_image_left, R.id.toolbar_text_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_image_left:
                finish();
                break;
            case R.id.toolbar_text_right:
                break;
        }
    }
}
