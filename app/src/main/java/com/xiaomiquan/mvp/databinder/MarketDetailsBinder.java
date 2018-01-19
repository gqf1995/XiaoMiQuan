package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.MarketDetailsDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class MarketDetailsBinder extends BaseDataBind<MarketDetailsDelegate> {

    public MarketDetailsBinder(MarketDetailsDelegate viewDelegate) {
        super(viewDelegate);
    }

    public Disposable getKlineByOnlyKey(
            String onlyKey,
            String timeType,
            String lastTime,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("onlyKey", onlyKey);
        baseMap.put("timeType", "kline_"+timeType);
        baseMap.put("lastTime", lastTime);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getKlineByOnlyKey)
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