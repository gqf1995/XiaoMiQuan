package com.xiaomiquan.mvp.activity.circle;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.mvp.databinder.circle.ReleaseForecastBinder;
import com.xiaomiquan.mvp.delegate.circle.ReleaseForecastDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

public class ReleaseForecastActivity extends BaseDataBindActivity<ReleaseForecastDelegate, ReleaseForecastBinder> {

    @Override
    protected Class<ReleaseForecastDelegate> getDelegateClass() {
        return ReleaseForecastDelegate.class;
    }

    @Override
    public ReleaseForecastBinder getDataBinder(ReleaseForecastDelegate viewDelegate) {
        return new ReleaseForecastBinder(viewDelegate);
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
