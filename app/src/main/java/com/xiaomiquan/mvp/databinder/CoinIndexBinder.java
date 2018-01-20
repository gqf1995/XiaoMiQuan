package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.CoinIndexDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class CoinIndexBinder extends BaseDataBind<CoinIndexDelegate> {

    public CoinIndexBinder(CoinIndexDelegate viewDelegate) {
        super(viewDelegate);
    }


}