package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.InputSetDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class InputSetBinder extends BaseDataBind<InputSetDelegate> {

    public InputSetBinder(InputSetDelegate viewDelegate) {
        super(viewDelegate);
    }

    public Disposable retrievePassword(
            String content,
            boolean isPhont,
            String password,
            String code,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("phone", isPhont ? content : null);
        baseMap.put("email", !isPhont ? content : null);
        baseMap.put("password", password);
        baseMap.put("code", code);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().retrievePassword)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("用户登录")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

}