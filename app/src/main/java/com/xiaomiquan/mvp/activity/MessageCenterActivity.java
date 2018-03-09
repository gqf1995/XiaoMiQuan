package com.xiaomiquan.mvp.activity;

import android.content.Intent;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.activity.user.PraiseReplyActivity;
import com.xiaomiquan.mvp.databinder.MessageCenterBinder;
import com.xiaomiquan.mvp.delegate.MessageCenterDelegate;

public class MessageCenterActivity extends BaseDataBindActivity<MessageCenterDelegate, MessageCenterBinder> {

    @Override
    protected Class<MessageCenterDelegate> getDelegateClass() {
        return MessageCenterDelegate.class;
    }

    @Override
    public MessageCenterBinder getDataBinder(MessageCenterDelegate viewDelegate) {
        return new MessageCenterBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_message_center)));
        viewDelegate.setOnClickListener(this, R.id.lin_sysytem, R.id.lin_my);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_sysytem:
                break;
            case R.id.lin_my:
                startActivity(new Intent(this, PraiseReplyActivity.class));
                break;
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
        }
    }

}
