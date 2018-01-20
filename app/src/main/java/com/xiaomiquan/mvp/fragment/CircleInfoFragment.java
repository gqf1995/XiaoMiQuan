package com.xiaomiquan.mvp.fragment;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.xiaomiquan.mvp.databinder.CircleInfoBinder;
import com.xiaomiquan.mvp.delegate.CircleInnfoDelegate;

/**
 * Created by Andy on 2018/1/19.
 */

public class CircleInfoFragment extends BasePullFragment<CircleInnfoDelegate,CircleInfoBinder>{
    @Override
    public CircleInfoBinder getDataBinder(CircleInnfoDelegate viewDelegate) {
        return new CircleInfoBinder(viewDelegate);
    }
    @Override
    protected Class<CircleInnfoDelegate> getDelegateClass() {
        return CircleInnfoDelegate.class;
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {

    }
    @Override
    protected void refreshData() {

    }
}
