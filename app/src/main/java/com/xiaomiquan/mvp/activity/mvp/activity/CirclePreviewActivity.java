package com.xiaomiquan.mvp.activity.mvp.activity;

import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.mvp.activity.mvp.databinder.CirclePreviewBinder;
import com.xiaomiquan.mvp.activity.mvp.delegate.CirclePreviewDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

public class CirclePreviewActivity extends BaseDataBindActivity<CirclePreviewDelegate, CirclePreviewBinder> {

    @Override
    protected Class<CirclePreviewDelegate> getDelegateClass() {
        return CirclePreviewDelegate.class;
    }

    @Override
    public CirclePreviewBinder getDataBinder(CirclePreviewDelegate viewDelegate) {
        return new CirclePreviewBinder(viewDelegate);
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
