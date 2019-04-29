package com.xiaomiquan.mvp.databinder.circle;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.circle.CreatCircleDelegate;
import com.xiaomiquan.mvp.delegate.circle.ReleaseDynamicDelegate;
import com.xiaomiquan.server.HttpUrl;

import java.io.File;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Creat for Andy
 */

public class ReleaseDynamicBinder extends BaseDataBind<ReleaseDynamicDelegate> {

    public ReleaseDynamicBinder(ReleaseDynamicDelegate viewDelegate) {
        super(viewDelegate);
    }

    public Disposable releaseDynamic(
            List<File> files,
            String content,
            String type,
            String platform,
            String sync,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("content", content);
        baseMap.put("type", type);
        baseMap.put("platform", platform);
        baseMap.put("sync", sync);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().saveArticle)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("发布动态")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setFileList(files)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

    public Disposable releaseDynamicCircle(
            List<File> files,
            String content,
            String type,
            String platform,
            String sync,
            String groupId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("content", content);
        baseMap.put("type", type);
        baseMap.put("platform", platform);
        baseMap.put("sync", sync);
        baseMap.put("groupId", groupId);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().saveArticle)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("发布动态")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setFileList(files)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }


}