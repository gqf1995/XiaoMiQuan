package com.xiaomiquan.mvp.activity.chat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.chat.ChatGroupInfo;
import com.xiaomiquan.mvp.activity.EditTextActivity;
import com.xiaomiquan.mvp.databinder.ChatManagementBinder;
import com.xiaomiquan.mvp.delegate.ChatManagementDelegate;
import com.xiaomiquan.utils.UiHeplUtils;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.util.ArrayList;

public class ChatManagementActivity extends BaseDataBindActivity<ChatManagementDelegate, ChatManagementBinder> {
    ChatGroupInfo chatGroupInfo;
    File pictureFile;

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
                                String id,
                                String onlineTotal,
                                String introduce,
                                String title,
                                int code) {
        Intent intent = new Intent(activity, ChatManagementActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("isMy", isMy);
        intent.putExtra("headPortrait", headPortrait);
        intent.putExtra("title", title);
        intent.putExtra("introduce", introduce);
        intent.putExtra("onlineTotal", onlineTotal);
        activity.startActivityForResult(intent, code);
    }

    private String introduce;
    private String headPortrait;
    private String onlineTotal;
    private String title;
    private String id;
    private boolean isMy;

    private void getIntentData() {
        Intent intent = getIntent();
        introduce = intent.getStringExtra("introduce");
        headPortrait = intent.getStringExtra("headPortrait");
        onlineTotal = intent.getStringExtra("onlineTotal");
        title = intent.getStringExtra("title");
        id = intent.getStringExtra("id");
        isMy = intent.getBooleanExtra("isMy", false);
        if (!isMy) {
            viewDelegate.viewHolder.lin1.setVisibility(View.GONE);
        } else {
            viewDelegate.viewHolder.ic_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //上传头像
                    getPic();
                }
            });
            viewDelegate.viewHolder.checkbox_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewDelegate.viewHolder.checkbox_status.isChecked()) {
                        //开启
                        addRequest(binder.openChatroom(id));
                    } else {
                        //关闭
                        addRequest(binder.closeChatroom(id));
                    }
                }
            });
            addRequest(binder.getChatroom(id, this));
        }
        viewDelegate.viewHolder.lin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextActivity.startAct(ChatManagementActivity.this,
                        CommonUtils.getString(R.string.str_chat_room_introduce),
                        introduce,
                        CommonUtils.getString(R.string.str_toast_please_input) + CommonUtils.getString(R.string.str_chat_room_introduce),
                        true,
                        isMy,
                        0x124
                );
            }
        });
        viewDelegate.viewHolder.lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextActivity.startAct(ChatManagementActivity.this,
                        CommonUtils.getString(R.string.str_chat_room_name),
                        introduce,
                        CommonUtils.getString(R.string.str_toast_please_input) + CommonUtils.getString(R.string.str_chat_room_name),
                        false,
                        isMy,
                        0x123
                );
            }
        });
        GlideUtils.loadImage(headPortrait, viewDelegate.viewHolder.ic_pic);
        viewDelegate.viewHolder.tv_name.setText(title);
        viewDelegate.viewHolder.tv_num.setText(CommonUtils.getString(R.string.str_chat_room_online_people, onlineTotal));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x123) {
                //修改名称
                title = data.getStringExtra("content");
                addRequest(binder.editChatroomBrief(id, title, introduce, ChatManagementActivity.this));
            } else if (requestCode == 0x124) {
                //修改简介
                introduce = data.getStringExtra("content");
                addRequest(binder.editChatroomBrief(id, title, introduce, ChatManagementActivity.this));
            }
        }
    }

    private void getPic() {
        UiHeplUtils.getPhoto(this, new Action<String>() {
            @Override
            public void onAction(int requestCode, @NonNull String result) {
                showPic(result);
            }
        }, new Action<ArrayList<AlbumFile>>() {
            @Override
            public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                showPic(result.get(0).getPath());
            }
        }, 1);
    }

    private void showPic(String path) {
        pictureFile = new File(path);
        GlideUtils.loadImage(Uri.fromFile(pictureFile), viewDelegate.viewHolder.ic_pic);
        addRequest(binder.editGroupAvatar(id, pictureFile, ChatManagementActivity.this));
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                //修改信息
                Intent intent = new Intent();
                intent.putExtra("title", title);
                setResult(RESULT_OK, intent);
                break;
            case 0x124:
                //头像
                setResult(RESULT_OK);
                break;
            case 0x125:
                chatGroupInfo = GsonUtil.getInstance().toObj(data, ChatGroupInfo.class);
                if (1 == chatGroupInfo.getStatus()) {
                    //开启
                    viewDelegate.viewHolder.checkbox_status.setChecked(true);
                } else if (2 == chatGroupInfo.getStatus()) {
                    //关闭
                    viewDelegate.viewHolder.checkbox_status.setChecked(false);
                }
                break;
        }
    }

}
