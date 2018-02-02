package com.xiaomiquan.mvp.databinder.circle;

import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.circle.EditCircleDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class EditCircleBinder extends BaseDataBind<EditCircleDelegate> {

    public EditCircleBinder(EditCircleDelegate viewDelegate) {
        super(viewDelegate);
    }

    public Disposable editCircleInfo(
            String id,
            String name,
            String brief,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("id", id);
        baseMap.put("name", name);
        baseMap.put("brief", brief);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().editCircleInfo)
                .setShowDialog(true)
                .setRequestName("修改圈子信息")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }


}