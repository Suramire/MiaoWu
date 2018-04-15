package com.suramire.miaowu.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseFragment;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.UserContract;
import com.suramire.miaowu.presenter.UserPresenter;
import com.suramire.miaowu.ui.AdoptHistoryActivity;
import com.suramire.miaowu.ui.ApplyListActivity;
import com.suramire.miaowu.ui.NewCatActivity;
import com.suramire.miaowu.ui.FansListActivity;
import com.suramire.miaowu.ui.LoginActivity;
import com.suramire.miaowu.ui.NoteListActivity;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.PicassoUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Suramire on 2018/1/25.
 */

public class UserFragment extends BaseFragment<UserPresenter> implements UserContract.View {


    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.ll_notlogin)
    LinearLayout llNotlogin;
    @Bind(R.id.img_icon)
    RoundedImageView imgIcon;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_follow_count)
    TextView tvFollowCount;
    @Bind(R.id.ll_followlist)
    LinearLayout llFollowlist;
    @Bind(R.id.tv_follower_count)
    TextView tvFollowerCount;
    @Bind(R.id.tv_note_count)
    TextView tvNoteCount;
    @Bind(R.id.ll_followerlist)
    LinearLayout llFollowerlist;
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;
    @Bind(R.id.ll_mynote)
    LinearLayout llMycourse;
    @Bind(R.id.ll_login)
    LinearLayout llLogin;
    @Bind(R.id.ll_verify)
    LinearLayout llVerify;
    @Bind(R.id.ll_verifycat)
    LinearLayout llVerifycat;
    @Bind(R.id.ll_addcat)
    LinearLayout llAddCat;

    private ProgressDialog mProgressDialog;
    private Integer uid;

    public interface OnUserListener{
        void onGetUserSuccess(User user);
    }
    private OnUserListener listener;

    public void setListener(OnUserListener listener) {
        this.listener = listener;
    }

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void cancelLoading() {
        mProgressDialog.dismiss();

    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void createPresenter() {
        mPresenter = new UserPresenter();
    }

    @Override
    public void initView() {
        setRequireFresh(true);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("请稍候……");
        if (CommonUtil.isLogined()) {
            llLogin.setVisibility(View.VISIBLE);
            llNotlogin.setVisibility(View.GONE);
            mPresenter.getUserInfo();
        } else {
            llLogin.setVisibility(View.GONE);
            llNotlogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_user;
    }


    @OnClick({R.id.btn_login, R.id.img_icon, R.id.ll_followlist, R.id.ll_followerlist,
            R.id.ll_mynote, R.id.ll_note2,R.id.ll_verify,R.id.ll_adopt,R.id.ll_verifycat,
            R.id.ll_addcat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                getActivity().startActivityForResult(new Intent(getActivity(), LoginActivity.class), ApiConfig.LOGINREQUESTCODE);
                break;
            case R.id.img_icon:
                break;
            case R.id.ll_followlist:
                //跳转到关注列表
                Intent intent1 = new Intent(mContext, FansListActivity.class);
                intent1.putExtra("index", 0);
                intent1.putExtra("uid", uid);
                startActivity(intent1);
                break;
            case R.id.ll_followerlist:
                //跳转到粉丝列表
                Intent intent2 = new Intent(mContext, FansListActivity.class);
                intent2.putExtra("index", 1);
                intent2.putExtra("uid", uid);
                startActivity(intent2);
                break;
            case R.id.ll_note2:
            case R.id.ll_mynote:
                Intent intent = new Intent(mContext, NoteListActivity.class);
                intent.putExtra("uid", getUid());
                startActivity(intent);
                break;
            case R.id.ll_verify:
                // 帖子审核页面
                Intent intent0 = new Intent(mContext, NoteListActivity.class);
                intent0.putExtra("uid", -1);
                startActivity(intent0);
                break;
            case R.id.ll_adopt:
                // 帖子审核页面
                Intent intent3 = new Intent(mContext, AdoptHistoryActivity.class);
                intent3.putExtra("uid", getUid());
                startActivity(intent3);
                break;
            case R.id.ll_verifycat:
                startActivity(ApplyListActivity.class);
                break;

            case R.id.ll_addcat:
                startActivity(NewCatActivity.class);
               break;
        }
    }


    @Override
    public int getUid() {
        return CommonUtil.getCurrentUid();
    }

    @Override
    public void onGetInfoSuccess(User userinfo) {
        if(listener!=null){
            listener.onGetUserSuccess(userinfo);
        }
        PicassoUtil.show(ApiConfig.BASUSERPICEURL + userinfo.getIcon(), imgIcon);
        tvUsername.setText(userinfo.getNickname());
        uid = userinfo.getId();
        //登录用户为管理员时显示审核帖子选项
        int visibility = userinfo.getRole()==1?View.VISIBLE:View.GONE;
        llVerify.setVisibility(visibility);
        llAddCat.setVisibility(visibility);
        llVerifycat.setVisibility(visibility);



    }

    @Override
    public void onGetUserFollowCountSuccess(int count) {
        tvFollowCount.setText(String.valueOf(count));

    }

    @Override
    public void onGetUserFollowerCountSuccess(int count) {
        tvFollowerCount.setText(String.valueOf(count));
    }

    @Override
    public void onGetUserNoteCountSuccess(int count) {
        tvNoteCount.setText(String.valueOf(count));
    }

    @Override
    public void onGetRelationshipSuccess(int type) {

    }


}
