package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.SearchCoinMarketResultDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class SearchCoinMarketResultBinder extends BaseDataBind<SearchCoinMarketResultDelegate> {

    public SearchCoinMarketResultBinder(SearchCoinMarketResultDelegate viewDelegate) {
        super(viewDelegate);
    }


}