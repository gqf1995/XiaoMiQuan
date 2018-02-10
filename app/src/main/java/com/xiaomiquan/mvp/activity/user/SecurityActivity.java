package com.xiaomiquan.mvp.activity.user;

import android.text.TextUtils;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.delegate.SecurityDelegate;

/**
 * 安全中心
 */
public class SecurityActivity extends BaseActivity<SecurityDelegate> {

    UserLogin userLogin;

    @Override
    protected Class<SecurityDelegate> getDelegateClass() {
        return SecurityDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        userLogin = SingSettingDBUtil.getUserLogin();
        initView();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_security)));
        viewDelegate.setOnClickListener(this, R.id.lin_phone, R.id.lin_email, R.id.lin_change_password);
    }

    private void initView() {
        if (TextUtils.isEmpty(userLogin.getPhone())) {
            viewDelegate.viewHolder.tv_phone_num.setText(CommonUtils.getString(R.string.str_no_binding));
        } else {
            viewDelegate.viewHolder.tv_phone_num.setText(userLogin.getPhone());
            viewDelegate.viewHolder.tv_phone_num.setEnabled(false);
        }
        if (TextUtils.isEmpty(userLogin.getEmail())) {
            viewDelegate.viewHolder.tv_email.setText(CommonUtils.getString(R.string.str_no_binding));
        } else {
            viewDelegate.viewHolder.tv_email.setText(userLogin.getEmail());
            viewDelegate.viewHolder.tv_email.setEnabled(false);
        }
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
                if (TextUtils.isEmpty(userLogin.getEmail())) {
                    //手机密码找回
                    InputSetActivity.startAct(this, InputSetActivity.FIND_PASSWORD_PHONE);
                } else {
                    //邮箱密码找回
                    InputSetActivity.startAct(this, InputSetActivity.FIND_PASSWORD_EMAIL);
                }
                break;
        }
    }
}
