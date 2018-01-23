package com.xiaomiquan.mvp.activity.circle;

import android.content.Intent;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.mvp.databinder.UserTopicBinder;
import com.xiaomiquan.mvp.delegate.UserTopicDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

public class UserTopicActivity extends BaseDataBindActivity<UserTopicDelegate, UserTopicBinder> {

    @Override
    protected Class<UserTopicDelegate> getDelegateClass() {
        return UserTopicDelegate.class;
    }

    @Override
    public UserTopicBinder getDataBinder(UserTopicDelegate viewDelegate) {
        return new UserTopicBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("发帖子"));
        final Intent intent=getIntent();
        viewDelegate.viewHolder.topic_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRequest(binder.savetopic(intent.getIntExtra("groupId",-1),
                        viewDelegate.viewHolder.topic_input1.getText().toString(),
                        UserTopicActivity.this
                ));
            }
        });
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:

                break;
        }
    }

}
