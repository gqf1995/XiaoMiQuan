package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.CoinDetailDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class CoinDetailBinder extends BaseDataBind<CoinDetailDelegate> {

    public CoinDetailBinder(CoinDetailDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 币种资料
     */
    public Disposable getSymbolInfomation(
            String name,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("name", name);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().getSymbolInfomation)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("币种资料")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 根据币种名称获得相关信息
     */
    public Disposable getAllMarketBySymbol(
            String coinName,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("coinName", coinName);
        baseMap.put("offset", 1);
        baseMap.put("limit", 5);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getAllMarketBySymbol)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("根据币种名称获得相关信息")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

    /**
     * 根据某个币种全称查询币种市值信息
     */
    public Disposable getMarketCapById(
            String ename,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("ename", ename);
        return new HttpRequest.Builder()
                .setRequestCode(0x125)
                .setRequestUrl(HttpUrl.getIntance().getMarketCapById)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("根据某个币种全称查询币种市值信息")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

}