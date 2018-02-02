package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.GetFriendsJoinDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class GetFriendsJoinBinder extends BaseDataBind<GetFriendsJoinDelegate> {

    public GetFriendsJoinBinder(GetFriendsJoinDelegate viewDelegate) {
        super(viewDelegate);
    }

    public Disposable getCircleMore(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("pageNum", 1);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getCircleMore)
                .setShowDialog(true)
                .setRequestName("获取未加入圈子")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    public Disposable joinCircle(
            String groupId,
            String inviteCode,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("groupId", groupId);
        baseMap.put("inviteCode", inviteCode);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().joinCircle)
                .setShowDialog(true)
                .setRequestName("加入圈子")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }


}