package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.SearchCoinMarketDefaultDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class SearchCoinMarketDefaultBinder extends BaseDataBind<SearchCoinMarketDefaultDelegate> {

    public SearchCoinMarketDefaultBinder(SearchCoinMarketDefaultDelegate viewDelegate) {
        super(viewDelegate);
    }


}