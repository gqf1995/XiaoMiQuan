package com.xiaomiquan.mvp.databinder.circle;


import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.circle.CreatCodeDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class CreatCodeBinder extends BaseDataBind<CreatCodeDelegate> {

    public CreatCodeBinder(CreatCodeDelegate viewDelegate) {
        super(viewDelegate);
    }

    public Disposable creatCircleCode(
            String groupId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("groupId", groupId);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().creatCircleCode)
                .setShowDialog(true)
                .setRequestName("生成邀请码")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

}