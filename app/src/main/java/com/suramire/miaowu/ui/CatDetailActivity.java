package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.suramire.miaowu.R;
import com.suramire.miaowu.adapter.MultiItemAdapter;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.M;
import com.suramire.miaowu.contract.CatContract;
import com.suramire.miaowu.presenter.CatPresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.ToastUtil;
import com.suramire.miaowu.wiget.MyToolbar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.suramire.miaowu.util.ApiConfig.BASECATPICEURL;
import static com.suramire.miaowu.util.ApiConfig.BASNOTEPICEURL;


/**
 * 猫咪详细信息页
 * 查看信息 进行领养操作
 */

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
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.tv_conditions)
    TextView tvConditions;
    @Bind(R.id.banner)
    Banner banner;

    private ProgressDialog progressDialog;
    private int cid;
    private boolean flag;

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
        toolbar.setTitle("猫咪详情");
        toolbar.setLeftImage(R.drawable.ic_arrow_back_black);
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
    public int getUid() {
        return CommonUtil.getCurrentUid();
    }

    @Override
    public void onAddCatSuccess(int cid) {

    }

    @Override
    public void onApplyCatSuccess() {
        ToastUtil.showShortToastCenter("申请成功，请等待管理员审核");
        flag = true;
    }


    @Override
    public List<String> getStringPaths() {
        return null;
    }

    @Override
    public void onGetAllPicturesSuccess(List<String> paths) {
        if(paths!=null && paths.size()!=0){
            final List<String> newItems = new ArrayList<>();
            for (String s: paths) {
                newItems.add(BASECATPICEURL+s);
            }
            banner.setImageLoader(new GlideImageLoader())
                    .setImages(newItems)
                    .isAutoPlay(false)
                    .setBannerStyle(BannerConfig.NUM_INDICATOR)
                    .setIndicatorGravity(BannerConfig.RIGHT)
                    .start();
        } else{
            banner.setVisibility(View.GONE);
        }

    }

    @Override
    public void onListAppliedCatSuccess(List<M> catinfo) {

    }


    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context)
                    .load(path)
                    .placeholder(R.mipmap.ic_loading)
                    .error(R.mipmap.ic_loading_error)
                    .into(imageView);
        }
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
            tvAge.setText(catinfo.getAge()==0?"未知":catinfo.getAge()+"");
            tvNeutering.setText(catinfo.getNeutering()==0?"未知":(catinfo.getNeutering()==1)?"是":"否");
            tvInsecticide.setText(catinfo.getInsecticide()==0?"未知":(catinfo.getInsecticide()==1)?"是":"否");
            tvType.setText(catinfo.getType() == null ? "未知" : catinfo.getType());
            tvConditions.setText(catinfo.getConditions());
            if(catinfo.getIsAdopted()==0){
                //可以领养
                toolbar.setRightText("申请领养");
                toolbar.setRightOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!flag){
                            mPresenter.applyCat();
                        }
                    }
                });
            }
            mPresenter.getAllPictures();
        }
    }


    @OnClick({R.id.toolbar_image_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_image_left:
                finish();
                break;
        }
    }
}
