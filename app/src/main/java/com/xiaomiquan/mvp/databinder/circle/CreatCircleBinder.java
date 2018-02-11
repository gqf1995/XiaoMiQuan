package com.xiaomiquan.mvp.databinder.circle;

import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.circle.CreatCircleDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.xiaomiquan.server.HttpUrl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Creat for Andy
 */

public class CreatCircleBinder extends BaseDataBind<CreatCircleDelegate> {

    public CreatCircleBinder(CreatCircleDelegate viewDelegate) {
        super(viewDelegate);
    }

    public Disposable creatCircle(
            File file,
            String name,
            String type,
            String brief,
            String isFree,
            String chargeMoney,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("name", name);
        baseMap.put("type", type);
        baseMap.put("brief", brief);
        baseMap.put("isFree", isFree);
        baseMap.put("chargeMoney", chargeMoney);
        Map<String, Object> map = new HashMap<>();
        map.put("files", file);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().creatCircle)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("创建圈子")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setFileMap(map)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }


}