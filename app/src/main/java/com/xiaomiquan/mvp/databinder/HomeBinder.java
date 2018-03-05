package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.HomeDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class HomeBinder extends BaseDataBind<HomeDelegate> {

    public HomeBinder(HomeDelegate viewDelegate) {
        super(viewDelegate);
    }


}