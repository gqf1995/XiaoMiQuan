package com.xiaomiquan.mvp.databinder.circle;

import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.circle.CirclePreviewDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class CirclePreviewBinder extends BaseDataBind<CirclePreviewDelegate> {

    public CirclePreviewBinder(CirclePreviewDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 获取圈子帖子
     */
    public Disposable getCicleInfo(
            String groupId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("groupId", groupId);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getCircleInfo)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("获取圈子详情")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    public Disposable joinCircle(
            String groupId,
            String inviteCode,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("groupId", groupId);
        baseMap.put("inviteCode", inviteCode);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().joinCircle)
                .setShowDialog(true)
                .setRequestName("加入圈子")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }


}