package com.xiaomiquan.mvp.activity.circle;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.mvp.databinder.circle.EditCircleBinder;
import com.xiaomiquan.mvp.delegate.circle.EditCircleDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

public class EditCircleActivity extends BaseDataBindActivity<EditCircleDelegate, EditCircleBinder> {

    @Override
    protected Class<EditCircleDelegate> getDelegateClass() {
        return EditCircleDelegate.class;
    }

    @Override
    public EditCircleBinder getDataBinder(EditCircleDelegate viewDelegate) {
        return new EditCircleBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("编辑币圈信息"));

    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
