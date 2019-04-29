package com.xiaomiquan.mvp.activity.user;

import android.app.Activity;
import android.content.Intent;
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
import com.xiaomiquan.mvp.databinder.InputSetBinder;
import com.xiaomiquan.mvp.delegate.InputSetDelegate;

/**
 *
 */
public class InputSetActivity extends BaseDataBindActivity<InputSetDelegate, InputSetBinder> {

    public static final String FIND_PASSWORD_PHONE = "find_password_phone";
    public static final String FIND_PASSWORD_EMAIL = "find_password_email";
    public static final String SET_PASSWORD_AGAIN = "set_password_again";
    public static final String SET_PHONE = "set_phone";
    public static final String SET_EMAIL = "set_email";
    String title = "";

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        if (FIND_PASSWORD_PHONE.equals(type)) {
            title = CommonUtils.getString(R.string.str_title_findpassword);
            viewDelegate.initPhoneFindPass();
        } else if (FIND_PASSWORD_EMAIL.equals(type)) {
            title = CommonUtils.getString(R.string.str_title_findpassword);
            viewDelegate.initEmailFindPass();
        } else if (SET_PASSWORD_AGAIN.equals(type)) {
            title = CommonUtils.getString(R.string.str_title_setpasswordagain);
            viewDelegate.initSetPassword();
        }
        initToolbar(new ToolbarBuilder().setTitle(title));
        viewDelegate.viewHolder.tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit();
            }
        });

    }

    private void commit() {
        if (FIND_PASSWORD_PHONE.equals(type)) {
            //下一步 手机验证码验证
            if (TextUtils.isEmpty(viewDelegate.viewHolder.et_input1.getText().toString())) {
                ToastUtil.show(CommonUtils.getString(R.string.str_login_et_phone));
                return;
            }
            VerificationCodeActivity.startAct(this, VerificationCodeActivity.CODE_FIND_PASSWORD_PHONE, viewDelegate.viewHolder.et_input1.getText().toString());
            finish();
        } else if (FIND_PASSWORD_EMAIL.equals(type)) {
            //下一步 邮箱验证码验证
            if (TextUtils.isEmpty(viewDelegate.viewHolder.et_input1.getText().toString())) {
                ToastUtil.show(CommonUtils.getString(R.string.str_login_et_email));
                return;
            }
            VerificationCodeActivity.startAct(this, VerificationCodeActivity.CODE_REGISTERED_EMAIL, viewDelegate.viewHolder.et_input1.getText().toString());
            finish();
        } else if (SET_PASSWORD_AGAIN.equals(type)) {
            //重置密码
            if (TextUtils.isEmpty(viewDelegate.viewHolder.et_input1.getText().toString())) {
                ToastUtil.show(CommonUtils.getString(R.string.str_et_new_pass));
                return;
            }
            if (TextUtils.isEmpty(viewDelegate.viewHolder.et_input2.getText().toString())) {
                ToastUtil.show(CommonUtils.getString(R.string.str_et_new_pass_again));
                return;
            }
            if (!viewDelegate.viewHolder.et_input1.getText().toString().equals(viewDelegate.viewHolder.et_input2.getText().toString())) {
                ToastUtil.show(CommonUtils.getString(R.string.str_et_new_pass_error));
                return;
            }
            addRequest(binder.retrievePassword(content, isPhone, viewDelegate.viewHolder.et_input1.getText().toString(), code, this));
        } else if (SET_PHONE.equals(type)) {
            //绑定手机号
            if (TextUtils.isEmpty(viewDelegate.viewHolder.et_input1.getText().toString())) {
                ToastUtil.show(CommonUtils.getString(R.string.str_login_et_phone));
                return;
            }
            VerificationCodeActivity.startAct(this, VerificationCodeActivity.CODE_FIND_PASSWORD_PHONE, viewDelegate.viewHolder.et_input1.getText().toString());
        } else if (SET_EMAIL.equals(type)) {
            //绑定邮箱
            if (TextUtils.isEmpty(viewDelegate.viewHolder.et_input1.getText().toString())) {
                ToastUtil.show(CommonUtils.getString(R.string.str_login_et_email));
                return;
            }
            VerificationCodeActivity.startAct(this, VerificationCodeActivity.CODE_FIND_PASSWORD_PHONE, viewDelegate.viewHolder.et_input1.getText().toString());
        }
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                //设置密码成功
                UserLogin userLogin = GsonUtil.getInstance().toObj(data, UserLogin.class);
                SingSettingDBUtil.setNewUserLogin(userLogin);
                gotoActivity(MainActivity.class).setIsFinish(true).startAct();
                break;
        }
    }

    public static void startAct(Activity activity,
                                String type
    ) {
        Intent intent = new Intent(activity, InputSetActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    public static void startAct(Activity activity,
                                String type,
                                String content,
                                boolean isPhone,
                                String code
    ) {
        Intent intent = new Intent(activity, InputSetActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("code", code);
        intent.putExtra("content", content);
        intent.putExtra("isPhone", isPhone);
        activity.startActivity(intent);
    }

    private String type;
    private String code;
    private String content;
    private boolean isPhone;

    private void getIntentData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        code = intent.getStringExtra("code");
        content = intent.getStringExtra("content");
        isPhone = intent.getBooleanExtra("isPhone", false);
    }

    @Override
    protected Class<InputSetDelegate> getDelegateClass() {
        return InputSetDelegate.class;
    }

    @Override
    public InputSetBinder getDataBinder(InputSetDelegate viewDelegate) {
        return new InputSetBinder(viewDelegate);
    }

}
