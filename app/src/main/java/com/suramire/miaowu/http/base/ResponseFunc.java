package com.suramire.miaowu.http.base;

import rx.functions.Func1;

/**
 * Created by Suramire on 2018/1/23.
 * 解包
 * 返回响应体内的实际数据
 * 非成功状态抛出异常信息
 */

public class ResponseFunc<T> implements Func1<BaseResponse<T>,T> {
    @Override
    public T call(BaseResponse<T> tBaseResponse) {
        if(tBaseResponse.code!=200){
            throw new RuntimeException(tBaseResponse.message+"");
        }
        return tBaseResponse.data;
    }
}
