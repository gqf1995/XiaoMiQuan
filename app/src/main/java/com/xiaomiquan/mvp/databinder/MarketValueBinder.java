package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.MarketValueDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class MarketValueBinder extends BaseDataBind<MarketValueDelegate> {

    public MarketValueBinder(MarketValueDelegate viewDelegate) {
        super(viewDelegate);
    }


}