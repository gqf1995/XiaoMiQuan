package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.ComTabViewpageDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class ComTabViewpageBinder extends BaseDataBind<ComTabViewpageDelegate> {

    public ComTabViewpageBinder(ComTabViewpageDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 战队待审核人数
     */
    public Disposable getToAuditCount(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getToAuditCount)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("修改组合信息")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }


}