package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.ChatManagementDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class ChatManagementBinder extends BaseDataBind<ChatManagementDelegate> {

    public ChatManagementBinder(ChatManagementDelegate viewDelegate) {
        super(viewDelegate);
    }


}