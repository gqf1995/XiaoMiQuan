package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.mvp.databinder.circle.CreatCircleBinder;
import com.xiaomiquan.mvp.databinder.circle.ReleaseDynamicBinder;
import com.xiaomiquan.mvp.delegate.circle.CreatCircleDelegate;
import com.xiaomiquan.mvp.delegate.circle.ReleaseDynamicDelegate;

public class ReleaseDynamicActivity extends BaseDataBindActivity<ReleaseDynamicDelegate, ReleaseDynamicBinder> {

    @Override
    protected Class<ReleaseDynamicDelegate> getDelegateClass() {
        return ReleaseDynamicDelegate.class;
    }

    @Override
    public ReleaseDynamicBinder getDataBinder(ReleaseDynamicDelegate viewDelegate) {
        return new ReleaseDynamicBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("编辑动态").setSubTitle("发布"));
        getIntentData();
        initView();

    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        if (platform.equals("1")) {
            initRealeseSquare(String.valueOf(viewDelegate.viewHolder.ck_circle.isChecked()));
        } else {
            initRealeseCircle(String.valueOf(viewDelegate.viewHolder.ck_live.isChecked()));
        }
    }

    private void initView() {
        if (platform.equals("1")) {
            viewDelegate.viewHolder.ck_live.setChecked(true);
            viewDelegate.viewHolder.ck_circle.setChecked(false);
            viewDelegate.viewHolder.ck_live.setClickable(false);
            viewDelegate.viewHolder.ck_circle.setClickable(true);
        } else {
            viewDelegate.viewHolder.ck_live.setChecked(false);
            viewDelegate.viewHolder.ck_circle.setChecked(true);
            viewDelegate.viewHolder.ck_live.setClickable(true);
            viewDelegate.viewHolder.ck_circle.setClickable(false);

        }
    }

    private void initRealeseSquare(String sync) {
        addRequest(binder.releaseDynamic(
                viewDelegate.viewHolder.et_con.getText().toString(),
                type,
                platform,
                sync,
                this));
    }

    private void initRealeseCircle(String sync) {
        addRequest(binder.releaseDynamicCircle(
                viewDelegate.viewHolder.et_con.getText().toString(),
                type,
                platform,
                sync,
                CircleContentActivity.groupId,
                this));
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                onBackPressed();
                break;
            case 0x124:
                onBackPressed();
                break;
        }
    }


    String type;
    String platform;

    private void getIntentData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        platform = intent.getStringExtra("platform");
    }
    public static void startAct(Activity activity, String type, String platform) {
        Intent intent = new Intent(activity,ReleaseDynamicActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("platform", platform);
        activity.startActivity(intent);
    }

}
