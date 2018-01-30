package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListActivity;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.FansContract;
import com.suramire.miaowu.presenter.FansPresenter;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.PicassoUtil;
import com.suramire.miaowu.wiget.MyToolbar;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2018/1/30.
 */

public class FansListActivity extends BaseListActivity<FansPresenter> implements FansContract.View {
    @Bind(R.id.toolbar_image_left)
    ImageView toolbarImageLeft;
    @Bind(R.id.toolbar_text_center)
    TextView toolbarTextCenter;
    @Bind(R.id.toolbar_text_right)
    TextView toolbarTextRight;
    @Bind(R.id.toolbar)
    MyToolbar toolbar;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    private int uid;
    private int currentPosition;
    private ProgressDialog progressDialog;

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
        if(data!=null){
            showData((List<User>) data);
        }else{
            switch (currentPosition){
                case 0:showEmpty("暂无关注用户");break;
                case 1:showEmpty("暂无粉丝用户");break;
            }
        }

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_fans;
    }

    @Override
    public void createPresenter() {
        mPresenter = new FansPresenter();
    }

    @Override
    public void initView() {
        toolbar.setTitle("好友列表");
        toolbar.setLeftImage(R.drawable.ic_arrow_back_black);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("请稍候……");
        final int index = getIntent().getIntExtra("index", 0);
        uid = getIntent().getIntExtra("uid", 0);

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPosition = tab.getPosition();
                switch (currentPosition){
                    case 0:mPresenter.getFollow();break;
                    case 1:mPresenter.getFollower();break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tablayout.getTabAt(index).select();
        switch (currentPosition){
            //若通过关注进入则强制刷新列表
            case 0:showEmpty("暂无关注用户");mPresenter.getFollow();break;
            case 1:showEmpty("暂无粉丝用户");break;
        }

    }

    private void showData(List<User> users){
        showList();
        listview.setLayoutManager(new LinearLayoutManager(mContext));

        listview.setAdapter(new CommonRecyclerAdapter<User>(mContext,R.layout.item_fan, users) {

            @Override
            public void onUpdate(BaseAdapterHelper helper, final User item, int position) {
                helper.setText(R.id.tv_username,item.getNickname())
                        .setOnClickListener(R.id.ll_fans, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, ProfileActivity.class);
                                intent.putExtra("uid", item.getId());
                                startActivity(intent);
                            }
                        });
                PicassoUtil.show(ApiConfig.BASUSERPICEURL+item.getIcon(), (ImageView) helper.getView(R.id.img_icon));
            }
        });
    }


    @OnClick(R.id.toolbar_image_left)
    public void onViewClicked() {
        finish();
    }


    @Override
    public int getUid() {
        return uid;
    }
}
