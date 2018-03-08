package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.ChatManagementDelegate;
import com.xiaomiquan.server.HttpUrl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class ChatManagementBinder extends BaseDataBind<ChatManagementDelegate> {

    public ChatManagementBinder(ChatManagementDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 修改群简介
     */
    public Disposable editChatroomBrief(
            String chatroomId,
            String chatroomName,
            String brief,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("chatroomId", chatroomId);
        baseMap.put("chatroomName", chatroomName);
        baseMap.put("brief", brief);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().editChatroomBrief)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("修改群简介")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 修改群组头像
     */
    public Disposable editGroupAvatar(
            String groupId,
            File file,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("groupId", groupId);
        Map<String, Object> map = new HashMap<>();
        map.put("file", file);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().editGroupAvatar)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("修改群简介")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setFileMap(map)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 查询群明细
     */
    public Disposable getChatroom(
            String groupId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("groupId", groupId);
        return new HttpRequest.Builder()
                .setRequestCode(0x125)
                .setRequestUrl(HttpUrl.getIntance().getChatroom)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("查询群明细")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 关闭聊天室
     */
    public Disposable closeChatroom(
            String chatroomId) {
        getBaseMapWithUid();
        baseMap.put("chatroomId", chatroomId);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().closeChatroom)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("关闭聊天室")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .build()
                .RxSendRequest();
    }

    /**
     * 开放聊天室
     */
    public Disposable openChatroom(
            String chatroomId) {
        getBaseMapWithUid();
        baseMap.put("chatroomId", chatroomId);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().openChatroom)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("开放聊天室")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .build()
                .RxSendRequest();
    }
}