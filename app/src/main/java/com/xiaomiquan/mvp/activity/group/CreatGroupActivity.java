package com.xiaomiquan.mvp.activity.group;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.mvp.databinder.group.CreatGroupBinder;
import com.xiaomiquan.mvp.delegate.group.CreatGroupDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

public class CreatGroupActivity extends BaseDataBindActivity<CreatGroupDelegate, CreatGroupBinder> {

    @Override
    protected Class<CreatGroupDelegate> getDelegateClass() {
        return CreatGroupDelegate.class;
    }

    @Override
    public CreatGroupBinder getDataBinder(CreatGroupDelegate viewDelegate) {
        return new CreatGroupBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("创建组合").setSubTitle("保存"));

    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
