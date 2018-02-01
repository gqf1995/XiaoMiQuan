package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.CoinDetailDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class CoinDetailBinder extends BaseDataBind<CoinDetailDelegate> {

    public CoinDetailBinder(CoinDetailDelegate viewDelegate) {
        super(viewDelegate);
    }


}