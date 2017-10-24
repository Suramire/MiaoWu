package com.suramire.miaowu.ui;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.squareup.picasso.Picasso;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;

import static com.suramire.miaowu.util.Constant.BASEURL;

/**
 * Created by Suramire on 2017/10/24.
 */

public class ProfileActivity extends BaseActivity {
    public static final int SUCCESS = 0x101 ;
    @Bind(R.id.toolbar4)
    Toolbar mToolbar4;
    @Bind(R.id.list_profile)
    ListView mListProfile;

    @Override
    protected String getTitleString() {
        return "个人中心";
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_profile;
    }

    @Override
    public void initView(View view) {
        setSupportActionBar(mToolbar4);
        View headerView = getLayoutInflater().inflate(R.layout.header_profile, null);
        ArrayList<String> list = new ArrayList<>();
        list.add(BASEURL+"upload/cat.jpg");
        list.add(BASEURL+"upload/cat1.jpg");
        final String icon = BASEURL+"upload/0000.png";
        mListProfile.addHeaderView(headerView);
        mListProfile.setAdapter(new CommonAdapter<String>(mContext, R.layout.item_list, list) {

            @Override
            public void onUpdate(BaseAdapterHelper helper, String item, int position) {

                Picasso.with(mContext)
                        .load(item)
                        .placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_loading_error)
                        .into((ImageView) helper.getView(R.id.imageView3));
                Picasso.with(mContext)
                        .load(icon)
                        .placeholder(R.drawable.default_icon)
                        .into((ImageView) helper.getView(R.id.imageView2));

                helper.setOnClickListener(R.id.cardview_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(ContentActivity.class);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem logout = menu.add(0, 10, 0, "logout");
        logout.setIcon(R.drawable.ic_close_black_24dp);
        logout.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==10){
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("提示")
                    .setMessage("是否注销登录")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setResult(SUCCESS);
                            finish();
                        }
                    })
                    .setNegativeButton("取消",null)
                    .setCancelable(true)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }
}
