package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.HomeDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class HomeBinder extends BaseDataBind<HomeDelegate> {

    public HomeBinder(HomeDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 我的组合
     */
    public Disposable listDemo(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().listDemo)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("我的组合")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 通过积分判断能否继续操作
     */
    public Disposable checkScore(
            String chatGroupId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("chatGroupId", chatGroupId);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().checkScore)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("通过积分判断能否继续操作")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 组合详情
     */
    public Disposable getDemoByUserId(
            String userId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("userId", userId);
        return new HttpRequest.Builder()
                .setRequestCode(0x127)
                .setRequestUrl(HttpUrl.getIntance().getDemoByUserId)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("组合详情")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }
}