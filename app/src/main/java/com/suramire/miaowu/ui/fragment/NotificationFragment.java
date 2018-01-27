package com.suramire.miaowu.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseListFragment;
import com.suramire.miaowu.bean.Notification;
import com.suramire.miaowu.util.A;
import com.suramire.miaowu.util.ToastUtil;

import butterknife.Bind;

/**
 * Created by Suramire on 2018/1/26.
 */

public class NotificationFragment extends BaseListFragment {
    @Bind(R.id.listview)
    RecyclerView listview;

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_list;
    }

    @Override
    public void createPresenter() {

    }

    @Override
    public void initView() {
        showList();
        listview.setLayoutManager(new LinearLayoutManager(mContext));
        listview.setAdapter(new CommonRecyclerAdapter<Notification>(mContext,R.layout.item_notification, A.getNotifications(5)) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, final Notification item, final int position) {
                helper.setText(R.id.tv_notification_time, A.timeStampToDateTimeString(item.getTime()))
                        .setText(R.id.tv_notification_content, item.getContent());
                helper.setOnClickListener(R.id.tv_notification_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showShortToastCenter("点击通知内容:类型"+item.getType());
                    }
                });
            }
        });
    }


}
