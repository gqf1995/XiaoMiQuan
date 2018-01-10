package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.MainDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class MainBinder extends BaseDataBind<MainDelegate> {

    public MainBinder(MainDelegate viewDelegate) {
        super(viewDelegate);
    }


}