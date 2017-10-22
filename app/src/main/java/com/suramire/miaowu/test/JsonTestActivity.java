package com.suramire.miaowu.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.suramire.miaowu.R;
import com.suramire.miaowu.base.BaseActivity;
import com.suramire.miaowu.util.FileUtil;
import com.suramire.miaowu.util.HTTPUtil;
import com.suramire.miaowu.util.L;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Suramire on 2017/10/21.
 */

public class JsonTestActivity extends BaseActivity {
    @Override
    protected String getTitleString() {
        return "JSON";
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_json_test;
    }

    @Override
    public void initView(View view) {
//        L.e("hhh");
//        HTTPUtil.getCall("http://192.168.1.101:8080/Miaowu/userAction", new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                L.e("onFailure");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                L.e("onResponse");
//                L.d("AAA", "onResponse");
//                String string = response.body().string();
//                List<User> users = GsonUtil.jsonToList(string, User.class);
//                L.d("AAA", string);
//                for (User u: users) {
//                    Log.d("AAA", u.getNickname());
//                }
//            }
//        });
//        HashMap<String, String> map = new HashMap<>();
//        map.put("flag","666");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
        File file = new File(FileUtil.writeToSDCard(bitmap, "cat.bmp"));
        HTTPUtil.getPost("http://192.168.1.101:8080/Miaowu/userAction", file, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("onFailure" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("onResponse"+response.body().string());
            }
        });
    }


}
