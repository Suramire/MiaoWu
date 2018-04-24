package com.suramire.miaowu.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.makeramen.roundedimageview.RoundedImageView;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.ProfileContract;
import com.suramire.miaowu.presenter.ProfilePresenter;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.PicassoUtil;
import com.suramire.miaowu.util.SPUtils;
import com.suramire.miaowu.util.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

import static com.suramire.miaowu.util.ApiConfig.BASUSERPICEURL;

/**
 * Created by Suramire on 2018/2/1.
 * 个人详情页
 */

public class ProfileDetailActivity extends BaseActivity<ProfilePresenter> implements ProfileContract.View {

    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.img_icon)
    RoundedImageView imgIcon;
    @Bind(R.id.edt_username)
    EditText edtUsername;
    @Bind(R.id.edt_birthday)
    EditText edtBirthday;
    @Bind(R.id.edt_address)
    EditText edtAddress;
    @Bind(R.id.edt_phone)
    EditText edtPhone;
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.edt_contact)
    EditText edtContact;
    private User mUser;
    private int uid;
    private String avaterPath;
    int year_begin;
    int month_begin;
    int day_begin;
    long time_begin;
    private Calendar calendar;


    @Override
    public void onSuccess(Object data) {
        if (data != null) {
            User user = (User) data;
            showData(user);
        }
    }

    private void showData(User user) {
        String icon = user.getIcon();
        PicassoUtil.showIcon(icon==null?null:BASUSERPICEURL+icon,(ImageView) imgIcon);
        edtUsername.setText(user.getNickname());
        Date birthday1 = user.getBirthday();
        edtBirthday.setText(CommonUtil.dateToString(birthday1));
        edtPhone.setText(user.getPhonenumber());
        //若用户有留联系方式则显示
        spinner.setSelection(user.getContacttype());
        edtContact.setText(user.getContact());
        edtAddress.setText(user.getAddress());
        mUser = user;
        SPUtils.put("hascontact",user.getContacttype());
        String birthday = birthday1.toString().trim();
        if(!TextUtils.isEmpty(birthday)){
            calendar.setTime(birthday1);
            year_begin = calendar.get(Calendar.YEAR);//获取当前年份
            month_begin = calendar.get(Calendar.MONTH);//获取当前月份
            day_begin = calendar.get(Calendar.DAY_OF_MONTH);//获取当前天数
        }
    }


    @Override
    public int bindLayout() {
        return R.layout.activity_profile;
    }

    @Override
    public void createPresenter() {
        mPresenter = new ProfilePresenter();
    }

    @Override
    public void initView() {
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());//设置日历对象
        year_begin = calendar.get(Calendar.YEAR);//获取当前年份
        month_begin = calendar.get(Calendar.MONTH);//获取当前月份
        day_begin = calendar.get(Calendar.DAY_OF_MONTH);//获取当前天数
        setTitle("个人信息");
        setRightText("保存");
        progressDialog.setMessage("请稍候……");
        uid = getIntent().getIntExtra("uid", 0);
        mPresenter.getProfile();
        String[] stringArray = getResources().getStringArray(R.array.contacts);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stringArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                //showPrice(position);
                TextView tv = (TextView)view;
                tv.setTextColor(getResources().getColor(R.color.verydarkgray));    //设置颜色
                tv.setTextSize(16.0f);    //设置大小
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){}
        });
        spinner.setAdapter(adapter);
    }


    @Override
    public int getUid() {
        return uid;
    }

    @Override
    public User getUser() {
        return mUser;
    }

    @Override
    public void onUpdateSuccess(User user) {
        ToastUtil.showLongToastCenter("用户信息修改成功");
        showData(user);
    }

    @Override
    public String getAvaterPath() {
        return avaterPath;
    }

    @Override
    public void onUpdateAvaterSuccess() {
        ToastUtil.showLongToastCenter("更新头像成功");
        mPresenter.getProfile();
    }


    @OnClick({R.id.toolbar_text_right, R.id.img_icon, R.id.edt_birthday,
            R.id.btn_loginout,R.id.tv_modify_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_text_right:
                verifyUser();
                mPresenter.updateProfile();
                break;
            case R.id.img_icon:
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setSelectLimit(1);    //选中数量限制
                Intent intent = new Intent(mContext, ImageGridActivity.class);
                startActivityForResult(intent,ApiConfig.REQUESTCODE_PHOTO);
                break;
            case R.id.edt_birthday:
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        edtBirthday.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                        calendar.set(year, monthOfYear, dayOfMonth);
                        time_begin = calendar.getTimeInMillis();
                    }
                }, year_begin,month_begin,day_begin);
                datePickerDialog.show();

                break;
            case R.id.btn_loginout:
                CommonUtil.loginOut();
                ToastUtil.showLongToastCenter("注销成功");
                setResult(ApiConfig.RESULTCODE);
                finish();
                break;
            case R.id.tv_modify_password:
                Bundle bundle = new Bundle();
                bundle.putInt("uid",getUid());
                startActivity(PasswordActivity.class,bundle);
                break;
        }
    }

    private void verifyUser() {
        String birthday = edtBirthday.getText().toString().trim();
//        String phone = edtPhone.getText().toString().trim();
        int selectedItemPosition = spinner.getSelectedItemPosition();
        String contact = edtContact.getText().toString().trim();
        if(TextUtils.isEmpty(birthday)){
            ToastUtil.showLongToastCenter("请填写生日信息");
            return;
        }else{
            mUser.setBirthday(new Date(calendar.getTimeInMillis()));
        }
        if(selectedItemPosition!=0 && TextUtils.isEmpty(contact)){
            ToastUtil.showLongToastCenter("若选择了联系方式请填写完整");
            return;
        }
        mUser.setAddress(edtAddress.getText().toString().trim());
        mUser.setContacttype(selectedItemPosition);
        mUser.setContact(contact);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS && requestCode == ApiConfig.REQUESTCODE_PHOTO) {
            if (data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if(images!=null){
                    avaterPath = images.get(0).path;
                    mPresenter.updateAvater();
                }
            }
        }
    }
}
