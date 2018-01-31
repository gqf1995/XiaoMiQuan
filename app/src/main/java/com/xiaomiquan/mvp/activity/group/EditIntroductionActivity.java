package com.xiaomiquan.mvp.activity.group;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
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
        getIntentData();
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        //保存
        if (TextUtils.isEmpty(viewDelegate.viewHolder.et_introduction.getText().toString())) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_group_introduction));
            return;
        }

    }

    public static void startAct(Activity activity,
                                String id,
                                String content
    ) {
        Intent intent = new Intent(activity, EditIntroductionActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("content", content);
        activity.startActivity(intent);
    }

    private String id;
    private String content;

    private void getIntentData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        content = intent.getStringExtra("content");
        viewDelegate.viewHolder.et_introduction.setText(content);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
