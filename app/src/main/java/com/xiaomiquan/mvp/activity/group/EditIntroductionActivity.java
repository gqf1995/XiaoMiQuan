package com.xiaomiquan.mvp.activity.group;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.EditIntroductionBinder;
import com.xiaomiquan.mvp.delegate.EditIntroductionDelegate;

public class EditIntroductionActivity extends BaseDataBindActivity<EditIntroductionDelegate, EditIntroductionBinder> {

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
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_edit_introduction)).setSubTitle(CommonUtils.getString(R.string.str_save)));

    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        //保存
        
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
