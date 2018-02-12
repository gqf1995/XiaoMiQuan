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

public class EditTeamIntroductionActivity extends BaseDataBindActivity<EditIntroductionDelegate, EditIntroductionBinder> {

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
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_edit_team_introduction)).setSubTitle(CommonUtils.getString(R.string.str_save)));
        viewDelegate.viewHolder.tv_title.setText(CommonUtils.getString(R.string.str_edit_team_introduction));
        getIntentData();
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        //保存
        if (TextUtils.isEmpty(viewDelegate.viewHolder.et_introduction.getText().toString())) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_team_introduction));
            return;
        }
        addRequest(binder.editRemark(viewDelegate.viewHolder.et_introduction.getText().toString(), this));
    }

    public static void startAct(Activity activity,
                                String content,
                                int requstcode
    ) {
        Intent intent = new Intent(activity, EditTeamIntroductionActivity.class);
        intent.putExtra("content", content);
        activity.startActivityForResult(intent, requstcode);
    }

    private String content;

    private void getIntentData() {
        Intent intent = getIntent();
        content = intent.getStringExtra("content");
        viewDelegate.viewHolder.et_introduction.setText(content);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                Intent intent = new Intent();
                intent.putExtra("remark", viewDelegate.viewHolder.et_introduction.getText().toString());
                setResult(RESULT_OK, intent);
                onBackPressed();
                break;
        }
    }

}
