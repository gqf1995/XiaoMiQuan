package com.xiaomiquan.mvp.activity.group;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.group.CreatGroupBinder;
import com.xiaomiquan.mvp.delegate.group.CreatGroupDelegate;

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
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_creat_combination)).setSubTitle(CommonUtils.getString(R.string.str_save)));

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
