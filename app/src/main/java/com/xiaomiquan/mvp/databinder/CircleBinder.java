package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.CircleDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class CircleBinder extends BaseDataBind<CircleDelegate> {

    public CircleBinder(CircleDelegate viewDelegate) {
        super(viewDelegate);
    }


}