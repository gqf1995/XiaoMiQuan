package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.xiaomiquan.mvp.delegate.UserDrawerDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class UserDrawerBinder extends BaseDataBind<UserDrawerDelegate> {

    public UserDrawerBinder(UserDrawerDelegate viewDelegate) {
        super(viewDelegate);
    }

    public Disposable loginOut() {
        getBaseMapWithUid();
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().loginOut)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("用户登出")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(null)
                .build()
                .RxSendRequest();

    }
}