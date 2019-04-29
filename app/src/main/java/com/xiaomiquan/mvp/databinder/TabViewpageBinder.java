package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.TabViewpageDelegate;
import com.xiaomiquan.server.HttpUrl;

import java.util.List;

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
                .setRequestName("获得所有库中拥有的交易所列表")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }


    public Disposable subs(
            List<String> onlykey,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("onlykey", onlykey);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().subs)
                .setShowDialog(false)
                .setRequestName("自选订阅")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }
}