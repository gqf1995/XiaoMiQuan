package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.CoinMarketDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class CoinMarketBinder extends BaseDataBind<CoinMarketDelegate> {

    public CoinMarketBinder(CoinMarketDelegate viewDelegate) {
        super(viewDelegate);
    }


}