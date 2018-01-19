package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.ExchangeDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class ExchangeBinder extends BaseDataBind<ExchangeDelegate> {

    public ExchangeBinder(ExchangeDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 交易所 数据
     */
    public Disposable getAllMarketByExchange(
            String exchangeName,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("exchangeName", exchangeName);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getAllMarketByExchange)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("根据交易所名称获得相关信息")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

    /**
     * 交易所 数据
     */
    public Disposable getAllMarketBySymbol(
            String coinName,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("coinName", coinName);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getAllMarketBySymbol)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("根据交易所名称获得相关信息")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }
}