package com.xiaomiquan.mvp.databinder.group;

import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.group.PositionDetailDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class PositionDetailBinder extends BaseDataBind<PositionDetailDelegate> {

    public PositionDetailBinder(PositionDetailDelegate viewDelegate) {
        super(viewDelegate);
    }
    /**
     * 持仓明细
     */
    public Disposable listPosition(
            String demoId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("demoId", demoId);
        baseMap.put("pageNum", viewDelegate.page);
        baseMap.put("pageSize", viewDelegate.pagesize);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().listPosition)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("持仓明细")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }


}