package com.xiaomiquan.mvp.databinder.group;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.group.CreatGroupDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class CreatGroupBinder extends BaseDataBind<CreatGroupDelegate> {

    public CreatGroupBinder(CreatGroupDelegate viewDelegate) {
        super(viewDelegate);
    }

    //创建组合
    public Disposable save(
            String name,
            String type,
            String brief,
            String sync,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("name", name);// 组合名称
        baseMap.put("type", type);// 1:公开组合 2:私有组合 3:活动组合
        baseMap.put("brief", brief);// 组合简介
        baseMap.put("sync", sync);// 组合简介
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().save)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("创建组合")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

}