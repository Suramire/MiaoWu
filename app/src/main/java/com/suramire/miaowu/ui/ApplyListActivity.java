package com.suramire.miaowu.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListActivity;
import com.suramire.miaowu.bean.Apply;
import com.suramire.miaowu.contract.ApplyContract;
import com.suramire.miaowu.presenter.ApplyPresenter;
import com.suramire.miaowu.wiget.MyToolbar;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Suramire on 2018/4/9.
 */

public class ApplyListActivity extends BaseListActivity<ApplyPresenter> implements ApplyContract.View {
    @Bind(R.id.toolbar_image_left)
    ImageView toolbarImageLeft;
    @Bind(R.id.toolbar_text_center)
    TextView toolbarTextCenter;
    @Bind(R.id.toolbar_text_right)
    TextView toolbarTextRight;
    @Bind(R.id.toolbar)
    MyToolbar toolbar;
    private int uid;
    private SimpleDateFormat simpleDateFormat;


    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void onSuccess(Object data) {
        if(data!=null){
            final List<Apply> applies = (List<Apply>) data;
            if(applies==null || applies.size()==0){
                showEmpty("暂无记录");
            }else{
                showList();
                listview.setLayoutManager(new LinearLayoutManager(mContext));
                listview.setAdapter(new CommonRecyclerAdapter<Apply>(mContext,R.layout.item_apply,applies) {

                    @Override
                    public void onUpdate(BaseAdapterHelper helper, Apply item, int position) {
                        helper.setText(R.id.textView2, position + 1 + "")
                                .setText(R.id.textView3, simpleDateFormat.format(item.getTime()))
                                .setText(R.id.textView4, item.getNid()+"");
                    }
                });
            }
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_applylist;
    }

    @Override
    public void createPresenter() {
        mPresenter = new ApplyPresenter();
    }

    @Override
    public void initView() {
        toolbarTextCenter.setText("领养记录列表");


        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        toolbar.setLeftImage(R.drawable.ic_arrow_back_black);
        toolbar.setLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        uid = getIntent().getIntExtra("uid", 0);

        mPresenter.getApplys();

    }

    @Override
    public int getUid() {
        return uid;
    }
}
