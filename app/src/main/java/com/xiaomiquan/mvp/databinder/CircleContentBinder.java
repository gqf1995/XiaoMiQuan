package com.xiaomiquan.mvp.databinder;

import android.support.annotation.NonNull;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.CircleContentDelegate;
import com.xiaomiquan.server.HttpUrl;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import io.reactivex.disposables.Disposable;

/**
 * Created by Andy on 2018/1/19.
 */

public class CircleContentBinder extends BaseDataBind<CircleContentDelegate> {
    public CircleContentBinder(CircleContentDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 获取圈子详情信息
     */
    public Disposable getCicleContent(
            int userGroupId,
            int page,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("userGroupId", userGroupId);
        baseMap.put("page", page);
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
            int linkId,
            int linkType,
            String content,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("linkId", linkId);
        baseMap.put("linkType", linkType);
        baseMap.put("content",content);

        return new HttpRequest.Builder()
                .setRequestCode(0x123)
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

}
