package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.TabViewpageDelegate;
import com.xiaomiquan.server.HttpUrl;
import com.yanzhenjie.nohttp.rest.CacheMode;

import io.reactivex.disposables.Disposable;

public class TabViewpageBinder extends BaseDataBind<TabViewpageDelegate> {

    public TabViewpageBinder(TabViewpageDelegate viewDelegate) {
        super(viewDelegate);
    }

    public Disposable getAllEXchange(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getAllEXchange)
                .setShowDialog(false)
                .setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE)
                .setRequestName("获得所有库中拥有的交易所列表")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

}