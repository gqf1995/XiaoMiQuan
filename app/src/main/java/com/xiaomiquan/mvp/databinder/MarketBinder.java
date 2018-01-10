package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.MarketDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class MarketBinder extends BaseDataBind<MarketDelegate> {

    public MarketBinder(MarketDelegate viewDelegate) {
        super(viewDelegate);
    }


}