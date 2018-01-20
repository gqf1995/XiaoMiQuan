package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.CircleContentDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

/**
 * Created by Andy on 2018/1/19.
 */

public class CircleContentBinder extends BaseDataBind<CircleContentDelegate> {
    public CircleContentBinder(CircleContentDelegate viewDelegate) {
        super(viewDelegate);
    }
    /**
     * 获取圈子详情信息
     */
    public Disposable getC(
            String exchangeName,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("exchangeName", exchangeName);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getAllMarketByExchange)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("获取圈子详情信息")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }


    /**
     * 市值
     */
    public Disposable getAllMarketCaps(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getAllMarketCaps)
                .setShowDialog(false)
                .setRequestName("获得所有市值信息 未做排序")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }
}
