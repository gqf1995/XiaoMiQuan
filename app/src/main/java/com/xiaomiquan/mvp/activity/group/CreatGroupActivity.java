package com.xiaomiquan.mvp.activity.group;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.group.CreatGroupBinder;
import com.xiaomiquan.mvp.delegate.group.CreatGroupDelegate;
import com.xiaomiquan.widget.CircleDialogHelper;

public class CreatGroupActivity extends BaseDataBindActivity<CreatGroupDelegate, CreatGroupBinder> {

    @Override
    protected Class<CreatGroupDelegate> getDelegateClass() {
        return CreatGroupDelegate.class;
    }

    @Override
    public CreatGroupBinder getDataBinder(CreatGroupDelegate viewDelegate) {
        return new CreatGroupBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_creat_combination)).setSubTitle(CommonUtils.getString(R.string.str_save)));

    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        //保存
        if (!check()) {
            return;
        }
        CircleDialogHelper.initDefaultDialog(this, CommonUtils.getString(R.string.str_warning_issave), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        }).show();
    }

    private boolean check() {
        if (TextUtils.isEmpty(viewDelegate.viewHolder.et_con.getText().toString())) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_group_name));
            return false;
        }
        if (TextUtils.isEmpty(viewDelegate.viewHolder.et_content.getText().toString())) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_group_introduction));
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_group_type));
            return false;
        }
        if (!viewDelegate.viewHolder.ck_circle.isChecked() && !viewDelegate.viewHolder.ck_live.isChecked()) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_group_type));
            return false;
        }
        return true;
    }

    private void save() {
        addRequest(binder.save(viewDelegate.viewHolder.et_con.getText().toString(),
                viewDelegate.viewHolder.ck_live.isChecked() ? "1" : "2",
                viewDelegate.viewHolder.et_content.getText().toString(),
                viewDelegate.viewHolder.checkbox_synchronous.isChecked()?"1":"0",
                this));
    }

    public static void startAct(Fragment activity,
            int requestCode){
        Intent intent =new Intent(activity.getActivity(),CreatGroupActivity.class);
        activity.startActivityForResult(intent,requestCode);
    }
    public static void startAct(FragmentActivity activity,
                                int requestCode){
        Intent intent =new Intent(activity,CreatGroupActivity.class);
        activity.startActivityForResult(intent,requestCode);
    }
    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                setResult(RESULT_OK);
                onBackPressed();
                break;
        }
    }

}
