package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.CombinationDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class CombinationBinder extends BaseDataBind<CombinationDelegate> {

    public CombinationBinder(CombinationDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 今日收益
     */
    public Disposable getTodayInfo(
            String demoId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("demoId", demoId);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getTodayInfo)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("今日收益&日均操作次数")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 分期收益
     */
    public Disposable allRate(
            String demoId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("demoId", demoId);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().allRate)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("分期收益")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 收益走势
     */
    public Disposable rateTrend(
            String demoId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("demoId", demoId);
        return new HttpRequest.Builder()
                .setRequestCode(0x125)
                .setRequestUrl(HttpUrl.getIntance().rateTrend)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("收益走势")
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
                .setRequestCode(0x126)
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

    /**
     * 关注组合
     */
    public Disposable demoattention(
            String demoId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("userId", demoId);
        return new HttpRequest.Builder()
                .setRequestCode(0x127)
                .setRequestUrl(HttpUrl.getIntance().demoattention)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("关注组合")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }


    /**
     * 大赛组合
     */
    public Disposable demoInfo(
            String demoId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("demoId", demoId);
        baseMap.put("type", "3");
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().demoInfo)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("大赛组合")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

}