package com.suramire.miaowu.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListActivity;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.M;
import com.suramire.miaowu.contract.CatContract;
import com.suramire.miaowu.presenter.CatPresenter;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.GsonUtil;

import java.util.List;

/**
 * Created by Suramire on 2018/1/27.
 */

public class ApplyListActivity extends BaseListActivity<CatPresenter> implements CatContract.View {


    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_list_toolbar;
    }

    @Override
    public void createPresenter() {
        mPresenter = new CatPresenter();
    }

    @Override
    public void initView() {
        progressDialog.setMessage("请稍候……");
        setTitle("申请列表");
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.listAppliedCat();
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
    public int getUid() {
        return 0;
    }

    @Override
    public void onAddCatSuccess(int cid) {

    }

    @Override
    public void onApplyCatSuccess() {

    }

    @Override
    public List<String> getStringPaths() {
        return null;
    }

    @Override
    public void onUploadCatPicturesSuccess() {

    }

    @Override
    public void onGetAllPicturesSuccess(List<String> paths) {

    }

    @Override
    public void onListAppliedCatSuccess(List<M> catinfo) {
        if(catinfo.size()>0){
            showList();
            listview.setLayoutManager(new LinearLayoutManager(mContext));
            listview.setAdapter(new CommonRecyclerAdapter<M>(mContext,R.layout.item_apply,catinfo) {

                @Override
                public void onUpdate(BaseAdapterHelper helper, final M item, int position) {
                    final Catinfo cat = (Catinfo) GsonUtil.jsonToObject(item.getStringy(),Catinfo.class);
                    helper.setText(R.id.textView4,"申请人:"+item.getStringx())
                            .setText(R.id.textView2,"编号："+cat.getId())
                            .setText(R.id.textView3, "申请时间："+CommonUtil.dateToString(cat.getAdoptdate()));
                    helper.setOnClickListener(R.id.ll_apply, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("aid",cat.getId());
                            startActivity(ApplyDetailActivity.class,bundle);
                        }
                    });

                }
            });
        }else{
            showEmpty("暂无待审核领养申请");
        }

    }


}
