package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Intent;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.mvp.databinder.circle.CirclePreviewBinder;
import com.xiaomiquan.mvp.delegate.circle.CirclePreviewDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

public class CirclePreviewActivity extends BaseDataBindActivity<CirclePreviewDelegate, CirclePreviewBinder> {

    @Override
    protected Class<CirclePreviewDelegate> getDelegateClass() {
        return CirclePreviewDelegate.class;
    }

    @Override
    public CirclePreviewBinder getDataBinder(CirclePreviewDelegate viewDelegate) {
        return new CirclePreviewBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle(userCircle.getName()));
    }

    private void initView() {
        viewDelegate.viewHolder.tv_title.setText(userCircle.getName());
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {

        }
    }

    public static void startAct(Activity activity,
                                UserCircle userCircle
    ) {
        Intent intent = new Intent(activity, CirclePreviewActivity.class);
        intent.putExtra("userCircle", userCircle);
        activity.startActivity(intent);
    }


    UserCircle userCircle;

    private void getIntentData() {
        Intent intent = getIntent();
        userCircle = intent.getParcelableExtra("userCircle");
    }

}
