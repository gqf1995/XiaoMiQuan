package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.BaseActivityPullDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;


/**
 * Created by 郭青枫 on 2017/9/27.
 * 统一的 activity列表页面 接口代理
 */

public class BaseActivityPullBinder<T extends BaseActivityPullDelegate> extends BaseDataBind<T> {
    public BaseActivityPullBinder(T viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 成交历史
     */
    public Disposable listDeal(
            String demoId,
            String status,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("demoId", demoId);
        baseMap.put("status", status);// 1：已成交 2：未成交
        baseMap.put("pageNum", viewDelegate.page);
        baseMap.put("pageSize", viewDelegate.pagesize);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().listDeal)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("成交历史")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 组合分享
     */
    public Disposable articleSave(
            String content,
            String type,
            String platform,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("content", content);
        baseMap.put("type", type);// 1：已成交 2：未成交
        baseMap.put("platform", platform);// 1：已成交 2：未成交
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().articleSave)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("组合分享")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }


}