package com.suramire.miaowu.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListFragment;
import com.suramire.miaowu.bean.Notification;
import com.suramire.miaowu.contract.NotificationContract;
import com.suramire.miaowu.presenter.NotificationPresenter;
import com.suramire.miaowu.ui.ProfileActivity;
import com.suramire.miaowu.util.A;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.ToastUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Suramire on 2018/1/26.
 */

public class NotificationFragment extends BaseListFragment<NotificationPresenter> implements NotificationContract.View {
    @Bind(R.id.listview)
    RecyclerView listview;
    private Integer currentId;

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void onSuccess(Object data) {
        if(data!=null){
            List<Notification> notifications = (List<Notification>) data;
            if(notifications!=null && notifications.size()>0){
                showNotifications(notifications);
            }else{
                showEmpty("暂无通知信息");
            }
        }else{
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_list;
    }

    @Override
    public void createPresenter() {
        mPresenter = new NotificationPresenter();
    }

    @Override
    public void initView() {
        if(CommonUtil.isLogined()){
            mPresenter.getNotifications();
        }else{
            showEmpty("请先登录");
        }
    }

    private void showNotifications(List<Notification> notifications) {
        showList();
        listview.setLayoutManager(new LinearLayoutManager(mContext));
        listview.setAdapter(new CommonRecyclerAdapter<Notification>(mContext, R.layout.item_notification, notifications) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, final Notification item, final int position) {
                helper.setText(R.id.tv_notification_time, A.timeStampToDateTimeString(item.getTime()))
                        .setText(R.id.tv_notification_content, item.getContent());
                if(item.getIsread()==0){
                    helper.setText(R.id.tv_isread,"未读");
                    ((TextView) helper.getView(R.id.tv_isread)).setTextColor(getResources().getColor(R.color.orange));
                }else{
                    helper.setText(R.id.tv_isread,"已读");
                    ((TextView) helper.getView(R.id.tv_isread)).setTextColor(getResources().getColor(R.color.darkgray));
                }
                helper.setOnClickListener(R.id.tv_notification_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showShortToastCenter("点击通知内容:类型"+item.getType());
                        switch (item.getType()){
                            case 1:{
                                Intent intent = new Intent(mContext, ProfileActivity.class);
                                intent.putExtra("uid", item.getUid1());
                                startActivity(intent);
                            }break;//关注类通知 点击跳转到关注源用户
                        }

                        currentId = item.getId();
                        mPresenter.readNotification();
                    }
                });
            }
        });
    }


    @Override
    public int getUid() {
        return CommonUtil.getCurrentUid();
    }

    @Override
    public int getNofiticationId() {
        return currentId;
    }

    @Override
    public void onReadSuccess(int id) {
        // TODO: 2018/1/31 更新通知列表
    }
}
