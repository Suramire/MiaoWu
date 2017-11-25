package com.suramire.miaowu.ui;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseSwipeActivity;

import butterknife.Bind;

/**
 * Created by Suramire on 2017/11/25.
 */

public class ExtendedInformationActivity extends BaseSwipeActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.spinner)
    Spinner mSpinner;

    @Override
    protected String getTitleString() {
        return "详细信息";
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_publish_form;
    }

    @Override
    public void initView(View view) {
        setSupportActionBar(mToolbar);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(mContext, "position:" + 0, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0x11, 0, "完成").setIcon(R.drawable.ic_done_black_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0x11) {
            Toast.makeText(mContext, "响应完成事件", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


}
