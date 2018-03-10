package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.MessageCenterDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class MessageCenterBinder extends BaseDataBind<MessageCenterDelegate> {

    public MessageCenterBinder(MessageCenterDelegate viewDelegate) {
        super(viewDelegate);
    }


}