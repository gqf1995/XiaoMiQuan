package com.xiaomiquan.mvp.activity;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.mvp.databinder.MarketBinder;
import com.xiaomiquan.mvp.delegate.MarketDelegate;

public class MarketFragment extends BaseDataBindFragment<MarketDelegate, MarketBinder> {

    @Override
    protected Class<MarketDelegate> getDelegateClass() {
        return MarketDelegate.class;
    }

    @Override
    public MarketBinder getDataBinder(MarketDelegate viewDelegate) {
        return new MarketBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(""));

    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
