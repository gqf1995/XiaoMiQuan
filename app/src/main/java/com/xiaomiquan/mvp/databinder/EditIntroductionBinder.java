package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.EditIntroductionDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class EditIntroductionBinder extends BaseDataBind<EditIntroductionDelegate> {

    public EditIntroductionBinder(EditIntroductionDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 修改组合信息
     */
    public Disposable updateDemoBrief(
            String demoId,
            String brief,
            String sync,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("demoId", demoId);
        baseMap.put("brief", brief);
        baseMap.put("sync", sync);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().updateDemoBrief)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("修改组合信息")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }
}