package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.MarketDetailsDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class MarketDetailsBinder extends BaseDataBind<MarketDetailsDelegate> {

    public MarketDetailsBinder(MarketDetailsDelegate viewDelegate) {
        super(viewDelegate);
    }


}