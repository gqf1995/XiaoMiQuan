package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.DemoDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class DemoBinder extends BaseDataBind<DemoDelegate> {

    public DemoBinder(DemoDelegate viewDelegate) {
        super(viewDelegate);
    }


}