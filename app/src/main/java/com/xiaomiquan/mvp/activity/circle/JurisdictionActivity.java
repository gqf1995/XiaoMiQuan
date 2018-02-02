package com.xiaomiquan.mvp.activity.circle;

import android.content.Intent;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.mvp.databinder.circle.JurisdictionBinder;
import com.xiaomiquan.mvp.delegate.circle.JurisdictionDelegate;

public class JurisdictionActivity extends BaseDataBindActivity<JurisdictionDelegate, JurisdictionBinder> {

    @Override
    protected Class<JurisdictionDelegate> getDelegateClass() {
        return JurisdictionDelegate.class;
    }

    @Override
    public JurisdictionBinder getDataBinder(JurisdictionDelegate viewDelegate) {
        return new JurisdictionBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("圈子权限"));
        viewDelegate.viewHolder.lin_all.setOnClickListener(this);
        viewDelegate.viewHolder.lin_main.setOnClickListener(this);
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                break;
            case 0x124:
                break;

        }

    }

    UserCircle userCircle;

    private void getIntentData() {
        Intent intent = getIntent();
        userCircle = (UserCircle) intent.getSerializableExtra("userCircle");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_all:
                viewDelegate.viewHolder.icf_all.setVisibility(View.VISIBLE);
                viewDelegate.viewHolder.icf_main.setVisibility(View.GONE);
                addRequest(binder.circleBanned(CircleContentActivity.groupId, JurisdictionActivity.this));
                break;
            case R.id.lin_main:
                viewDelegate.viewHolder.icf_all.setVisibility(View.GONE);
                viewDelegate.viewHolder.icf_main.setVisibility(View.VISIBLE);
                addRequest(binder.circleBannedCancel(CircleContentActivity.groupId, JurisdictionActivity.this));

                break;
        }
    }
}
