package com.xiaomiquan.mvp.activity.user;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.R;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.databinder.IMBinder;
import com.xiaomiquan.mvp.delegate.CustomerServiceActDelegate;
import com.xiaomiquan.utils.UserSet;

import io.rong.imkit.RongExtension;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

import static com.xiaomiquan.base.AppConst.serviceId;

/**
 * Created by 郭青枫 on 2017/11/14.
 * 聊天页面
 */
public class ConversationActivity extends BaseDataBindActivity<CustomerServiceActDelegate,IMBinder> {


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
        //SoftHideKeyBoardUtil.assistActivity(this);
        viewDelegate.setNoStatusBarFlag(false);
        initToolbar(new ToolbarBuilder().setTitle("客服中心"));
        setStatusBarLightOrNight(UserSet.getinstance().isNight());

        setWindowManagerLayoutParams(WindowManagerLayoutParamsNone);
        ConversationFragment fragment = new ConversationFragment();

        Conversation.ConversationType conversationType = null;


        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation")
                .appendPath(Conversation.ConversationType.CUSTOMER_SERVICE.getName().toLowerCase())
                .appendQueryParameter("targetId", serviceId).build();
        fragment.setUri(uri);

        /* 加载 ConversationFragment */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.rong_content, fragment);
        transaction.commit();

        RongExtension rongExtension = findViewById(R.id.rc_extension);


    }
    @Override
    protected void onResume() {
        super.onResume();
        binder.connect(SingSettingDBUtil.getUserLogin().getImToken());
    }
    @Override
    public IMBinder getDataBinder(CustomerServiceActDelegate viewDelegate) {
        return new IMBinder(viewDelegate) {
        };
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {

    }
}