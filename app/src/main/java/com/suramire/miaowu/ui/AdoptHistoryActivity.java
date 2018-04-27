package com.suramire.miaowu.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListActivity;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.contract.AdoptContract;
import com.suramire.miaowu.presenter.AdoptPresenter;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Suramire on 2018/4/9.
 */

public class AdoptHistoryActivity extends BaseListActivity<AdoptPresenter> implements AdoptContract.View {
    private int uid;
    private SimpleDateFormat simpleDateFormat;


    @Override
    public void onSuccess(Object data) {
        if(data!=null){
            final List<Catinfo> applies = (List<Catinfo>) data;
            if(applies.size()==0){
                showEmpty("暂无记录");
            }else{
                showList();
                listview.setLayoutManager(new LinearLayoutManager(mContext));
                listview.setAdapter(new CommonRecyclerAdapter<Catinfo>(mContext,R.layout.item_adopt,applies) {

                    @Override
                    public void onUpdate(BaseAdapterHelper helper, final Catinfo item, int position) {
                        helper.setText(R.id.textView2, "编号："+item.getId())
                                .setText(R.id.textView3, "领养日期："+simpleDateFormat.format(item.getAdoptdate()));
                        helper.setOnClickListener(R.id.ll_adopt, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("cid", item.getId());
                                startActivity(CatDetailActivity.class,bundle);
                            }
                        });

                    }
                });
            }
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_adoptlist;
    }

    @Override
    public void createPresenter() {
        mPresenter = new AdoptPresenter();
    }

    @Override
    public void initView() {
        setTitle("领养记录列表");
        progressDialog.setMessage("请稍候……");
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        uid = getIntent().getIntExtra("uid", 0);
        mPresenter.getAdoptHistory();
    }

    @Override
    public int getUid() {
        return uid;
    }
}
