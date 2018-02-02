package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.UserHomePageDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class UserHomePageBinder extends BaseDataBind<UserHomePageDelegate> {

    public UserHomePageBinder(UserHomePageDelegate viewDelegate) {
        super(viewDelegate);
    }


}