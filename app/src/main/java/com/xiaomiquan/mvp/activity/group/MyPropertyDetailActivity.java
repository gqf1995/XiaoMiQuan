package com.xiaomiquan.mvp.activity.group;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.mvp.databinder.group.MyPropertyDetailBinder;
import com.xiaomiquan.mvp.delegate.group.MyPropertyDetailDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

public class MyPropertyDetailActivity extends BaseDataBindActivity<MyPropertyDetailDelegate, MyPropertyDetailBinder> {

    @Override
    protected Class<MyPropertyDetailDelegate> getDelegateClass() {
        return MyPropertyDetailDelegate.class;
    }

    @Override
    public MyPropertyDetailBinder getDataBinder(MyPropertyDetailDelegate viewDelegate) {
        return new MyPropertyDetailBinder(viewDelegate);
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
