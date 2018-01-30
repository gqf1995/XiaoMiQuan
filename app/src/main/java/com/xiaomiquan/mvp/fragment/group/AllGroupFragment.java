package com.xiaomiquan.mvp.fragment.group;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.AllGroupDelegate;

/**
 * 全部组合
 */
public class AllGroupFragment extends BasePullFragment<AllGroupDelegate, BaseFragmentPullBinder> {

    @Override
    protected Class<AllGroupDelegate> getDelegateClass() {
        return AllGroupDelegate.class;
    }

    @Override
    public BaseFragmentPullBinder getDataBinder(AllGroupDelegate viewDelegate) {
        return new BaseFragmentPullBinder(viewDelegate);
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

    @Override
    protected void refreshData() {

    }
}
