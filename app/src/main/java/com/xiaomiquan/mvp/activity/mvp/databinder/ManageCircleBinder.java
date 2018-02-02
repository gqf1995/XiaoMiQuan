package com.xiaomiquan.mvp.activity.mvp.databinder;

import com.xiaomiquan.mvp.activity.mvp.delegate.ManageCircleDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class ManageCircleBinder extends BaseDataBind<ManageCircleDelegate> {

    public ManageCircleBinder(ManageCircleDelegate viewDelegate) {
        super(viewDelegate);
    }


}