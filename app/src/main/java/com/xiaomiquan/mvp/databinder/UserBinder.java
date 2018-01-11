package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.UserDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class UserBinder extends BaseDataBind<UserDelegate> {

    public UserBinder(UserDelegate viewDelegate) {
        super(viewDelegate);
    }


}