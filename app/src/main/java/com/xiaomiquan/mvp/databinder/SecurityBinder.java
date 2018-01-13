package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.SecurityDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class SecurityBinder extends BaseDataBind<SecurityDelegate> {

    public SecurityBinder(SecurityDelegate viewDelegate) {
        super(viewDelegate);
    }


}