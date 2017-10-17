package com.suramire.miaowu.ui;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by Suramire on 2017/10/17.
 */

public class ContentActivity extends BaseActivity {
    @Bind(R.id.webview_content)
    WebView mWebviewContent;



    @Override
    public int bindLayout() {
        return R.layout.activity_content;
    }

    @Override
    public void initView(View view) {
        mWebviewContent.loadUrl("http://192.168.1.101:8080/Miaowu/index.jsp");
    }

    @Override
    public void doBusiness(Context mContext) {

    }

}
