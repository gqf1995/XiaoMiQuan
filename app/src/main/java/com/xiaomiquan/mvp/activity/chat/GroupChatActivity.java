package com.xiaomiquan.mvp.activity.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ListUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.entity.bean.chat.CheckScore;
import com.xiaomiquan.entity.bean.event.ChatControlEvent;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.user.PersonalDetailsActivity;
import com.xiaomiquan.mvp.databinder.GroupChatBinder;
import com.xiaomiquan.mvp.delegate.CustomerServiceActDelegate;
import com.xiaomiquan.mvp.fragment.ConversationFragmentEx;
import com.xiaomiquan.utils.UserSet;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Created by 郭青枫 on 2018/3/5 0005.
 */

public class GroupChatActivity extends BaseDataBindActivity<CustomerServiceActDelegate, GroupChatBinder> {

    @Override
    protected Class<CustomerServiceActDelegate> getDelegateClass() {
        return CustomerServiceActDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
    }

    public static void startAct(Activity context,
                                String id,
                                String title,
                                String headPortrait,
                                String introduce,
                                String onlineTotal,
                                boolean isMy,
                                boolean isCanTalk,
                                int code
    ) {
        Intent intent;
        intent = new Intent(context, GroupChatActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("headPortrait", headPortrait);
        intent.putExtra("introduce", introduce);
        intent.putExtra("onlineTotal", onlineTotal);
        intent.putExtra("isMy", isMy);
        intent.putExtra("isCanTalk", isCanTalk);
        ((Activity) context).startActivityForResult(intent, code);
    }

    public static void startAct(Fragment context,
                                String id,
                                String title,
                                String headPortrait,
                                String introduce,
                                String onlineTotal,
                                boolean isMy,
                                boolean isCanTalk,
                                int code
    ) {
        Intent intent;
        intent = new Intent(context.getActivity(), GroupChatActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("headPortrait", headPortrait);
        intent.putExtra("introduce", introduce);
        intent.putExtra("onlineTotal", onlineTotal);
        intent.putExtra("isMy", isMy);
        intent.putExtra("isCanTalk", isCanTalk);
        context.startActivityForResult(intent, code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            String title = data.getStringExtra("title");
            if (!TextUtils.isEmpty(title)) {
                //修改群名称
                viewDelegate.getmToolbarTitle().setText(title);
            }
        }
    }

    private String id;
    private String headPortrait;
    private String introduce;
    private String onlineTotal;
    private String title;
    private boolean isMy;
    private boolean isCanTalk;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            initToolbar(new ToolbarBuilder().setTitle(title)
                    .setSubTitle(CommonUtils.getString(R.string.str_management)));
            viewDelegate.getmToolbarSubTitle().setTextColor(CommonUtils.getColor(R.color.mark_color));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        binder.connect(SingSettingDBUtil.getUserLogin().getImToken());
        addRequest(binder.checkScore(id, GroupChatActivity.this));
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        //管理
        ChatManagementActivity.startAct(this,
                isMy,
                headPortrait,
                id,
                onlineTotal,
                introduce,
                title,
                0x123
        );
    }

    @Override
    public GroupChatBinder getDataBinder(CustomerServiceActDelegate viewDelegate) {
        return new GroupChatBinder(viewDelegate);
    }

    private Handler handler = new Handler() {//进行延时跳转
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //定时刷新 检测
                    addRequest(binder.checkScore(id, GroupChatActivity.this));
                    handler.sendEmptyMessageDelayed(1, 30000);
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);//清空消息方便gc回收
        RongIM.getInstance().setMessageAttachedUserInfo(false);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x124:
                //检测结果
                CheckScore checkScore = GsonUtil.getInstance().toObj(data, CheckScore.class);
                isMy = checkScore.isLeader();
                isCanTalk = checkScore.isCanSpeak();
                if (fragment != null) {
                    fragment.setClose(checkScore.isJoinGroup());
                }
                break;
        }
    }

    //客服消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChatControlEvent(ChatControlEvent event) {
        if (!isMy) {
            fragment.setClose(event.isClose());
        }
    }

    ConversationFragmentEx fragment;

    private void getIntentData() {
        UserLogin userLogin = SingSettingDBUtil.getUserLogin();
        RongIM.getInstance().setMessageAttachedUserInfo(true);
        handler.sendEmptyMessageDelayed(1, 30000);
        RongIM.getInstance().setCurrentUserInfo(new UserInfo(userLogin.getId() + "", userLogin.getNickName(), Uri.parse(userLogin.getAvatar())));
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        headPortrait = intent.getStringExtra("headPortrait");
        introduce = intent.getStringExtra("introduce");
        onlineTotal = intent.getStringExtra("onlineTotal");
        title = intent.getStringExtra("title");
        isMy = intent.getBooleanExtra("isMy", false);
        isCanTalk = intent.getBooleanExtra("isCanTalk", false);
        viewDelegate.setNoStatusBarFlag(false);
        setStatusBarLightOrNight(UserSet.getinstance().isNight());

        setWindowManagerLayoutParams(WindowManagerLayoutParamsNone);
        if (ListUtils.isEmpty(getSupportFragmentManager().getFragments())) {
            fragment = new ConversationFragmentEx();
            Conversation.ConversationType conversationType = null;
            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversation")
                    .appendPath(Conversation.ConversationType.GROUP.getName().toLowerCase())
                    .appendQueryParameter("targetId", id).build();
            fragment.setUri(uri);
        /* 加载 ConversationFragment */
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.rong_content, fragment);
            transaction.commit();
            fragment.setCanTalk(isCanTalk);
            RongExtension rongExtension = findViewById(R.id.rc_extension);
        } else {
            if (getSupportFragmentManager().getFragments().get(0) instanceof ConversationFragmentEx) {
                fragment = (ConversationFragmentEx) getSupportFragmentManager().getFragments().get(0);
            }
        }
        fragment.getContext();
        RongIM.setConversationBehaviorListener(new MyConversationBehaviorListener());
    }

    private class MyConversationBehaviorListener implements RongIM.ConversationBehaviorListener {

        /**
         * 当点击用户头像后执行。
         *
         * @param context          上下文。
         * @param conversationType 会话类型。
         * @param userInfo         被点击的用户的信息。
         * @return 如果用户自己处理了点击后的逻辑，则返回 true，否则返回 false，false 走融云默认处理方式。
         */
        @Override
        public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
            if (userInfo != null) {
                if (!TextUtils.isEmpty(userInfo.getUserId())) {
                    if (context != null) {
                        PersonalDetailsActivity.startAct(fragment.getActivity(), userInfo.getUserId(), 0x123);
                    }
                }
            }
            return true;
        }

        /**
         * 当长按用户头像后执行。
         *
         * @param context          上下文。
         * @param conversationType 会话类型。
         * @param userInfo         被点击的用户的信息。
         * @return 如果用户自己处理了点击后的逻辑，则返回 true，否则返回 false，false 走融云默认处理方式。
         */
        @Override
        public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
            return false;
        }

        @Override
        public boolean onMessageClick(Context context, View view, io.rong.imlib.model.Message message) {
            return false;
        }

        /**
         * 当点击链接消息时执行。
         *
         * @param context 上下文。
         * @param link    被点击的链接。
         * @return 如果用户自己处理了点击后的逻辑处理，则返回 true， 否则返回 false, false 走融云默认处理方式。
         */
        @Override
        public boolean onMessageLinkClick(Context context, String link) {
            return false;
        }

        @Override
        public boolean onMessageLongClick(Context context, View view, io.rong.imlib.model.Message message) {
            return false;
        }
    }
}
