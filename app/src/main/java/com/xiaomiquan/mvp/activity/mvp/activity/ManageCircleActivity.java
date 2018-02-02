package com.xiaomiquan.mvp.activity.mvp.activity;

import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.mvp.activity.mvp.databinder.ManageCircleBinder;
import com.xiaomiquan.mvp.activity.mvp.delegate.ManageCircleDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

public class ManageCircleActivity extends BaseDataBindActivity<ManageCircleDelegate, ManageCircleBinder> {

    @Override
    protected Class<ManageCircleDelegate> getDelegateClass() {
        return ManageCircleDelegate.class;
    }

    @Override
    public ManageCircleBinder getDataBinder(ManageCircleDelegate viewDelegate) {
        return new ManageCircleBinder(viewDelegate);
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
