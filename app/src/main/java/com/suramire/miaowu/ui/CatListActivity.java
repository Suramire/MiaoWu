package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListActivity;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.contract.CatContract;
import com.suramire.miaowu.presenter.CatPresenter;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.PicassoUtil;

import java.util.List;

public class CatListActivity extends BaseListActivity<CatPresenter> implements CatContract.View {

    private ProgressDialog progressDialog;

    @Override
    public int bindLayout() {
        return R.layout.activity_catlist;
    }

    @Override
    public void createPresenter() {
        mPresenter = new CatPresenter();
    }

    @Override
    public void initView() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("请稍候……");
        mPresenter.listCats();
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

    }

    @Override
    public Catinfo getCatInfo() {
        return null;
    }

    @Override
    public int getCatId() {
        return 0;
    }

    @Override
    public void onAddCatSuccess(int cid) {

    }

    @Override
    public void onGetCatListSuccess(List<Catinfo> catinfos) {
        if(catinfos!=null && catinfos.size()!=0){
            showList();
            listview.setLayoutManager(new LinearLayoutManager(mContext));
            listview.setAdapter(new CommonRecyclerAdapter<Catinfo>(mContext, R.layout.item_cat, catinfos) {
                @Override
                public void onUpdate(BaseAdapterHelper helper, final Catinfo item, int position) {
                    helper.setText(R.id.tv_id, "编号：" + item.getId())
                            .setText(R.id.tv_adddate, "添加时间：" + item.getAdddate())
                            .setText(R.id.tv_type, "品种："+(item.getType() == null ? "未知" : item.getType()));
                    helper.setOnClickListener(R.id.cardview_item, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    L.e("clicked");
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("cid", item.getId());
                                    startActivity(CatDetailActivity.class,bundle);
                                }
                            });
                    PicassoUtil.show(ApiConfig.BASECATPICEURL+item.getId()+"_0.png", (ImageView) helper.getView(R.id.catimg));
                }
            });
        }else{
            showEmpty("暂时没有猫咪信息");
        }
    }

    @Override
    public List<String> getStringPaths() {
        return null;
    }
}
