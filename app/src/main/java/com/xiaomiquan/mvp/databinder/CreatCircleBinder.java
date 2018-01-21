package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.CreatCircleDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

/**
 * Creat for Andy
 */

public class CreatCircleBinder extends BaseDataBind<CreatCircleDelegate> {

    public CreatCircleBinder(CreatCircleDelegate viewDelegate) {
        super(viewDelegate);
    }

    public Disposable creatCircle(
            String name,
            String brief,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("circlename", name);
        baseMap.put("circlebrief","");
        baseMap.put("circlebrief","hah");
        baseMap.put("circlebrief",brief);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().creatCircle)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("创建圈子")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }


}