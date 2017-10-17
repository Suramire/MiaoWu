package com.suramire.miaowu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.App;
import com.suramire.miaowu.test.model.ReceiveItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;

/**
 * Created by Suramire on 2017/10/17.
 */

public class TestActivity extends AppCompatActivity {
    @Bind(R.id.list_receive_test)
    RecyclerView mListReceiveTest;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.ll_bottom)
    LinearLayout mLlBottom;
    @Bind(R.id.edittext_comment)
    EditText mEdittextComment;
    @Bind(R.id.ll_popup)
    LinearLayout mLlPopup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_test);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("评论详情");
        mListReceiveTest.setLayoutManager(new LinearLayoutManager(this));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<ReceiveItem> receiveItems = new ArrayList<>();
        try {
            receiveItems.add(new ReceiveItem(1, 1, 0, "张三", "这是回复内容", sdf.parse("2017-10-15 10:20:30")));
            receiveItems.add(new ReceiveItem(1, 2, 1, "李四", "啦啦啦", sdf.parse("2017-10-15 11:00:30")));
            receiveItems.add(new ReceiveItem(1, 1, 0, "张三", "哈哈哈", sdf.parse("2017-10-15 12:20:10")));
        } catch (ParseException e) {
            Log.e("TestActivity", "e:" + e);
        }
        ArrayList<Spanned> receives = new ArrayList<>();

        for (ReceiveItem r : receiveItems) {
            if (r.getrId() == 0) {
                //不回复谁 直接显示在评论列表
                receives.add(Html.fromHtml("<font color='blue'>" + r.getuName() + "</font> : " + r.getuContent()));
//                receives.add(Html.fromHtml("<font color='blue'>%s</font> : 回复 %s %s"),r.getuName());
            } else {
                for (ReceiveItem r1 : receiveItems) {
                    if (r1.getuId() == r.getrId()) {
                        receives.add(Html.fromHtml("<font color='blue'>" + r.getuName() + "</font> : <font color='blue'>@" + r.getuName() + "</font> " + r.getuContent()));
                        break;
                    }
                }
            }
        }
        mListReceiveTest.setAdapter(new CommonRecyclerAdapter<Spanned>(App.getApp(), R.layout.item_receive_test, receives) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, Spanned item, int position) {
                helper.setText(R.id.textView18, item);
            }
        });

    }

    @OnClick(R.id.editText3)
    public void onViewClicked() {
        if (mLlBottom.getVisibility() != GONE) {
            mLlBottom.setVisibility(GONE);
        }
        mLlPopup.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (mLlPopup.getVisibility() == View.VISIBLE) {
            mLlPopup.setVisibility(GONE);
            mLlBottom.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }

    }
}
