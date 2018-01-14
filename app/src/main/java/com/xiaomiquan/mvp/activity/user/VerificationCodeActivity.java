package com.xiaomiquan.mvp.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.ActUtil;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.main.MainActivity;
import com.xiaomiquan.mvp.databinder.VerificationCodeBinder;
import com.xiaomiquan.mvp.delegate.VerificationCodeDelegate;
import com.xiaomiquan.utils.UiHeplUtils;
import com.xiaomiquan.widget.editcodeview.EditCodeWatcher;

public class VerificationCodeActivity extends BaseDataBindActivity<VerificationCodeDelegate, VerificationCodeBinder> {


    public static final String CODE_FIND_PASSWORD_PHONE = "code_find_password_phone";
    public static final String CODE_FIND_PASSWORD_EMAIL = "code_find_password_email";
    public static final String CODE_REGISTERED_PHONE = "code_registered_phone";
    public static final String CODE_REGISTERED_EMAIL = "code_registered_EMAIL";
    public static final String GET_CODE = "get_code";
    String title;
    String toast;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initCode();
        initToolbar(new ToolbarBuilder().setTitle(title));
        viewDelegate.viewHolder.et_code.setEditCodeWatcher(new EditCodeWatcher() {
            @Override
            public void onCodeChanged(String code) {
                if (!TextUtils.isEmpty(code)) {
                    if (code.length() == 6) {
                        if (CODE_REGISTERED_PHONE.equals(type) || CODE_REGISTERED_EMAIL.equals(type)) {
                            //直接注册登录
                            addRequest(binder.saveUser(
                                    CODE_REGISTERED_PHONE.equals(type) ? content : null,
                                    CODE_REGISTERED_EMAIL.equals(type) ? content : null,
                                    passs,
                                    code,
                                    VerificationCodeActivity.this
                            ));
                        } else if (CODE_FIND_PASSWORD_PHONE.equals(type) || CODE_FIND_PASSWORD_EMAIL.equals(type)) {
                            InputSetActivity.startAct(VerificationCodeActivity.this,
                                    InputSetActivity.SET_PASSWORD_AGAIN,
                                    content,
                                    CODE_FIND_PASSWORD_PHONE.equals(type),
                                    code
                            );
                            finish();
                        }
                    }
                }
            }
        });
        viewDelegate.viewHolder.tv_get_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initCode();
            }
        });
    }

    private void initCode() {
        if (CODE_FIND_PASSWORD_PHONE.equals(type)) {
            title = CommonUtils.getString(R.string.str_title_findpassword);
            toast = CommonUtils.getString(R.string.str_send_code_toast, CommonUtils.getString(R.string.str_login_label_phone));
            addRequest(binder.sendCodeForForgotPassWord(content, true, this));
        } else if (CODE_FIND_PASSWORD_EMAIL.equals(type)) {
            title = CommonUtils.getString(R.string.str_title_findpassword);
            toast = CommonUtils.getString(R.string.str_send_code_toast, CommonUtils.getString(R.string.str_login_label_email));
            addRequest(binder.sendCodeForForgotPassWord(content, false, this));
        } else if (CODE_REGISTERED_PHONE.equals(type)) {
            title = CommonUtils.getString(R.string.str_title_registeredvalidation);
            toast = CommonUtils.getString(R.string.str_send_code_toast, CommonUtils.getString(R.string.str_login_label_phone));
            addRequest(binder.sendCodeForRegister(content, true, this));
        } else if (CODE_REGISTERED_EMAIL.equals(type)) {
            title = CommonUtils.getString(R.string.str_title_registeredvalidation);
            toast = CommonUtils.getString(R.string.str_send_code_toast, CommonUtils.getString(R.string.str_login_label_email));
            addRequest(binder.sendCodeForRegister(content, false, this));
        }
    }


    private String type;
    private String content;
    private String passs;

    private void getIntentData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        content = intent.getStringExtra("content");
        passs = intent.getStringExtra("passs");
    }

    public static void startAct(Activity activity,
                                String type,
                                String content,
                                String passs
    ) {
        Intent intent = new Intent(activity, VerificationCodeActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("content", content);
        intent.putExtra("passs", passs);
        activity.startActivity(intent);
    }

    public static void startAct(Activity activity,
                                String type,
                                String content
    ) {
        Intent intent = new Intent(activity, VerificationCodeActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("content", content);
        activity.startActivity(intent);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                //获取验证码
                viewDelegate.viewHolder.tv_toast.setText(toast);
                UiHeplUtils.getCode(viewDelegate.viewHolder.tv_get_again, 60);
                break;
            case 0x124:
                //注册成功
                UserLogin userLogin = GsonUtil.getInstance().toObj(data, UserLogin.class);
                SingSettingDBUtil.setNewUserLogin(userLogin);
                ActUtil.getInstance().killAllActivity(this);
                gotoActivity(MainActivity.class).setIsFinish(true).startAct();
                break;
        }
    }


    @Override
    protected Class<VerificationCodeDelegate> getDelegateClass() {
        return VerificationCodeDelegate.class;
    }

    @Override
    public VerificationCodeBinder getDataBinder(VerificationCodeDelegate viewDelegate) {
        return new VerificationCodeBinder(viewDelegate);
    }

}
