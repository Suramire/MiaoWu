package com.suramire.miaowu.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseSwipeActivity;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.Constant;

import butterknife.Bind;

/**
 * Created by Suramire on 2017/11/25.
 */

public class ExtendedInformationActivity extends BaseSwipeActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.sp_sex)
    Spinner spSex;
    @Bind(R.id.sp_age)
    Spinner spAge;
    @Bind(R.id.sp_neutering)
    Spinner spNeutering;
    @Bind(R.id.sp_insecticide)
    Spinner spInsecticide;
    @Bind(R.id.sp_contact)
    Spinner spContact;
    @Bind(R.id.edt_contact)
    EditText edtContact;
    @Bind(R.id.edt_type)
    EditText edtType;
    @Bind(R.id.edt_conditions)
    EditText edtConditions;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0x11, 0, "完成").setIcon(R.drawable.ic_done_black_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0x11) {
//          领养要求不能为空
            String conditions = edtConditions.getText().toString().trim();
            String contact = edtContact.getText().toString().trim();
            String type = edtType.getText().toString().trim();
            if (TextUtils.isEmpty(conditions)) {
                CommonUtil.snackBar(this, "领养条件不能为空", "去完善", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });

            } else if (spContact.getSelectedItemPosition() == 0) {
                CommonUtil.snackBar(this, "请选择联系方式", "去完善", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            } else if (TextUtils.isEmpty(contact)) {
                CommonUtil.snackBar(this, "请填写联系方式", "去完善", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            } else {
                Catinfo catinfo = new Catinfo();
                catinfo.setAge(spAge.getSelectedItemPosition());
                catinfo.setSex(spSex.getSelectedItemPosition());
                catinfo.setContacttype(spContact.getSelectedItemPosition());
                catinfo.setNeutering(spNeutering.getSelectedItemPosition());
                catinfo.setInsecticide(spInsecticide.getSelectedItemPosition());
                catinfo.setType(type);
                catinfo.setContact(contact);
                catinfo.setConditions(conditions);
                Intent intent = new Intent();
                intent.putExtra("catinfo",catinfo);
                setResult(Constant.CODE_SUCCESS,intent);
                finish();
            }

        }

        return super.onOptionsItemSelected(item);
    }


}
