package com.xiaomiquan.mvp.activity.circle;

import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.mvp.databinder.CreatCircleBinder;
import com.xiaomiquan.mvp.delegate.CreatCircleDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

public class CreatCircleActivity extends BaseDataBindActivity<CreatCircleDelegate, CreatCircleBinder> {

    @Override
    protected Class<CreatCircleDelegate> getDelegateClass() {
        return CreatCircleDelegate.class;
    }

    @Override
    public CreatCircleBinder getDataBinder(CreatCircleDelegate viewDelegate) {
        return new CreatCircleBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("创建圈子"));
        viewDelegate.viewHolder.circle_crate_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRequest(binder.creatCircle(
                        viewDelegate.viewHolder.circle_creat_name.getText().toString(),
                        viewDelegate.viewHolder.circle_creat_brief.getText().toString(),
                        CreatCircleActivity.this
                ));
            }
        });

    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                //登录成功
                finish();
                break;
        }
    }
}
