package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.CombinationDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class CombinationBinder extends BaseDataBind<CombinationDelegate> {

    public CombinationBinder(CombinationDelegate viewDelegate) {
        super(viewDelegate);
    }


}