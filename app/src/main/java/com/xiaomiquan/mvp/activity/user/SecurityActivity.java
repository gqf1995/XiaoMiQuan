package com.xiaomiquan.mvp.activity.user;

import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.delegate.SecurityDelegate;

/**
 * 安全中心
 */
public class SecurityActivity extends BaseActivity<SecurityDelegate> {

    @Override
    protected Class<SecurityDelegate> getDelegateClass() {
        return SecurityDelegate.class;
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_security)));
        viewDelegate.setOnClickListener(this, R.id.lin_phone, R.id.lin_email, R.id.lin_change_password);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_phone:
                //手机

                break;
            case R.id.lin_email:
                //绑定邮箱

                break;
            case R.id.lin_change_password:
                //修改密码

                break;
        }
    }
}
