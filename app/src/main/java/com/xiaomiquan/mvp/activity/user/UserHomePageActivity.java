package com.xiaomiquan.mvp.activity.user;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.UserHomePageBinder;
import com.xiaomiquan.mvp.delegate.UserHomePageDelegate;
import com.xiaomiquan.mvp.fragment.group.HistoryTradingFragment;

public class UserHomePageActivity extends BaseDataBindActivity<UserHomePageDelegate, UserHomePageBinder> {

    @Override
    protected Class<UserHomePageDelegate> getDelegateClass() {
        return UserHomePageDelegate.class;
    }

    @Override
    public UserHomePageBinder getDataBinder(UserHomePageDelegate viewDelegate) {
        return new UserHomePageBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("郭青枫").setmRightImg1(CommonUtils.getString(R.string.ic_Chat)).setmRightImg2(CommonUtils.getString(R.string.ic_Filter2)));
        viewDelegate.initToolbar();
        viewDelegate.initAddFragment(R.id.fl_root, getSupportFragmentManager());
        viewDelegate.addFragment(new HistoryTradingFragment());
        viewDelegate.showFragment(0);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
