package com.suramire.miaowu.ui;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2017/10/17.
 */

public class ContentActivity extends BaseActivity {
    @Bind(R.id.webview_content)
    WebView mWebviewContent;
    @Bind(R.id.toolbar)
    Toolbar mToolbar2;
    @Bind(R.id.editText3)
    EditText mEditText3;
    @Bind(R.id.ll_bottom)
    LinearLayout mLlBottom;
    @Bind(R.id.edittext_comment)
    EditText mEdittextComment;
    @Bind(R.id.button2)
    Button mButton2;
    @Bind(R.id.ll_popup)
    LinearLayout mLlPopup;
    @Bind(R.id.btn_comment)
    ImageView mBtnComment;
    @Bind(R.id.btn_like)
    ImageView mBtnLike;
    @Bind(R.id.btn_share)
    ImageView mBtnShare;


    @Override
    public int bindLayout() {
        return R.layout.activity_content;
    }

    @Override
    public void initView(View view) {
        setSupportActionBar(mToolbar2);
        int noteId = getIntent().getIntExtra("noteId", 0);
        Toast.makeText(mContext, "noteId:" + noteId, Toast.LENGTH_SHORT).show();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mWebviewContent.loadUrl("http://192.168.1.101:8080/Miaowu/index.jsp");

    }




    @Override
    protected String getTitleString() {
        return "帖子详情";
    }


    @OnClick({R.id.editText3,R.id.btn_comment, R.id.btn_like, R.id.btn_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.editText3:{
                if (mLlBottom.getVisibility() != View.GONE) {
                    mLlBottom.setVisibility(View.GONE);
                }
                mLlPopup.setVisibility(View.VISIBLE);
            }break;
            case R.id.btn_comment:
                startActivity(TestActivity.class);
                break;
            case R.id.btn_like:
                break;
            case R.id.btn_share:
                break;
        }
    }

}
