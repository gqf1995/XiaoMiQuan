package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.SortingUserCoinDelegate;
import com.xiaomiquan.server.HttpUrl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("订阅数据展示")
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

    /**
     * 自定义排序
     */
    public Disposable order(
            List<String> order,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        Map<String, String> key = new LinkedHashMap<>();
        for (int i = 0; i < order.size(); i++) {
            key.put(order.get(i), i + "");
        }
        baseMap.put("order", key);
        return new HttpRequest.Builder()
                .setRequestCode(0x125)
                .setRequestUrl(HttpUrl.getIntance().order)
                .setShowDialog(false)
                .setRequestName("自定义排序")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

}