package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.WebDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class WebBinder extends BaseDataBind<WebDelegate> {

    public WebBinder(WebDelegate viewDelegate) {
        super(viewDelegate);
    }


}