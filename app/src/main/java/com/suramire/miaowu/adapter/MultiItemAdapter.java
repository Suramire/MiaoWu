package com.suramire.miaowu.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.suramire.miaowu.R;
import com.suramire.miaowu.bean.M;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.ui.UserActivity;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.GsonUtil;
import com.suramire.miaowu.util.PicassoUtil;
import com.suramire.miaowu.util.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static com.suramire.miaowu.util.ApiConfig.BASNOTEPICEURL;
import static com.suramire.miaowu.util.ApiConfig.BASUSERPICEURL;

/**
 * Created by Suramire on 2017/11/7.
 * 多类型列表适配器 帖子详情页
 */

public class MultiItemAdapter extends BaseAdapter {

    private final Class[] mClasses;
    private List  mList;
    private Context mContext;
    private boolean isFirst = true;


    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context)
                    .load(path)
                    .placeholder(R.mipmap.ic_loading)

                    .error(R.mipmap.ic_loading_error)
                    .into(imageView);
        }
    }


        public MultiItemAdapter(Context context , List list) {
        mList = list;
        mContext = context;
        mClasses =new Class[]{M.class, Note.class, ArrayList.class, User.class};
    }

    @Override
    public int getItemViewType(int position) {
        Object item = getItem(position);
        for(int i=0,size=mClasses.length;i<size;i++){
            if(item.getClass() == mClasses[i]){
                return i;
            }
        }
        return  0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType){
            case 0:{
                convertView = getView0(position, convertView, parent);
            }break;
            case 1:{
                convertView = getView1(position, convertView, parent);
            }break;
            case 2:{
                convertView = getView2(position, convertView, parent);
            }break;
            case 3:{
                convertView = getView3(position, convertView, parent);
            }break;
        }
        return convertView;
    }

    //帖子回复
    private View getView0(int position, View convertView, ViewGroup parent){
        ViewHolder0 mVH;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_reply, parent, false);
            mVH = new ViewHolder0();
            mVH.mtvNickname = convertView.findViewById(R.id.reply_user_nickname);
            mVH.mtvContent = convertView.findViewById(R.id.reply_content);
            mVH.mtvReplytime = convertView.findViewById(R.id.reply_date);
            mVH.mimgUserIcon = convertView.findViewById(R.id.reply_user_icon);
            mVH.ll_replyuser = convertView.findViewById(R.id.ll_reply_user);
            convertView.setTag(mVH);
        }else{
            mVH = (ViewHolder0) convertView.getTag();
        }
        final M m = (M) mList.get(position);
        Reply reply = (Reply) GsonUtil.jsonToObject(m.getStringx(),Reply.class);
        final User user = (User) GsonUtil.jsonToObject(m.getStringy(),User.class);
        //显示用户昵称与头像
        mVH.mtvNickname.setText(user.getNickname());
        String icon = user.getIcon();
        PicassoUtil.showIcon(icon==null?null:BASUSERPICEURL+icon,mVH.mimgUserIcon);

