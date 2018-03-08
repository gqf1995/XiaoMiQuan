package com.xiaomiquan.mvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.EditTextBinder;
import com.xiaomiquan.mvp.delegate.EditTextDelegate;

public class EditTextActivity extends BaseDataBindActivity<EditTextDelegate, EditTextBinder> {

    @Override
    protected Class<EditTextDelegate> getDelegateClass() {
        return EditTextDelegate.class;
    }

    @Override
    public EditTextBinder getDataBinder(EditTextDelegate viewDelegate) {
        return new EditTextBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
    }


    public static void startAct(Activity activity,
                                String title,
                                String content,
                                String hint,
                                boolean isCanNull,
                                int requestCode) {
        Intent intent = new Intent(activity, EditTextActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("hint", hint);
        intent.putExtra("isCanNull", isCanNull);
        activity.startActivityForResult(intent, requestCode);
    }

    private String title;
    private String content;
    private String hint;
    private boolean isCanNull = false;

    private void getIntentData() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        hint = intent.getStringExtra("hint");
        isCanNull = intent.getBooleanExtra("isCanNull", false);
        initToolbar(new ToolbarBuilder().setTitle(title).setSubTitle(CommonUtils.getString(R.string.str_save)));
        viewDelegate.viewHolder.edit.setText(content);
        viewDelegate.viewHolder.edit.setHint(hint);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            viewDelegate.getmToolbarSubTitle().setTextColor(CommonUtils.getColor(R.color.mark_color));
        }
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        if (!isCanNull) {
            if (TextUtils.isEmpty(viewDelegate.viewHolder.edit.getText().toString())) {
                ToastUtil.show(hint);
                return;
            }
        }
        if (content.equals(viewDelegate.viewHolder.edit.getText().toString())) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_no_change));
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("content", viewDelegate.viewHolder.edit.getText().toString());
        setResult(RESULT_OK, intent);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
        }
    }

}
