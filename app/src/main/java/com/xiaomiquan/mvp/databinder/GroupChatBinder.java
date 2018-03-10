package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.CustomerServiceActDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class GroupChatBinder extends IMBinder<CustomerServiceActDelegate> {

    public GroupChatBinder(CustomerServiceActDelegate viewDelegate) {
        super(viewDelegate);
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
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("通过积分判断能否继续操作")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }




}