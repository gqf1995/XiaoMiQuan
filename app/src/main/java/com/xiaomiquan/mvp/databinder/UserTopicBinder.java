package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.UserTopicDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class UserTopicBinder extends BaseDataBind<UserTopicDelegate> {

    public UserTopicBinder(UserTopicDelegate viewDelegate) {
        super(viewDelegate);
    }

    public Disposable savetopic(
            int groupId,
            String content,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("groupId", groupId);
        baseMap.put("content",content);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().saveUsertopic)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("发帖子")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Rest)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }


}