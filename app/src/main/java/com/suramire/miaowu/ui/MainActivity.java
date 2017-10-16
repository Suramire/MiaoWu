package com.suramire.miaowu.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Toast;
import com.suramire.miaowu.R;
import com.suramire.miaowu.util.CommonUtil;
import java.util.HashMap;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

import static com.suramire.miaowu.R.id.editText;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(editText)
    TextInputEditText mEditText;
    @Bind(R.id.tl_url)
    TextInputLayout mTlUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        mTlUrl.setErrorEnabled(false);
        String url = mEditText.getText().toString().trim();
        if(TextUtils.isEmpty(url)){
            CommonUtil.showError(mTlUrl, "请输入要分享的URL地址！");
        }else{
            OnekeyShare onekeyShare = new OnekeyShare();
            onekeyShare.setImagePath("/storage/sdcard1/cat.jpg");
            onekeyShare.setTitle("title here");
            onekeyShare.setText("text here");
            onekeyShare.setUrl(url);
            onekeyShare.setCallback(new PlatformActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    Toast.makeText(MainActivity.this, "onComplete", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {
                    Toast.makeText(MainActivity.this, "onError", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
                }
            });
            onekeyShare.show(MainActivity.this);
        }
    }
}
