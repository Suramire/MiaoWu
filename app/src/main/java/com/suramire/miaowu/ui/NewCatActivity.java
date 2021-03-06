package com.suramire.miaowu.ui;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.M;
import com.suramire.miaowu.contract.CatContract;
import com.suramire.miaowu.presenter.CatPresenter;
import com.suramire.miaowu.util.ApiConfig;
import com.suramire.miaowu.util.CommonUtil;
import com.suramire.miaowu.util.PicassoUtil;
import com.suramire.miaowu.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 新建猫咪页
 */

public class NewCatActivity extends BaseActivity<CatPresenter> implements CatContract.View {
    @Bind(R.id.sp_sex)
    Spinner spSex;
    @Bind(R.id.sp_age)
    Spinner spAge;
    @Bind(R.id.sp_neutering)
    Spinner spNeutering;
    @Bind(R.id.sp_insecticide)
    Spinner spInsecticide;
    @Bind(R.id.edt_type)
    EditText edtType;
    @Bind(R.id.edt_conditions)
    EditText edtConditions;
    @Bind(R.id.gridview_picture)
    GridView gridviewPicture;
    @Bind(R.id.hscroll)
    HorizontalScrollView hscroll;
    private int cid;
    private ArrayList<String> mPhotos;
    private boolean flag;


    @Override
    public int bindLayout() {
        return R.layout.activity_newcat;
    }

    @Override
    public void createPresenter() {
        mPresenter = new CatPresenter();
    }

    @Override
    public void initView() {
        setTitle("详细信息");
        setRightText("添加");
        progressDialog.setMessage("请稍候……");
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
            String type = edtType.getText().toString().trim();
            if (TextUtils.isEmpty(conditions)) {
                CommonUtil.snackBar(this, "领养条件不能为空", "去完善", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });

            } else if(mPhotos.size()<1) {
                CommonUtil.snackBar(this, "请选择猫咪配图", "去完善", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            } else{
                Catinfo catinfo = new Catinfo();
                catinfo.setAge(spAge.getSelectedItemPosition());
                catinfo.setSex(spSex.getSelectedItemPosition());
                catinfo.setNeutering(spNeutering.getSelectedItemPosition());
                catinfo.setInsecticide(spInsecticide.getSelectedItemPosition());
                catinfo.setType(type);
                catinfo.setConditions(conditions);
                Intent intent = new Intent();
                intent.putExtra("catinfo", catinfo);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }

        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onSuccess(Object data) {

    }



    @OnClick({R.id.tv_choosepic,R.id.toolbar_text_right})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_choosepic:{
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setSelectLimit(9);    //选中数量限制
                Intent intent = new Intent(mContext, ImageGridActivity.class);
                startActivityForResult(intent, ApiConfig.REQUESTCODE_PHOTO);
            }break;
            case R.id.toolbar_text_right:{
                String conditions = edtConditions.getText().toString().trim();
                if (TextUtils.isEmpty(conditions)) {
                    CommonUtil.snackBar(NewCatActivity.this, "领养条件不能为空", "去完善", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }else if(mPhotos.size()==0){
                    ToastUtil.showLongToastCenter("请选择配图文件");
                }else{
                    if (flag) {
                        ToastUtil.showLongToastCenter("请勿重复添加");
                    }else{
                        mPresenter.addCat();

                    }
                }
            }break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS && requestCode == ApiConfig.REQUESTCODE_PHOTO) {
            if (data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if(images!=null){
                    mPhotos = new ArrayList<>();
                    hscroll.setVisibility(View.VISIBLE);
                    gridviewPicture.setAdapter(new CommonAdapter<ImageItem>(this, R.layout.item_picture, images) {
                        @Override
                        public void onUpdate(BaseAdapterHelper helper, ImageItem item, int position) {
                            PicassoUtil.show(new File(item.path),(ImageView) helper.getView(R.id.imageView15));
                        }
                    });
                    for (int i = 0; i < images.size(); i++) {
                        mPhotos.add(images.get(i).path);
                    }

                }
            }
        }
    }

    @Override
    public Catinfo getCatInfo() {
        String conditions = edtConditions.getText().toString().trim();
        String type = edtType.getText().toString().trim();
        Catinfo catinfo = new Catinfo();
        catinfo.setAge(spAge.getSelectedItemPosition());
        catinfo.setSex(spSex.getSelectedItemPosition());
        catinfo.setNeutering(spNeutering.getSelectedItemPosition());
        catinfo.setInsecticide(spInsecticide.getSelectedItemPosition());
        catinfo.setType(type);
        catinfo.setConditions(conditions);
        catinfo.setAdddate(new Date());
        catinfo.setIsAdopted(0);
        return catinfo;
    }

    @Override
    public int getCatId() {
        return cid;
    }

    @Override
    public int getUid() {
        return 0;
    }

    @Override
    public void onAddCatSuccess(int cid) {
        this.cid = cid;
//        ToastUtil.showLongToastCenter("猫咪信息添加成功");
        //添加猫咪成功后先保存图片文件名到数据库
        mPresenter.uploadPictuePaths();
        progressDialog.setMessage("正在上传图片，请稍候……");
        progressDialog.setCancelable(false);
        flag = true;
    }

    @Override
    public void onApplyCatSuccess() {
    }


    @Override
    public List<String> getStringPaths() {
        return mPhotos;
    }

    @Override
    public void onUploadCatPicturesSuccess() {
        ToastUtil.showLongToastCenter("猫咪添加成功");
    }

    @Override
    public void onGetAllPicturesSuccess(List<String> paths) {

    }

    @Override
    public void onListAppliedCatSuccess(List<M> catinfo) {

    }
}
