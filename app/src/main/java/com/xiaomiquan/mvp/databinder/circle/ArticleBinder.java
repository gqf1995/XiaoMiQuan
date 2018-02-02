package com.xiaomiquan.mvp.databinder.circle;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.circle.ArticleDelegate;
import com.xiaomiquan.mvp.delegate.circle.LiveDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class ArticleBinder extends BaseDataBind<ArticleDelegate> {

    public ArticleBinder(ArticleDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 获取文章
     */
    public Disposable getArticle(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("pageNum", 1);
        baseMap.put("platform", 1);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getArticle)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("获取文章")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

}