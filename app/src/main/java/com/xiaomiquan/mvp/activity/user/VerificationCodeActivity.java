package com.xiaomiquan.mvp.activity.user;

import android.app.Activity;
import android.content.Intent;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.VerificationCodeBinder;
import com.xiaomiquan.mvp.delegate.VerificationCodeDelegate;

public class VerificationCodeActivity extends BaseDataBindActivity<VerificationCodeDelegate, VerificationCodeBinder> {


    public static final String CODE_FIND_PASSWORD_PHONE = "code_find_password_phone";
    public static final String CODE_FIND_PASSWORD_EMAIL = "code_find_password_email";
    public static final String CODE_FIND_PASSWORD_REGISTERED_PHONE = "code_find_password_registered_phone";
    public static final String CODE_FIND_PASSWORD_REGISTERED_EMAIL = "code_find_password_registered_EMAIL";

    String title;

    String toast;


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();

        if (CODE_FIND_PASSWORD_PHONE.equals(type)) {
            title = CommonUtils.getString(R.string.str_title_findpassword);
            toast = CommonUtils.getString(R.string.str_send_code_toast, CommonUtils.getString(R.string.str_login_label_phone));
        } else if (CODE_FIND_PASSWORD_EMAIL.equals(type)) {
            title = CommonUtils.getString(R.string.str_title_findpassword);
            toast = CommonUtils.getString(R.string.str_send_code_toast, CommonUtils.getString(R.string.str_login_label_email));
        } else if (CODE_FIND_PASSWORD_REGISTERED_PHONE.equals(type)) {
            title = CommonUtils.getString(R.string.str_title_registeredvalidation);
            toast = CommonUtils.getString(R.string.str_send_code_toast, CommonUtils.getString(R.string.str_login_label_phone));
        } else if (CODE_FIND_PASSWORD_REGISTERED_EMAIL.equals(type)) {
            title = CommonUtils.getString(R.string.str_title_registeredvalidation);
            toast = CommonUtils.getString(R.string.str_send_code_toast, CommonUtils.getString(R.string.str_login_label_email));
        }
        viewDelegate.viewHolder.tv_toast.setText(toast);
        initToolbar(new ToolbarBuilder().setTitle(title));

    }

    public static void startAct(Activity activity,
                                String type
    ) {
        Intent intent = new Intent(activity, VerificationCodeActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    private String type;

    private void getIntentData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
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
