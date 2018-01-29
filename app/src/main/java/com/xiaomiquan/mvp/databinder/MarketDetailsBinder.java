package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.MarketDetailsDelegate;
import com.xiaomiquan.server.HttpUrl;
import com.yanzhenjie.nohttp.rest.CacheMode;

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
        baseMap.put("timeType", "kline_" + timeType);
        baseMap.put("lastTime", lastTime);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getKlineByOnlyKey)
                .setCacheMode(CacheMode.ONLY_REQUEST_NETWORK)
                .setShowDialog(false)
                .setRequestName("获取K线")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

    /**
     * 单独订阅/取消
     */
    public Disposable singlesubs(
            String onlykey,
            String symbol,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("onlykey", onlykey);
        baseMap.put("symbol", symbol);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().singlesubs)
                .setShowDialog(false)
                .setRequestName("单独订阅/取消")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }
}