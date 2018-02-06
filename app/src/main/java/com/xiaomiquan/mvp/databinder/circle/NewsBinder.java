package com.xiaomiquan.mvp.databinder.circle;

import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.circle.NewsDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class NewsBinder extends BaseDataBind<NewsDelegate> {

    public NewsBinder(NewsDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 获取直播
     */
    public Disposable getLive(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("pageNum", 1);
        baseMap.put("platform", 1);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getSquareLive)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("获取直播")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

    /**
     * 点赞
     */
    public Disposable savePraise(
            String linkId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("linkId", linkId);
        return new HttpRequest.Builder()
                .setRequestCode(0x127)
                .setRequestUrl(HttpUrl.getIntance().savePraise)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("点赞")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }
    /**
     * 获取资讯
     */
    public Disposable getNews(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("pageNum", viewDelegate.page);
        baseMap.put("pageSize", viewDelegate.pagesize);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getNews)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("获取资讯")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

//    /**
//     * 点赞
//     */
//    public Disposable saveLike(
//            String userId,
//            String newsId,
//            String like,
//            RequestCallback requestCallback) {
//        getBaseMapWithUid();
//        baseMap.put("userId", userId);
//        baseMap.put("newsId", newsId);
//        baseMap.put("like", like);
//        return new HttpRequest.Builder()
//                .setRequestCode(0x124)
//                .setRequestUrl(HttpUrl.getIntance().newsLike)
//                .setShowDialog(false)
//                .setDialog(viewDelegate.getNetConnectDialog())
//                .setRequestName("利好")
//                .setRequestMode(HttpRequest.RequestMode.POST)
//                .setParameterMode(HttpRequest.ParameterMode.Json)
//                .setRequestObj(baseMap)
//                .setRequestCallback(requestCallback)
//                .build()
//                .RxSendRequest();
//
//    }


}