package com.suramire.miaowu.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.suramire.miaowu.wiget.MyToolbar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2018/2/1.
 * 个人详情页
 */

public class ProfileDetailActivity extends BaseActivity<ProfilePresenter> implements ProfileContract.View {

    @Bind(R.id.toolbar_image_left)
    ImageView toolbarImageLeft;
    @Bind(R.id.toolbar_text_center)
    TextView toolbarTextCenter;
    @Bind(R.id.toolbar_text_right)
    TextView toolbarTextRight;
    @Bind(R.id.toolbar)
    MyToolbar toolbar;
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
    private ProgressDialog progressDialog;
    private int uid;

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void cancelLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void onSuccess(Object data) {
        if (data != null) {
            User user = (User) data;
            showData(user);
        }
    }

    private void showData(User user) {
        PicassoUtil.show(ApiConfig.BASUSERPICEURL + user.getIcon(), imgIcon);
        edtUsername.setText(user.getNickname());
        edtBirthday.setText(CommonUtil.dateToString(user.getBirthday()));
        edtPhone.setText(user.getPhonenumber());
        //若用户有留联系方式则显示
        spinner.setSelection(user.getContacttype());
        edtContact.setText(user.getContact());
        mUser = user;
        SPUtils.put("hascontact",user.getContacttype());

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
        toolbar.setTitle("个人信息");
        toolbarImageLeft.setImageResource(R.drawable.ic_arrow_back_black);
        toolbarTextRight.setText("保存");
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("请稍候……");
        uid = getIntent().getIntExtra("uid", 0);
        mPresenter.getProfile();
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
        ToastUtil.showShortToastCenter("用户信息修改成功");
        showData(user);
    }



    @OnClick({R.id.toolbar_image_left, R.id.toolbar_text_right, R.id.img_icon, R.id.edt_birthday,
            R.id.btn_loginout,R.id.tv_modify_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_image_left:
                // TODO: 2018/2/1 信息已修改 提示是否保存修改
                finish();
                break;
            case R.id.toolbar_text_right:
                verifyUser();
                mPresenter.updateProfile();
                break;
            case R.id.img_icon:
                // TODO: 2018/2/1 选择头像
                break;
            case R.id.edt_birthday:
                // TODO: 2018/2/1 日期选择
                break;
            case R.id.btn_loginout:
                CommonUtil.loginOut();
                ToastUtil.showShortToastCenter("注销成功");
                setResult(ApiConfig.RESULTCODE);
                finish();
                // TODO: 2018/2/1 上一个页面刷新
                break;
            case R.id.tv_modify_password:
                // TODO: 2018/2/1 修改密码
                Bundle bundle = new Bundle();
                bundle.putInt("uid",getUid());
                startActivity(ModifyPasswordActivity.class,bundle);
                break;
        }
    }

    private void verifyUser() {
        String birthday = edtBirthday.getText().toString().trim();
//        String phone = edtPhone.getText().toString().trim();
        // TODO: 2018/2/1 修改手机需先验证
        int selectedItemPosition = spinner.getSelectedItemPosition();
        String contact = edtContact.getText().toString().trim();
        if(TextUtils.isEmpty(birthday)){
            ToastUtil.showShortToastCenter("请填写生日信息");
            return;
        }
        if(selectedItemPosition!=0 && TextUtils.isEmpty(contact)){
            ToastUtil.showShortToastCenter("若选择了联系方式请填写完整");
            return;
        }

//        mUser.setBirthday();
        mUser.setContacttype(selectedItemPosition);
        mUser.setContact(contact);

    }
}
