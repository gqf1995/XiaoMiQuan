package com.xiaomiquan.mvp.activity.circle;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.mvp.databinder.circle.CirclePayBinder;
import com.xiaomiquan.mvp.delegate.circle.CirclePayDelegate;

/**
 * Created by Andy on 2018/1/27.
 */

public class PayCircleActivity extends BaseDataBindActivity<CirclePayDelegate, CirclePayBinder> {
    @Override
    public CirclePayBinder getDataBinder(CirclePayDelegate viewDelegate) {
        return new CirclePayBinder(viewDelegate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {

    }

    @Override
    protected Class<CirclePayDelegate> getDelegateClass() {
        return CirclePayDelegate.class;
    }
}
