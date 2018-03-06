package com.xiaomiquan.mvp.activity.chat;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.ChatManagementBinder;
import com.xiaomiquan.mvp.delegate.ChatManagementDelegate;
import com.xiaomiquan.widget.CircleDialogHelper;

public class ChatManagementActivity extends BaseDataBindActivity<ChatManagementDelegate, ChatManagementBinder> {

    @Override
    protected Class<ChatManagementDelegate> getDelegateClass() {
        return ChatManagementDelegate.class;
    }

    @Override
    public ChatManagementBinder getDataBinder(ChatManagementDelegate viewDelegate) {
        return new ChatManagementBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_chat_room_management)));
        getIntentData();
    }


    public static void startAct(Activity activity,
                                boolean isMy,
                                String headPortrait,
                                String onlineTotal,
                                String title,
                                int code) {
        Intent intent = new Intent(activity, ChatManagementActivity.class);
        intent.putExtra("isMy", isMy);
        intent.putExtra("headPortrait", headPortrait);
        intent.putExtra("title", title);
        intent.putExtra("introduce", headPortrait);
        intent.putExtra("onlineTotal", onlineTotal);
        activity.startActivityForResult(intent, code);
    }

    private String introduce;
    private String headPortrait;
    private String onlineTotal;
    private String title;
    private boolean isMy;

    private void getIntentData() {
        Intent intent = getIntent();
        introduce = intent.getStringExtra("introduce");
        headPortrait = intent.getStringExtra("headPortrait");
        onlineTotal = intent.getStringExtra("onlineTotal");
        title = intent.getStringExtra("title");
        isMy = intent.getBooleanExtra("isMy", false);
        if (!isMy) {
            viewDelegate.viewHolder.lin1.setVisibility(View.GONE);
        }
        GlideUtils.loadImage(headPortrait, viewDelegate.viewHolder.ic_pic);
        viewDelegate.viewHolder.lin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CircleDialogHelper.initDefaultInputDialog(ChatManagementActivity.this,
                        CommonUtils.getString(R.string.str_chat_room_introduce),
                        introduce, CommonUtils.getString(R.string.str_confirm), new OnInputClickListener() {
                            @Override
                            public void onClick(String text, View v) {
                                //修改简介

                            }
                        }
                ).show();
            }
        });
        viewDelegate.viewHolder.tv_name.setText(title);
        viewDelegate.viewHolder.tv_num.setText(CommonUtils.getString(R.string.str_chat_room_online_people, onlineTotal));
        viewDelegate.viewHolder.lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CircleDialogHelper.initDefaultInputDialog(ChatManagementActivity.this,
                        CommonUtils.getString(R.string.str_chat_room_name),
                        title, CommonUtils.getString(R.string.str_confirm), new OnInputClickListener() {
                            @Override
                            public void onClick(String text, View v) {
                                //名称

                            }
                        }
                ).show();
            }
        });
        viewDelegate.viewHolder.ic_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
