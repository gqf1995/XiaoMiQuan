package com.xiaomiquan.mvp.databinder.circle;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.circle.SquareDelegate;
import com.xiaomiquan.mvp.delegate.circle.SquareWebDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

public class SquareWebBinder extends BaseDataBind<SquareWebDelegate> {

    public SquareWebBinder(SquareWebDelegate viewDelegate) {
        super(viewDelegate);
    }


}