package com.xiaomiquan.mvp.databinder.circle;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.circle.CircleContentDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

/**
 * Created by Andy on 2018/1/19.
 */

public class CircleContentBinder extends BaseDataBind<CircleContentDelegate> {
    public CircleContentBinder(CircleContentDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 获取圈子帖子
     */
    public Disposable getCicleContent(
            String pageNum,
            String groupId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("pageNum", pageNum);
        baseMap.put("groupId", groupId);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getUsertopic)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("获取帖子")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

    /**
     * 评论
     */
    public Disposable saveComment(
            String linkId,
            String content,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("linkId", linkId);
        baseMap.put("content", content);

        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().saveComment)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("评论")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

    /**
     * 回复
     */
    public Disposable saveRecomment(
            String linkId,
            String content,
            String re_user_id,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("linkId", linkId);
        baseMap.put("content", content);
        baseMap.put("re_user_id", re_user_id);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().saveComment)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("回复")
                .setRequestMode(HttpRequest.RequestMode.POST)
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
                .setRequestCode(0x126)
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
}
