package com.xiaomiquan.mvp.databinder.group;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class GroupChangeBinder extends BaseDataBind<BaseFragentPullDelegate> {

    public GroupChangeBinder(BaseFragentPullDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 我的关注
     */
    public Disposable listAttentionDemo(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().listAttentionDemo)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("我的关注")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }


    /**
     * 取消关注组合
     */
    public Disposable cancelAttention(
            String demoId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("demoId", demoId);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().cancelAttention)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("取消关注组合")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

}