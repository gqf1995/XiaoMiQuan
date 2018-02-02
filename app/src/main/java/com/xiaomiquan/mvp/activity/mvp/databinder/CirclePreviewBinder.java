package com.xiaomiquan.mvp.activity.mvp.databinder;

import com.xiaomiquan.mvp.activity.mvp.delegate.CirclePreviewDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class CirclePreviewBinder extends BaseDataBind<CirclePreviewDelegate> {

    public CirclePreviewBinder(CirclePreviewDelegate viewDelegate) {
        super(viewDelegate);
    }


}