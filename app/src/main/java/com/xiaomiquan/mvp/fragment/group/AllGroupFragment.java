package com.xiaomiquan.mvp.activity;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.mvp.databinder.AllGroupBinder;
import com.xiaomiquan.mvp.delegate.AllGroupDelegate;

public class AllGroupFragment extends BaseDataBindActivity<AllGroupDelegate, AllGroupBinder> {

    @Override
    protected Class<AllGroupDelegate> getDelegateClass() {
        return AllGroupDelegate.class;
    }

    @Override
    public AllGroupBinder getDataBinder(AllGroupDelegate viewDelegate) {
        return new AllGroupBinder(viewDelegate);
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
