package com.xiaomiquan.mvp.activity;

import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.mvp.databinder.EditIntroductionBinder;
import com.xiaomiquan.mvp.delegate.EditIntroductionDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

public class EditIntroductionActivity extends BaseDataBindActivity<EditIntroductionDelegate,EditIntroductionBinder>{

    @Override
    protected Class<EditIntroductionDelegate> getDelegateClass() {
        return EditIntroductionDelegate.class;
    }

    @Override
    public EditIntroductionBinder getDataBinder(EditIntroductionDelegate viewDelegate) {
        return new EditIntroductionBinder(viewDelegate);
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
