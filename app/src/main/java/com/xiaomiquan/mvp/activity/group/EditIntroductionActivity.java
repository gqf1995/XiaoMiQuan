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
        addRequest(binder.updateDemoBrief(id, viewDelegate.viewHolder.et_introduction.getText().toString(),
                viewDelegate.viewHolder.checkbox_synchronous.isChecked() ? "1" : "0", this));
    }

    public static void startAct(Activity activity,
                                String id,
                                String content,
                                String sync,
                                int requstcode
    ) {
        Intent intent = new Intent(activity, EditIntroductionActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("content", content);
        intent.putExtra("sync", sync);
        activity.startActivityForResult(intent, requstcode);
    }

    private String id;
    private String content;
    private String sync;

    private void getIntentData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        content = intent.getStringExtra("content");
        sync = intent.getStringExtra("sync");
        viewDelegate.viewHolder.et_introduction.setText(content);
        if ("0".equals(sync)) {
            viewDelegate.viewHolder.checkbox_synchronous.setChecked(false);
        } else {
            viewDelegate.viewHolder.checkbox_synchronous.setChecked(true);
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                Intent intent = new Intent();
                intent.putExtra("brief", viewDelegate.viewHolder.et_introduction.getText().toString());
                intent.putExtra("sync", viewDelegate.viewHolder.checkbox_synchronous.isChecked() ? "1" : "0");
                setResult(RESULT_OK, intent);
                onBackPressed();
                break;
        }
    }

}
