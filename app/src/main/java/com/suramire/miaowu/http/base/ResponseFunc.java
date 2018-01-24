package com.suramire.miaowu.http.base;

import rx.functions.Func1;

/**
 * Created by Suramire on 2018/1/23.
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
