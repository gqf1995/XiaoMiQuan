package com.xiaomiquan.mvp.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.InputSetBinder;
import com.xiaomiquan.mvp.delegate.InputSetDelegate;

public class InputSetActivity extends BaseDataBindActivity<InputSetDelegate, InputSetBinder> {

    public static final String FIND_PASSWORD_PHONE = "find_password_phone";
    public static final String FIND_PASSWORD_EMAIL = "find_password_email";
    public static final String SET_PASSWORD_AGAIN = "set_password_again";
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

        } else if (FIND_PASSWORD_EMAIL.equals(type)) {
            //下一步 邮箱验证码验证

        } else if (SET_PASSWORD_AGAIN.equals(type)) {
            //重置密码

        }
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

    public static void startAct(Activity activity,
                                String type
    ) {
        Intent intent = new Intent(activity, InputSetActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    private String type;

    private void getIntentData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
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
