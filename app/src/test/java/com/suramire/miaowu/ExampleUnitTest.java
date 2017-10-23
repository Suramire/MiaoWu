package com.suramire.miaowu;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        if(isMobileNumber("188505")){
            System.out.println("是手机号");
        }else{
            System.out.println("不是手机号");
        }
    }
    //手机号判断
    public static boolean isMobileNumber(String mobiles) {
        return Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[^1^4,\\D]))\\d{8}").matcher(mobiles).matches();
    }
}