//        int count = multi.getCount()-1;
        mVH.mtvContent.setText(reply.getReplycontent());
        mVH.mtvReplytime.setText(CommonUtil.timeStampToDateString(reply.getReplytime()));
        //评论第一项添加标头
        if(isFirst){
            mVH.mtvTitle = convertView.findViewById(R.id.tv_title_reply);
            mVH.mtvTitle.setVisibility(View.VISIBLE);
            int replyCount = m.getIntx();
            //在标头显示评论总数
            if(replyCount >0){
                mVH.mtvTitle.setText("帖子回复 ("+ replyCount +")");
            }
            isFirst = false;
        }
        mVH.ll_replyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserActivity.class);
                intent.putExtra("uid",user.getId());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    //帖子内容
    private View getView1(int position, View convertView, ViewGroup parent){
        ViewHolder1 mVH;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_nt_content, parent, false);
            mVH = new ViewHolder1();
            mVH.mtvTitle = convertView.findViewById(R.id.notedetail_title);
            mVH.mtvContent = convertView.findViewById(R.id.notedetail_content);
            mVH.mtvPublishTime = convertView.findViewById(R.id.nt_publishtime);
            mVH.mtvViewcount = convertView.findViewById(R.id.nt_viewcount);
            convertView.setTag(mVH);
        }else{
            mVH = (ViewHolder1) convertView.getTag();
        }
        Note note = (Note) mList.get(position);

        String titleString;
        if(note.getVerified()==3){
            titleString = "[已锁定] "+note.getTitle();
        }else{
            titleString = note.getTitle();
        }
        mVH.mtvTitle.setText(titleString);
        mVH.mtvContent.setText(note.getContent());
        mVH.mtvPublishTime.setText("发布时间："+CommonUtil.timeStampToDateString(note.getPublish()));
        mVH.mtvViewcount.setText("浏览次数:"+note.getViewcount()+"");
        return convertView;
    }


    //帖子配图轮播
    private View getView2(int position, View convertView, ViewGroup parent){
        ViewHolder2 mVH;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_nt_banner, null, false);
            mVH = new ViewHolder2();
            mVH.mBanner = convertView.findViewById(R.id.banner);
            convertView.setTag(mVH);
        }else{
            mVH = (ViewHolder2) convertView.getTag();
        }
        List<String> items = (List<String>) getItem(position);
        final List<String> newItems = new ArrayList<>();
        for (String s:
             items) {
            newItems.add(BASNOTEPICEURL+s);
        }
        mVH.mBanner.setImageLoader(new GlideImageLoader())
                .setImages(newItems)
                .isAutoPlay(false)
                .setBannerStyle(BannerConfig.NUM_INDICATOR)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start();
        return convertView;
    }

    //作者信息区
    private View getView3(int position, View convertView, ViewGroup parent){
        ViewHolder3 mVH;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_nt_profile, null, false);
            mVH = new ViewHolder3();
            mVH.mButtonFollow = convertView.findViewById(R.id.btn_nt_follow);
//            mVH.mButtonApply = convertView.findViewById(R.id.btn_apply);
            mVH.mImgUser = convertView.findViewById(R.id.img_nt_profile);
            mVH.mTvNickname = convertView.findViewById(R.id.tv_nt_nickname);
            mVH.llProfile = convertView.findViewById(R.id.ll_profile);
            convertView.setTag(mVH);
        }else{
            mVH = (ViewHolder3) convertView.getTag();
        }
        final User user = (User) mList.get(position);
        mVH.mTvNickname.setText(user.getNickname());
        //当前登录的用户为帖子作者时 不显示联系作者按钮
        if(user.getId()==CommonUtil.getCurrentUid()){
            mVH.mButtonFollow.setVisibility(View.GONE);
        }else{
            mVH.mButtonFollow.setVisibility(View.VISIBLE);
        }
        mVH.mButtonFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CommonUtil.isLogined()){
                    String contacttype = "";
                    switch (user.getContacttype()){
                        case 1:contacttype="电话";break;
                        case 2:contacttype="QQ";break;
                        case 3:contacttype="微信";break;
                        case 4:contacttype="邮箱";break;
                    }
                    CommonUtil.showDialog(mContext,"联系方式", "联系方式:" + contacttype + " " + user.getContact(), "复制",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData mClipData = ClipData.newPlainText("contact", user.getContact());
                                    // 将ClipData内容放到系统剪贴板里。
                                    cm.setPrimaryClip(mClipData);
                                    Toast.makeText(mContext, "复制成功！", Toast.LENGTH_SHORT).show();
                                }
                            },"关闭",null);
                }else{
                    ToastUtil.showLongToastCenter("请先登录");
                }

            }
        });
        mVH.llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入个人详情页
                Intent intent = new Intent(mContext, UserActivity.class);
                intent.putExtra("uid",user.getId());
                mContext.startActivity(intent);
            }
        });
        String icon = user.getIcon();
        PicassoUtil.showIcon(icon==null?null:BASUSERPICEURL+icon,mVH.mImgUser);
        return convertView;
    }



    class ViewHolder0 {
        TextView mtvNickname,mtvContent;
        TextView mtvTitle,mtvReplytime;
        ImageView mimgUserIcon;
        LinearLayout ll_replyuser;
    }

    class ViewHolder1 {
        TextView mtvTitle,mtvContent,
            mtvPublishTime,mtvViewcount;
    }

    class ViewHolder2 {
        Banner mBanner;
    }

    class ViewHolder3 {
        RelativeLayout llProfile;
        ImageView mImgUser;
        TextView mTvNickname;
        Button mButtonFollow;
    }



    @Override
    public int getViewTypeCount() {
        return mClasses.length;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
