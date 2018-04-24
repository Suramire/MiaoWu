package com.suramire.miaowu.util;

import android.view.Gravity;
import android.widget.Toast;

import com.suramire.miaowu.base.App;

/**
 * Created by Suramire on 2018/1/24.
 */

public class ToastUtil {

    private static Toast toast;//实现不管我们触发多少次Toast调用，都只会持续一次Toast显示的时长



    /**
     * 长时间显示Toast【居中】
     * @param msg 显示的内容-字符串*/
    public static void showLongToastCenter(String msg){
        if(App.getInstance() != null) {
            if (toast == null) {
                toast = Toast.makeText(App.getInstance(), msg, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(msg);
            }
            toast.show();
            L.e("Toast msg:" + msg);

        }
    }
}