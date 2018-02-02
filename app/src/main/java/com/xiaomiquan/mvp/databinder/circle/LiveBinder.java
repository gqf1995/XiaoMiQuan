package com.xiaomiquan.mvp.databinder.circle;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.xiaomiquan.mvp.delegate.circle.LiveDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class LiveBinder extends BaseDataBind<LiveDelegate> {

    public LiveBinder(LiveDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 获取直播
     */
    public Disposable getLive(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("pageNum", 1);
        baseMap.put("platform", 1);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getSquareLive)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("获取直播")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

    /**
     * 获取大V
     */
    public Disposable getBigVlist(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("page", 1);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
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
     * 获取文章
     */
    public Disposable getArticle(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("pageNum", 1);
        baseMap.put("platform", 1);
        return new HttpRequest.Builder()
                .setRequestCode(0x125)
                .setRequestUrl(HttpUrl.getIntance().getArticle)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("获取文章")
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
            String type,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("attentionedUserId", attentionedUserId);
        baseMap.put("type", type);
        return new HttpRequest.Builder()
                .setRequestCode(0x126)
                .setRequestUrl(HttpUrl.getIntance().attention)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("获取大V")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }


    /**
     * 获取粉丝
     */
    public Disposable getFans(
            String userId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("userId", userId);
        baseMap.put("page", 1);
        return new HttpRequest.Builder()
                .setRequestCode(0x127)
                .setRequestUrl(HttpUrl.getIntance().getFans)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("获取粉丝")
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
            String userId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("userId", userId);
        baseMap.put("page", 1);
        return new HttpRequest.Builder()
                .setRequestCode(0x128)
                .setRequestUrl(HttpUrl.getIntance().getAttention)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("获取关注")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }


}