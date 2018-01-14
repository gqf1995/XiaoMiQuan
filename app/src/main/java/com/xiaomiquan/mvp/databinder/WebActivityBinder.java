package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.WebActivityDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class WebActivityBinder extends BaseDataBind<WebActivityDelegate> {

    public WebActivityBinder(WebActivityDelegate viewDelegate) {
        super(viewDelegate);
    }


}