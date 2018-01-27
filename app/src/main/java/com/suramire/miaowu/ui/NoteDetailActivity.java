package com.suramire.miaowu.ui;


import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.suramire.miaowu.R;
import com.suramire.miaowu.adapter.MultiItemAdapter;
import com.suramire.miaowu.base.BaseSwipeActivity;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.contract.NoteDetailContract;
import com.suramire.miaowu.presenter.NoteDetailPresenter;
import com.suramire.miaowu.ui.dialog.BottomCommentDialogFragment;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.ToastUtil;
import com.suramire.miaowu.wiget.MyToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2017/10/17.
 */

public class NoteDetailActivity extends BaseSwipeActivity<NoteDetailPresenter> implements NoteDetailContract.View {

    @Bind(R.id.toolbar)
    MyToolbar mToolbar;
    @Bind(R.id.list_notedetail)
    ListView mListNotedetail;
    @Bind(R.id.bar_num)
    TextView mBarNum;
    @Bind(R.id.bar_num2)
    TextView mBarNum2;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    @Bind(R.id.edittext_comment)
    EditText edittextComment;
    @Bind(R.id.imageView9)
    ImageView imageView9;
    @Bind(R.id.imageView13)
    ImageView imageView13;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.ll_popup)
    LinearLayout llPopup;
    private ProgressDialog mProgressDialog;
    private int mNoteId;
    private MultiItemAdapter mAdapter;
    private List<Object> mObjects;
    private boolean thumbed;
    private Integer mThumbs;
    private int mReplyCount;
    private boolean isAtComment;//是否在评论页标志位
    private int index;//上次浏览的index
    private int top;//距离顶部距离

    @Override
    public int bindLayout() {
        return R.layout.activity_notedetail;
    }

    @Override
    public void createPresenter() {
        mPresenter = new NoteDetailPresenter();
    }

    @Override
    public void initView() {
        mToolbar.setTitle("帖子详情");
        mToolbar.setStyle(MyToolbar.STYLE_LEFT_AND_TITLE);
        mToolbar.setLeftImage(R.drawable.ic_arrow_back_black);
        mToolbar.setLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mNoteId = getIntent().getIntExtra("noteId", 0);
        Multi multi = (Multi) getIntent().getSerializableExtra("multi");
        //查询帖子信息
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在读取帖子信息，请稍候……");

//        mNoteDetailPresenter.getData();
        // TODO: 2017/11/14 点击详情页 浏览次数+1
        mObjects = new ArrayList<>();
        List<String> picturesStrings = multi.getPicturesStrings();
        mObjects.add(picturesStrings);
        mObjects.add(multi.getmUser());
        mObjects.add(multi.getmNote());
        Catinfo catinfo = multi.getmCatinfo();
        if (catinfo != null) {
            mObjects.add(catinfo);
        }

        mAdapter = new MultiItemAdapter(this, mObjects);
        mListNotedetail.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mThumbs = multi.getmNote().getThumbs();
        thumb(mThumbs);
        mPresenter.getReply();

    }


    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void cancelLoading() {
        mProgressDialog.cancel();
    }

    @Override
    public void onSuccess(Object object) {
        List<Multi> replies = (List<Multi>) object;
        for (Multi multi : replies
                ) {
            mObjects.add(multi);
            //统计楼层数（非楼层回复）
            if (multi.getmReply().getReplyuid() == 0) {
                mReplyCount++;
            }
        }
        mAdapter.notifyDataSetChanged();
        setReplyCount(mReplyCount);

    }

    @Override
    public int getNoteId() {
        return mNoteId;
    }


    private void thumb(int count) {
        if (count <= 0) {
            mBarNum2.setVisibility(View.GONE);
        } else {
            if (count > 99) {
                mBarNum2.setText("99+");
            } else {
                mBarNum2.setText(count + "");
            }
        }
    }

    private void setReplyCount(int count) {
        if (count <= 0) {
            mBarNum.setVisibility(View.GONE);
        } else {
            if (count > 99) {
                mBarNum.setText("99+");
            } else {
                mBarNum.setText(count + "");
            }
        }
    }


    @Override
    public void onThumbSuccess() {
        thumbed = true;
        ToastUtil.showShortToastCenter("点赞成功！");
        thumb(mThumbs + 1);
    }

    @Override
    public void onOnGetPictureSuccess() {
        L.e("响应获取帖子图片成功的事件");
    }

    @Override
    public void onGetCatInfoSuccess() {
        L.e("响应获取帖子内猫咪信息成功后的事件");
    }


    @OnClick({R.id.btn_comment, R.id.btn_like, R.id.btn_share, R.id.editText3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_comment: {
                if (!isAtComment) {
                    isAtComment = true;
                    //记录上次浏览位置
                    index = mListNotedetail.getFirstVisiblePosition();
                    L.e("index:" + index);
                    View v = mListNotedetail.getChildAt(0);
                    top = (v == null) ? 0 : v.getTop();
                    L.e("top:" + top);
                    mListNotedetail.smoothScrollToPosition(2);
                } else {
                    isAtComment = false;
                    L.e("index2:" + index);
                    L.e("top2:" + top);
                    mListNotedetail.setSelectionFromTop(index, top);
                }

            }
            break;
            case R.id.btn_like:
                if (!thumbed) {
                    mPresenter.thumb();
                }
                break;
            case R.id.btn_share:
                ToastUtil.showShortToastCenter("响应分享操作");
                break;
            case R.id.editText3: {
                if (!CommonUtil.isLogined()) {
                    Snackbar.make(findViewById(android.R.id.content), "您还未登录", Snackbar.LENGTH_INDEFINITE)
                            .setAction("登录", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(LoginActivity.class);
                                }
                            }).show();
                } else {
                    final FragmentTransaction mFragTransaction = getFragmentManager().beginTransaction();
                    final BottomCommentDialogFragment bottomCommentDialogFragment = BottomCommentDialogFragment.newInstance();
                    bottomCommentDialogFragment.setReplyListener(new BottomCommentDialogFragment.OnReplyListener() {
                        @Override
                        public void onSucess() {
                            Toast.makeText(mContext, "发表成功！", Toast.LENGTH_SHORT).show();
                            bottomCommentDialogFragment.dismiss();
                        }

                        @Override
                        public void onFailure(String failureMessage) {
                            Toast.makeText(mContext, "发表失败:" + failureMessage, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Toast.makeText(mContext, "发表过程出现错误:" + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                    Bundle bundle = new Bundle();
                    bundle.putInt("nid", getNoteId());
                    bottomCommentDialogFragment.setArguments(bundle);
                    bottomCommentDialogFragment.show(mFragTransaction, "111");
                }
            }
            break;

        }
    }


}
