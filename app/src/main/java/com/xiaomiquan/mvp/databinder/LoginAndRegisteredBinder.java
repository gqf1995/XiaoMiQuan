package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.LoginAndRegisteredDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class LoginAndRegisteredBinder extends BaseDataBind<LoginAndRegisteredDelegate> {

    public LoginAndRegisteredBinder(LoginAndRegisteredDelegate viewDelegate) {
        super(viewDelegate);
    }

    public Disposable userLogin(
            String phone,
            String email,
            String password,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("phone", phone);
        baseMap.put("email", email);
        baseMap.put("password", password);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().userLogin)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("用户登录")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }


}