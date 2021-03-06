package com.suramire.miaowu.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListFragment;
import com.suramire.miaowu.bean.Notification;
import com.suramire.miaowu.contract.NotificationContract;
import com.suramire.miaowu.presenter.NotificationPresenter;
import com.suramire.miaowu.ui.ApplyDetailActivity;
import com.suramire.miaowu.ui.UserActivity;
import com.suramire.miaowu.util.CommonUtil;

import java.util.List;

/**
 * Created by Suramire on 2018/1/26.
 */

public class NotificationFragment extends BaseListFragment<NotificationPresenter> implements NotificationContract.View {
    private Integer currentId;
    private Intent intent;
    private int count;
    private Integer currentType;

    public interface onNotificationListener{
        void onChange(int count);
    }

    private onNotificationListener listener;

    public void setListener(onNotificationListener listener) {
        this.listener = listener;
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void cancelLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSuccess(Object data) {

        if(data!=null){
            List<Notification> notifications = (List<Notification>) data;
            count = 0;
            for (int i = 0; i < notifications.size(); i++) {
                if(notifications.get(i).getIsread()==0){
                    count++;
                }
            }
            if(listener!=null){
                listener.onChange(count);
            }
            if(notifications.size()>0){
                showNotifications(notifications);
            }else{
                showEmpty("暂无通知信息");
            }
        }else{
            showEmpty("暂无通知信息");
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
        setRequireFresh(true);
        progressDialog.setMessage("正在读取通知，请稍候……");
        if(CommonUtil.isLogined()){
            mPresenter.getNotifications();
        }else{
            showEmpty("请先登录");
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listview.setAdapter(null);
                mPresenter.getNotifications();
            }
        });
    }

    private void showNotifications(List<Notification> notifications) {
        showList();

        listview.setLayoutManager(new LinearLayoutManager(mContext));
        listview.setAdapter(new CommonRecyclerAdapter<Notification>(mContext, R.layout.item_notification, notifications) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, final Notification item, final int position) {
                helper.setText(R.id.tv_notification_time, CommonUtil.timeStampToDateString(item.getTime()))
                        .setText(R.id.tv_notification_content, item.getContent());
                if(item.getIsread()==0){
                    helper.setText(R.id.tv_isread,"未读");
                    ((TextView) helper.getView(R.id.tv_isread)).setTextColor(getResources().getColor(R.color.orange));
                }else{
                    helper.setText(R.id.tv_isread,"已读");
                    ((TextView) helper.getView(R.id.tv_isread)).setTextColor(getResources().getColor(R.color.verydarkgray));
                }
                helper.setOnClickListener(R.id.tv_notification_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        currentType = item.getType();
                        if(item.getIsread()!=1){
                            switch (item.getType()){
                                case 1:{
                                    intent = new Intent(mContext, UserActivity.class);
                                    intent.putExtra("uid", item.getUid1());
                                }break;//关注类通知 点击跳转到关注源用户
                                case 2:{
//                                intent = new Intent(mContext, NoteDetailActivity.class);
//                                intent.putExtra("noteId", item.getUid1());
//                                intent.putExtra("userId", item.getUid2());
                                }break;
                                case 3:{
                                    intent = new Intent(mContext, ApplyDetailActivity.class);
                                    intent.putExtra("aid", item.getUid1());
                                    //已经审核的申请不再显示
                                    if(item.getIsread()==1){
                                        return;
                                    }
                                }break;
                                default:break;
                            }
                            //已读的通知不能再点击
                            item.setIsread(1);
                            notifyDataSetChanged();
                            currentId = item.getId();
                            mPresenter.readNotification();
                        }


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
        if (intent!=null&&currentType!=4){
            startActivity(intent);
        }
        if(count>0){
            count--;
            if(listener!=null){
                listener.onChange(count);
            }
        }
    }
}
