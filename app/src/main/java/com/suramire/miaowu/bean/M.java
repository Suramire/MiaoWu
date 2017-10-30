package com.suramire.miaowu.bean;

import java.io.Serializable;

/**
 * Created by Suramire on 2017/10/22.
 * 数据传送时使用的结构体
 */

public class M implements Serializable {
    public final static int CODE_SUCCESS = 0;
    public final static int CODE_FAILURE = -1;
    public final static int CODE_ERROR = -2;

    //状态标志位
    private int code;
    //描述信息(选填)
    private String message;
    //包含的文本数据，主要用于JSON传输
    private String data;

    //不带描述信息的包
    public M(int code, String data) {
        this.code = code;
        this.data = data;
    }

    //带描述信息的包
    public M(int code, String data, String message) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
