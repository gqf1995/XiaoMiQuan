package com.xiaomiquan.mvp.databinder.circle;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.circle.CreatCircleDelegate;
import com.xiaomiquan.mvp.delegate.circle.ReleaseArticleDelegate;
import com.xiaomiquan.server.HttpUrl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Creat for Andy
 */

public class ReleaseArticleBinder extends BaseDataBind<ReleaseArticleDelegate> {

    public ReleaseArticleBinder(ReleaseArticleDelegate viewDelegate) {
        super(viewDelegate);
    }


    /**
     * 发布到广场
     * @param title
     * @param content
     * @param type
     * @param platform
     * @param sync
     * @param requestCallback
     * @return
     */
    public Disposable releaseDynamic(
            File file,
            String title,
            String content,
            String type,
            String platform,
            String sync,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("title", title);
        baseMap.put("content", content);
        baseMap.put("type", type);
        baseMap.put("platform", platform);
        baseMap.put("sync", sync);
        Map<String,Object> map=new HashMap<>();
        map.put("files",file);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().saveArticle)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("发布帖子")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setFileMap(map)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }
    /**
     * 发布到圈子
     * @param title
     * @param content
     * @param type
     * @param platform
     * @param sync
     * @param requestCallback
     * @return
     */
    public Disposable releaseDynamicCircle(
            String title,
            String content,
            String type,
            String platform,
            String sync,
            String groupId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("title", title);
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
                .setRequestName("发布帖子")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }


}