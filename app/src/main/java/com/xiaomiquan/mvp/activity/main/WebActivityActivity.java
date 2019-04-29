package com.xiaomiquan.mvp.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.base.BaseWebFragment;
import com.fivefivelike.mybaselibrary.entity.ResultDialogEntity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.chat.ChatLiveItem;
import com.xiaomiquan.entity.bean.chat.CheckScore;
import com.xiaomiquan.mvp.activity.chat.ChatLiveListActivity;
import com.xiaomiquan.mvp.activity.chat.GroupChatActivity;
import com.xiaomiquan.mvp.activity.group.HisAccountActivity;
import com.xiaomiquan.mvp.databinder.WebActivityBinder;
import com.xiaomiquan.mvp.delegate.WebActivityDelegate;
import com.xiaomiquan.widget.CircleDialogHelper;

import org.greenrobot.eventbus.EventBus;

public class WebActivityActivity extends BaseDataBindActivity<WebActivityDelegate, WebActivityBinder> {

    BaseWebFragment baseWebFragment;

    @Override
    protected Class<WebActivityDelegate> getDelegateClass() {
        return WebActivityDelegate.class;
    }

    @Override
    public WebActivityBinder getDataBinder(WebActivityDelegate viewDelegate) {
        return new WebActivityBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        getIntentData();
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(""));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().findFragmentByTag("BaseWebFragment") == null) {
            baseWebFragment = BaseWebFragment.newInstance(type);
            transaction.add(R.id.fl_root, baseWebFragment, "BaseWebFragment");
        } else {
            baseWebFragment = (BaseWebFragment) getSupportFragmentManager().findFragmentByTag("BaseWebFragment");
            transaction.show(baseWebFragment);
        }
        transaction.commitAllowingStateLoss();

        viewDelegate.getmToolbarBackLin().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!baseWebFragment.goBack()) {
                    onBackPressed();
                }
            }
        });
        baseWebFragment.setWebLinsener(new BaseWebFragment.WebLinsener() {
            @Override
            public void onLoadEndPage() {
                bridgeWeb();
            }

            @Override
            public void onLoadTitle(String title) {

            }
        });
    }

    ChatLiveItem chatLiveItem;
    BridgeWebView mBridgeWebView;

    private void bridgeWeb() {
        if (mBridgeWebView == null) {
            mBridgeWebView = baseWebFragment.getmBridgeWebView();
            mBridgeWebView.registerHandler("WebToLocal", new BridgeHandler() {
                @Override
                public void handler(String data, CallBackFunction function) {
                    String forward = GsonUtil.getInstance().getValue(data, "forward");
                    if ("bcoin://chatList".equals(forward)) {
                        //跳转聊天室列表
                        startActivity(new Intent(WebActivityActivity.this, ChatLiveListActivity.class));
                    } else if ("bcoin://virCoin".equals(forward)) {
                        //跳转投资组合
                        ResultDialogEntity resultDialogEntity = new ResultDialogEntity();
                        resultDialogEntity.setCode("0");
                        EventBus.getDefault().post(resultDialogEntity);
                    } else if ("bcoin://chatRoom".equals(forward)) {
                        //点击参与进入具体聊天室
                        String parameters = GsonUtil.getInstance().getValue(data, "parameters");
                        chatLiveItem = GsonUtil.getInstance().toObj(parameters, ChatLiveItem.class);
                        addRequest(binder.checkScore(chatLiveItem.getGroupId(), WebActivityActivity.this));
                    } else if ("bcoin://showAccount".equals(forward)) {
                        //点击进入某用户组合详情页,参数userId、type标示在详情页面默认打开的tab
                        String parameters = GsonUtil.getInstance().getValue(data, "parameters");
                        String userId = GsonUtil.getInstance().getValue(parameters, "userId");
                        String type = GsonUtil.getInstance().getValue(parameters, "type");
                        HisAccountActivity.startAct(WebActivityActivity.this, userId, type);
                    } else {
                        //跳转web页面
                        WebActivityActivity.startAct(WebActivityActivity.this, forward);
                    }
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (!baseWebFragment.goBack()) {
                onBackPressed();
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public static void startAct(Activity activity,
                                String type
    ) {
        Intent intent = new Intent(activity, WebActivityActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    private String type;

    private void getIntentData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            baseWebFragment.loadUrl(type);
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x124:
                CheckScore checkScore = GsonUtil.getInstance().toObj(data, CheckScore.class);
                if (!checkScore.isJoinGroup()) {
                    CircleDialogHelper.initDefaultToastDialog(this, CommonUtils.getString(R.string.str_toast_cannot_join_group), null)
                            .show();
                    return;
                }
                GroupChatActivity.startAct(this,
                        chatLiveItem.getGroupId(),
                        chatLiveItem.getGroupName(),
                        chatLiveItem.getAvatar(),
                        chatLiveItem.getTitle(),
                        chatLiveItem.getOnlineTotal() + "",
                        checkScore.isLeader(),
                        checkScore.isCanSpeak(),
                        0x123
                );
                break;
        }
    }

}
