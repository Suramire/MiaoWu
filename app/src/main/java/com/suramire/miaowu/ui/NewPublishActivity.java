package com.suramire.miaowu.ui;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by Suramire on 2017/10/26.
 */

public class NewPublishActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected String getTitleString() {
        return "发表帖子";
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_newpublish;
    }

    @Override
    public void initView(View view) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0x12, 0, "发表").setIcon(R.drawable.ic_send_black_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

}
