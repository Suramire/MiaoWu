package com.suramire.miaowu.model;

import com.suramire.miaowu.listener.OnValidationListener;

import org.junit.Test;

/**
 * Created by Suramire on 2017/10/23.
 */
public class RegisterModelTest {
    @Test
    public void validateRegisterInformation() throws Exception {
        new RegisterModel().validateRegisterInformation("18850508860", "zhang", "123", "123", new OnValidationListener() {
            @Override
            public void onSuccess() {
                System.out.println("onSuccess");
            }

            @Override
            public void onFailure(String failtureMessage) {
                System.out.println("onFailure"+failtureMessage);
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println("onError"+errorMessage);
            }
        });
    }

}