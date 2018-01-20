package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.VerificationCodeDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class VerificationCodeBinder extends BaseDataBind<VerificationCodeDelegate> {

    public VerificationCodeBinder(VerificationCodeDelegate viewDelegate) {
        super(viewDelegate);
    }

    public Disposable sendCodeForRegister(
            String content,
            boolean isPhone,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("phone", isPhone ? content : null);
        baseMap.put("email", !isPhone ? content : null);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().sendCodeForRegister)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("用户注册发送验证码")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    public Disposable saveUser(
            String phone,
            String email,
            String password,
            String code,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("phone", phone);
        baseMap.put("email", email);
        baseMap.put("password", password);
        baseMap.put("code", code);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().saveUser)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("用户注册")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

    public Disposable sendCodeForForgotPassWord(
            String content,
            boolean isPhone,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("phone", isPhone ? content : null);
        baseMap.put("email", !isPhone ? content : null);
        return new HttpRequest.Builder()
                .setRequestCode(0x125)
                .setRequestUrl(HttpUrl.getIntance().sendCodeForForgotPassWord)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("用户找回密码发送短信")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }
}