package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.SortingUserCoinDelegate;
import com.xiaomiquan.server.HttpUrl;
import com.yanzhenjie.nohttp.rest.CacheMode;

import io.reactivex.disposables.Disposable;

public class SortingUserCoinBinder extends BaseDataBind<SortingUserCoinDelegate> {

    public SortingUserCoinBinder(SortingUserCoinDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 订阅数据展示
     */
    public Disposable marketdata(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().marketdata)
                .setShowDialog(false)
                .setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE)
                .setRequestName("订阅数据展示")
                .setRequestMode(HttpRequest.RequestMode.GET)
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
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("onlykey", onlykey);
        baseMap.put("symbol", "0");
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