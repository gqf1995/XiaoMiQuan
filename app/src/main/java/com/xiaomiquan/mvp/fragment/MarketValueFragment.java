package com.xiaomiquan.mvp.fragment;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.xiaomiquan.mvp.databinder.MarketValueBinder;
import com.xiaomiquan.mvp.delegate.MarketValueDelegate;

public class MarketValueFragment extends BaseDataBindFragment<MarketValueDelegate, MarketValueBinder> {

    @Override
    protected Class<MarketValueDelegate> getDelegateClass() {
        return MarketValueDelegate.class;
    }

    @Override
    public MarketValueBinder getDataBinder(MarketValueDelegate viewDelegate) {
        return new MarketValueBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();

    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
