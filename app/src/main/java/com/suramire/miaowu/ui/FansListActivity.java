package com.suramire.miaowu.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListActivity;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.FansContract;
import com.suramire.miaowu.presenter.FansPresenter;
import com.suramire.miaowu.util.PicassoUtil;

import java.util.List;

import butterknife.Bind;

import static com.suramire.miaowu.util.ApiConfig.BASUSERPICEURL;

/**
 * Created by Suramire on 2018/1/30.
 */

public class FansListActivity extends BaseListActivity<FansPresenter> implements FansContract.View {
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    private int uid;
    private int currentPosition;


    @Override
    public void onSuccess(Object data) {

        if(data !=null){
            List<User> data1 = (List<User>) data;
            if(data1.size()>0){
                showData(data1);
            }else{
                switch (currentPosition){
                    case 0:showEmpty("暂无关注用户");break;
                    case 1:showEmpty("暂无粉丝用户");break;
                }
            }

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
        setTitle("好友列表");
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
        //根据传过来的下标使选择对应标签页
        tablayout.getTabAt(index).select();
        if(index==0){
            mPresenter.getFollow();
        }

    }

    private void showData(List<User> users){
        if(users!=null && users.size()>0){
            showList();
            listview.setLayoutManager(new LinearLayoutManager(mContext));
            listview.setAdapter(new CommonRecyclerAdapter<User>(mContext,R.layout.item_fan, users) {

                @Override
                public void onUpdate(BaseAdapterHelper helper, final User item, int position) {
                    helper.setText(R.id.tv_username,item.getNickname())
                            .setOnClickListener(R.id.ll_fans, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(mContext, UserActivity.class);
                                    intent.putExtra("uid", item.getId());
                                    startActivity(intent);
                                }
                            });
                    String icon = item.getIcon();
                    PicassoUtil.showIcon(icon==null?null:BASUSERPICEURL+icon,(ImageView) helper.getView(R.id.img_icon));
                }
            });
        }

    }


    @Override
    public int getUid() {
        return uid;
    }
}
