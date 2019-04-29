package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.CoinCircleIndexDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class CoinCircleIndexBinder extends BaseDataBind<CoinCircleIndexDelegate> {

    public CoinCircleIndexBinder(CoinCircleIndexDelegate viewDelegate) {
        super(viewDelegate);
    }


}