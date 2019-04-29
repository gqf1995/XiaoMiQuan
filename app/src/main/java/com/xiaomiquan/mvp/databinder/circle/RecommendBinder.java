package com.xiaomiquan.mvp.databinder.circle;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.xiaomiquan.mvp.delegate.circle.CircleDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class RecommendBinder extends BaseDataBind<BaseFragentPullDelegate> {

    public RecommendBinder(BaseFragentPullDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 获取大V
     */
    public Disposable getBigVlist(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("page", 1);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getBigVlist)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("获取大V")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

    /**
     * 获取关注
     */
    public Disposable getAttention(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("page", 1);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().getAttention)
                .setRequestName("获取关注")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

    /**
     * 关注大V
     */
    public Disposable attention(
            String attentionedUserId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("attentionedUserId", attentionedUserId);
        baseMap.put("type", 1);
        return new HttpRequest.Builder()
                .setRequestCode(0x125)
                .setRequestUrl(HttpUrl.getIntance().attention)
                .setRequestName("关注")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

    /**
     * 取消关注
     */
    public Disposable attentiondelete(
            String userId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("userId", userId);
        return new HttpRequest.Builder()
                .setRequestCode(0x126)
                .setRequestUrl(HttpUrl.getIntance().attentiondelete)
                .setRequestName("取消关注")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }


}