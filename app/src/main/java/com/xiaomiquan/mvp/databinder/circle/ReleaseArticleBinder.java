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
     * 发布
     *
     * @param title
     * @param content
     * @param type
     * @param platform
     * @param sync
     * @param requestCallback
     * @return
     */
    public Disposable releaseArticle(
            File file,
            String title,
            String content,
            String type,
            String platform,
            Boolean sync,
            String groupId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("title", title);
        baseMap.put("content", content);
        baseMap.put("type", type);
        baseMap.put("platform", platform);
        baseMap.put("sync", sync);
        baseMap.put("groupId", groupId);
        Map<String, Object> map = new HashMap<>();
        map.put("files", file);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().saveArticle)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("发布文章")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setFileMap(map)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 广场导入文章
     *
     * @param sync
     * @param requestCallback
     * @return
     */
    public Disposable leadSquareArticle(
            File file,
            String url,
            Boolean sync,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("url", url);
        baseMap.put("sync", sync);
        Map<String, Object> map = new HashMap<>();
        map.put("files", file);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().dealSquareArticle)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("导入微信文章")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setFileMap(map)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 圈子导入文章
     *
     * @param sync
     * @param requestCallback
     * @return
     */
    public Disposable leadCircleArticle(
            File file,
            String groupId,
            String url,
            Boolean sync,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("groupId", groupId);
        baseMap.put("url", url);
        baseMap.put("sync", sync);
        Map<String, Object> map = new HashMap<>();
        map.put("files", file);
        return new HttpRequest.Builder()
                .setRequestCode(0x125)
                .setRequestUrl(HttpUrl.getIntance().dealCircleArticle)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("导入微信文章")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setFileMap(map)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

}