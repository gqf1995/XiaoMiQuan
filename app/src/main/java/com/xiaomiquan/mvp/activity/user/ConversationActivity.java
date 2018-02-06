package com.xiaomiquan.mvp.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.delegate.CustomerServiceActDelegate;
import com.xiaomiquan.utils.UserSet;

import io.rong.imkit.RongExtension;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by 郭青枫 on 2017/11/14.
 * 聊天页面
 */
public class ConversationActivity extends BaseActivity<CustomerServiceActDelegate> {

    public static final String conversation_private = "CONVERSATION_PRIVATE";
    public static final String conversation_service = "CONVERSATION_SERVICE";

    public static void startAct(Activity activity,
                                String type,
                                String id
    ) {
        Intent intent = new Intent(activity, ConversationActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    private String type;
    private String id;

    private void getIntentData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        id = intent.getStringExtra("id");
    }

    @Override
    protected Class<CustomerServiceActDelegate> getDelegateClass() {
        return CustomerServiceActDelegate.class;
    }

    @Override
    public void onBackPressed() {
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.rong_content);
        if (!fragment.onBackPressed()) {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        //SoftHideKeyBoardUtil.assistActivity(this);
        viewDelegate.setNoStatusBarFlag(false);
        initToolbar(new ToolbarBuilder().setTitle("客服中心"));
        setStatusBarLightOrNight(UserSet.getinstance().isNight());

        setWindowManagerLayoutParams(WindowManagerLayoutParamsNone);
        ConversationFragment fragment = new ConversationFragment();

        Conversation.ConversationType conversationType = null;

        if (conversation_private.equals(type)) {
            conversationType = Conversation.ConversationType.PRIVATE;
        } else if (conversation_service.equals(type)) {
            conversationType = Conversation.ConversationType.CUSTOMER_SERVICE;
        }

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation")
                .appendPath(conversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", id).build();
        fragment.setUri(uri);

        /* 加载 ConversationFragment */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, fragment);
        transaction.commit();

        RongExtension rongExtension = findViewById(R.id.rc_extension);


    }

}