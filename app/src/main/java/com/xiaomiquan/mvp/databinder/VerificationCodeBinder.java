package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.VerificationCodeDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class VerificationCodeBinder extends BaseDataBind<VerificationCodeDelegate> {

    public VerificationCodeBinder(VerificationCodeDelegate viewDelegate) {
        super(viewDelegate);
    }


}