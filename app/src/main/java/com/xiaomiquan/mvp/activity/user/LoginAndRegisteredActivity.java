package com.xiaomiquan.mvp.activity.user;

import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.mvp.databinder.LoginAndRegisteredBinder;
import com.xiaomiquan.mvp.delegate.LoginAndRegisteredDelegate;

public class LoginAndRegisteredActivity extends BaseDataBindActivity<LoginAndRegisteredDelegate, LoginAndRegisteredBinder> {


    @Override
    protected Class<LoginAndRegisteredDelegate> getDelegateClass() {
        return LoginAndRegisteredDelegate.class;
    }

    @Override
    public LoginAndRegisteredBinder getDataBinder(LoginAndRegisteredDelegate viewDelegate) {
        return new LoginAndRegisteredBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(""));
        viewDelegate.getmToolbarTitle().setVisibility(View.GONE);


    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
