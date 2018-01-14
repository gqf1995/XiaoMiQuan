package com.xiaomiquan.mvp.activity.user;

import android.text.TextUtils;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.main.MainActivity;
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
        viewDelegate.viewHolder.tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(viewDelegate.viewHolder.et_input1.getText().toString())) {
                    String toast;
                    if (viewDelegate.viewHolder.tl_2.getCurrentTab() == 0) {
                        toast = viewDelegate.isLoginInputPhone ? CommonUtils.getString(R.string.str_login_et_phone) : CommonUtils.getString(R.string.str_login_et_email);
                    } else {
                        toast = viewDelegate.isRegisteredInputPhone ? CommonUtils.getString(R.string.str_login_et_phone) : CommonUtils.getString(R.string.str_login_et_email);
                    }
                    ToastUtil.show(toast);
                    return;
                }
                if (TextUtils.isEmpty(viewDelegate.viewHolder.et_input2.getText().toString())) {
                    ToastUtil.show(CommonUtils.getString(R.string.str_login_et_pass));
                    return;
                }
                if (viewDelegate.viewHolder.tl_2.getCurrentTab() == 0) {
                    //登录
                    addRequest(binder.userLogin(
                            viewDelegate.isLoginInputPhone ? viewDelegate.viewHolder.et_input1.getText().toString() : null,
                            !viewDelegate.isLoginInputPhone ? viewDelegate.viewHolder.et_input1.getText().toString() : null,
                            viewDelegate.viewHolder.et_input2.getText().toString(),
                            LoginAndRegisteredActivity.this
                    ));
                } else {
                    //注册 跳转验证码页面
                    VerificationCodeActivity.startAct(LoginAndRegisteredActivity.this,
                            viewDelegate.isRegisteredInputPhone ? VerificationCodeActivity.CODE_REGISTERED_PHONE : VerificationCodeActivity.CODE_REGISTERED_EMAIL,
                            viewDelegate.viewHolder.et_input1.getText().toString(),
                            viewDelegate.viewHolder.et_input2.getText().toString()
                    );
                }
            }
        });

    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                //登录成功
                UserLogin userLogin = GsonUtil.getInstance().toObj(data, UserLogin.class);
                SingSettingDBUtil.setNewUserLogin(userLogin);
                gotoActivity(MainActivity.class).setIsFinish(true).startAct();
                break;
        }
    }

}
