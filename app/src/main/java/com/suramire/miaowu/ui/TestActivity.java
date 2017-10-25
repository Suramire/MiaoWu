package com.suramire.miaowu.ui;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by Suramire on 2017/10/17.
 */

public class TestActivity extends BaseActivity {
    @Bind(R.id.list_receive_test)
    RecyclerView mListReceiveTest;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.ll_reply)
    LinearLayout mLlReply;
    @Bind(R.id.imageView11)
    ImageView mImageView11;
    @Bind(R.id.rl_replydetail)
    RelativeLayout mRlReplydetail;


    @Override
    protected String getTitleString() {
        return "评论详情";
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_profile_introduction;
    }

    @Override
    public void initView(View view) {
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mListReceiveTest.setLayoutManager(new LinearLayoutManager(this));
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        ArrayList<ReceiveItem> receiveItems = new ArrayList<>();
//        try {
//            receiveItems.add(new ReceiveItem(1, 1, 0, "张三", "这是回复内容", sdf.parse("2017-10-15 10:20:30")));
//            receiveItems.add(new ReceiveItem(1, 2, 1, "李四", "啦啦啦", sdf.parse("2017-10-15 11:00:30")));
//            receiveItems.add(new ReceiveItem(1, 1, 0, "张三", "哈哈哈", sdf.parse("2017-10-15 12:20:10")));
//        } catch (ParseException e) {
//            Log.e("TestActivity", "e:" + e);
//        }
//        ArrayList<Spanned> receives = new ArrayList<>();
//
//        for (ReceiveItem r : receiveItems) {
//            if (r.getrId() == 0) {
//                //不回复谁 直接显示在评论列表
//                receives.add(Html.fromHtml("<font color='blue'>" + r.getuName() + "</font> : " + r.getuContent()));
////                receives.add(Html.fromHtml("<font color='blue'>%s</font> : 回复 %s %s"),r.getuName());
//            } else {
//                for (ReceiveItem r1 : receiveItems) {
//                    if (r1.getuId() == r.getrId()) {
//                        receives.add(Html.fromHtml("<font color='blue'>" + r.getuName() + "</font> : <font color='blue'>@" + r.getuName() + "</font> " + r.getuContent()));
//                        break;
//                    }
//                }
//            }
//        }
//        mListReceiveTest.setAdapter(new CommonRecyclerAdapter<Spanned>(mContext, R.layout.item_reply_test, receives) {
//            @Override
//            public void onUpdate(BaseAdapterHelper helper, Spanned item, int position) {
//                helper.setText(R.id.textView18, item);
//            }
//        });


    }


//
//    @OnClick(R.id.editText3)
//    public void onViewClicked() {
////        if (mLlBottom.getVisibility() != GONE) {
////            mLlBottom.setVisibility(GONE);
////        }
////        mLlPopup.setVisibility(View.VISIBLE);
//    }

//    @Override
//    public void onBackPressed() {
////        if (mLlPopup.getVisibility() == View.VISIBLE) {
////            mLlPopup.setVisibility(GONE);
////            mLlBottom.setVisibility(View.VISIBLE);
////        } else {
////            super.onBackPressed();
////        }
//    }


//    @OnClick({R.id.imageView11, R.id.btn_reply})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.imageView11:
//                CommonUtil.toggleVisiable(mRlReplydetail);
////                mLlReply.setVisibility(View.VISIBLE);
//                break;
//            case R.id.btn_reply:
//                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        mGestureDetector.onTouchEvent(event);
//        return true;
//    }
}
