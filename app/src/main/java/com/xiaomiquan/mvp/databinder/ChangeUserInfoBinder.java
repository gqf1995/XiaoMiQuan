package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.ChangeUserInfoDelegate;
import com.xiaomiquan.server.HttpUrl;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class ChangeUserInfoBinder extends BaseDataBind<ChangeUserInfoDelegate> {

    public ChangeUserInfoBinder(ChangeUserInfoDelegate viewDelegate) {
        super(viewDelegate);
    }

    /**
     * 市值
     */
    public Disposable editUserInfo(
            String nickName,
            String brief,
            File file,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("nickName", nickName);
        baseMap.put("brief", brief);
        Map<String, Object> objectMap = null;
        if (file != null) {
            objectMap = new LinkedHashMap<>();
            objectMap.put("file", file);
        }
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().editUserInfo)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("获得所有市值信息 未做排序")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setFileMap(objectMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }


}