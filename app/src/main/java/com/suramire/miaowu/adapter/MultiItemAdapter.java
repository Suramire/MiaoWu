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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.suramire.miaowu.R;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.Multi0;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.ui.HDPictureActivity;
import com.suramire.miaowu.ui.ProfileActivity;
import com.suramire.miaowu.ui.ReplyDetailActivity;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.L;
import com.suramire.miaowu.util.PicassoUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static com.suramire.miaowu.util.ApiConfig.BASNOTEPICEURL;
import static com.suramire.miaowu.util.ApiConfig.BASUSERPICEURL;

/**
 * Created by Suramire on 2017/11/7.
 * 多类型列表适配器
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
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_loading_error)
                    .into(imageView);
        }
    }


        public MultiItemAdapter(Context context , List list) {
        mList = list;
        mContext = context;
        mClasses =new Class[]{Multi0.class, Note.class, ArrayList.class, User.class, Catinfo.class};
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
            case 4:{
                convertView = getView4(position, convertView, parent);
            }break;
        }
        return convertView;
    }

    private View getView0(int position, View convertView, ViewGroup parent){
        ViewHolder0 mVH;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_reply, parent, false);
            mVH = new ViewHolder0();
            mVH.mtvNickname = convertView.findViewById(R.id.reply_user_nickname);
            mVH.mtvContent = convertView.findViewById(R.id.reply_content);
            mVH.mtvReplytime = convertView.findViewById(R.id.reply_date);
            mVH.mimgUserIcon = convertView.findViewById(R.id.reply_user_icon);
            mVH.mtvCount = convertView.findViewById(R.id.reply_count);
            mVH.mll_repleydetail = convertView.findViewById(R.id.ll_replydetail);
            convertView.setTag(mVH);
        }else{
            mVH = (ViewHolder0) convertView.getTag();
        }
        final Multi0 multi0 = (Multi0) mList.get(position);
        Reply reply = multi0.getReply();
        User user = multi0.getUser();
        mVH.mtvNickname.setText(user.getNickname());
        String icon = user.getIcon();
        if(icon!=null){
            PicassoUtil.show(BASUSERPICEURL+icon,mVH.mimgUserIcon);
        }
//        int count = multi.getCount()-1;
        mVH.mtvCount.setText("");

        mVH.mtvContent.setText(reply.getReplycontent());
        mVH.mtvReplytime.setText(CommonUtil.timeStampToDateString(reply.getReplytime()));
        //第一项添加标头
        if(isFirst){
            mVH.mtvTitle = convertView.findViewById(R.id.tv_title_reply);
            mVH.mtvTitle.setVisibility(View.VISIBLE);
            isFirst = false;
        }
        mVH.mll_repleydetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReplyDetailActivity.class);
                intent.putExtra("multi",  multi0);

                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

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
        mVH.mtvTitle.setText(note.getTitle());
        mVH.mtvContent.setText(note.getContent());
        mVH.mtvPublishTime.setText("发布时间："+CommonUtil.timeStampToDateString(note.getPublish()));
        mVH.mtvViewcount.setText("浏览次数:"+note.getViewcount()+"");
        return convertView;
    }



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
                .setOnBannerListener(new OnBannerListener() {

                    @Override
                    public void OnBannerClick(int position) {
                        Intent intent = new Intent(mContext, HDPictureActivity.class);
                        intent.putExtra("path", newItems.get(position));
                        mContext.startActivity(intent);
                    }
                })
                .start();
        return convertView;
    }

    private View getView3(int position, View convertView, ViewGroup parent){
        ViewHolder3 mVH;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_nt_profile, null, false);
            mVH = new ViewHolder3();
            mVH.mButtonFollow = convertView.findViewById(R.id.btn_nt_follow);
            mVH.mImgUser = convertView.findViewById(R.id.img_nt_profile);
            mVH.mTvNickname = convertView.findViewById(R.id.tv_nt_nickname);
            mVH.llProfile = convertView.findViewById(R.id.ll_profile);
            convertView.setTag(mVH);
        }else{
            mVH = (ViewHolder3) convertView.getTag();
        }
        final User user = (User) mList.get(position);
        mVH.mTvNickname.setText(user.getNickname());
        if(user.getId()==CommonUtil.getCurrentUid()){
            mVH.mButtonFollow.setVisibility(View.GONE);
        }else{
            mVH.mButtonFollow.setVisibility(View.VISIBLE);
        }
        mVH.mButtonFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "这里响应查看作者联系方式事件", Toast.LENGTH_SHORT).show();
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
            }
        });
        mVH.llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入个人详情页
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra("uid",user.getId());
                mContext.startActivity(intent);
            }
        });
        PicassoUtil.show(BASUSERPICEURL+user.getIcon(),mVH.mImgUser);

        return convertView;
    }

    private View getView4(int position, View convertView, ViewGroup parent){
        ViewHolder4 mVH;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_catinfo, null, false);
            mVH = new ViewHolder4(convertView);
            convertView.setTag(mVH);
        }else{
            mVH = (ViewHolder4) convertView.getTag();
        }
        Catinfo catinfo = (Catinfo) mList.get(position);

        mVH.tvAge.setText(catinfo.getAge()==0?"未知":catinfo.getAge().toString());
        Integer sex = catinfo.getSex();
        if(sex==2){
            mVH.tvSex.setText("母");
        }else if(sex==1){
            mVH.tvSex.setText("公");
        }else{
            mVH.tvSex.setText("未知");
        }
        mVH.tvType.setText(catinfo.getType());
        Integer neutering = catinfo.getNeutering();
        if(neutering==1){
            mVH.tvNeutering.setText("是");
        }else if(neutering ==2){
            mVH.tvNeutering.setText("否");
        }else{
            mVH.tvNeutering.setText("未知");
        }
        Integer insecticide = catinfo.getInsecticide();
        if(insecticide==1){
            mVH.tvInsecticide.setText("是");
        }else if(insecticide ==2){
            mVH.tvInsecticide.setText("否");
        }else{
            mVH.tvInsecticide.setText("未知");
        }

        Integer contacttype = catinfo.getContacttype();
        String result = null;
        switch (contacttype){
            case 0:result = "未知";break;
            case 1:result = "电话";break;
            case 2:result = "QQ";break;
            case 3:result = "微信";break;
            case 4:result = "邮箱";break;
        }
        mVH.tvContacttype.setText(result);
        mVH.tvContact.setText(catinfo.getContact());
        mVH.tvConditions.setText(catinfo.getConditions());
        return convertView;
    }


    class ViewHolder0 {
        TextView mtvNickname,mtvContent,mtvCount;
        TextView mtvTitle,mtvReplytime;
        ImageView mimgUserIcon;
        LinearLayout mll_repleydetail;
    }

    class ViewHolder1 {
        TextView mtvTitle,mtvContent,
            mtvPublishTime,mtvViewcount;
    }

    class ViewHolder2 {
        Banner mBanner;
    }

    class ViewHolder3 {
        LinearLayout llProfile;
        ImageView mImgUser;
        TextView mTvNickname;
        Button mButtonFollow;
    }


    class ViewHolder4 {
        TextView tvSex;
        TextView tvAge;
        TextView tvNeutering;
        TextView tvInsecticide;
        TextView tvType;
        TextView tvContacttype;
        TextView tvContact;
        TextView tvConditions;

        public ViewHolder4(View view) {
            tvSex = (TextView) view.findViewById(R.id.tv_sex);
            tvAge = (TextView) view.findViewById(R.id.tv_age);
            tvNeutering = (TextView) view.findViewById(R.id.tv_neutering);
            tvInsecticide = (TextView) view.findViewById(R.id.tv_insecticide);
            tvType = (TextView) view.findViewById(R.id.tv_type);
            tvContacttype = (TextView) view.findViewById(R.id.tv_contacttype);
            tvContact = (TextView) view.findViewById(R.id.tv_contact);
            tvConditions = (TextView) view.findViewById(R.id.tv_conditions);
        }
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
