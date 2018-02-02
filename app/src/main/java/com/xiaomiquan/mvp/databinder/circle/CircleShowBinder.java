package com.xiaomiquan.mvp.databinder.circle;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.circle.CircleShowDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

/**
 * Created by Andy on 2018/1/25.
 */

public class CircleShowBinder extends BaseDataBind<CircleShowDelegate> {

    public CircleShowBinder(CircleShowDelegate viewDelegate) {
        super(viewDelegate);
    }

    public Disposable getMyCircle(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("pageNum", 1);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getMyCircleInfo)
                .setShowDialog(true)
                .setRequestName("获取已加入圈子信息")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    public Disposable getCircleTopic(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("pageNum", 1);
        baseMap.put("platform", 2);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().getCircleTopic)
                .setShowDialog(true)
                .setRequestName("获取帖子")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 点赞
     */
    public Disposable savePraise(
            String linkId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("linkId", linkId);
        return new HttpRequest.Builder()
                .setRequestCode(0x127)
                .setRequestUrl(HttpUrl.getIntance().savePraise)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("点赞")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

}
