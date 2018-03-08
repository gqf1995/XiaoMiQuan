package com.xiaomiquan.mvp.activity.main;

import android.view.View;

import com.circledialog.CircleDialogHelper;
import com.circledialog.callback.ConfigText;
import com.circledialog.params.TextParams;
import com.fivefivelike.mybaselibrary.entity.ResultDialogEntity;
import com.fivefivelike.mybaselibrary.utils.ActUtil;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.AppVersion;
import com.xiaomiquan.entity.bean.event.CustomerServiceEvent;
import com.xiaomiquan.mvp.activity.user.ConversationActivity;
import com.xiaomiquan.mvp.databinder.MainBinder;
import com.xiaomiquan.mvp.delegate.MainDelegate;
import com.xiaomiquan.server.NotificationHelper;
import com.xiaomiquan.utils.UiHeplUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by 郭青枫 on 2017/11/24.
 */

public class MainEventBusHelper {

    MainActivity activity;
    MainDelegate viewDelegate;
    MainBinder binder;


    public MainEventBusHelper(MainActivity activity, MainDelegate viewDelegate, MainBinder binder) {
        EventBus.getDefault().register(this);
        this.activity = activity;
        this.viewDelegate = viewDelegate;
        this.binder = binder;
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }


    //返回弹窗点击事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResuleDialgoEvent(ResultDialogEntity event) {
        if ("0".equals(event.getCode())) {
            //去投资组合
            ActUtil.getInstance().killAllActivity(activity);
            activity.toPage(0, 3);
        } else if ("1".equals(event.getCode())) {
            //去大赛页面
            activity.toPage(0, 1);
        }
    }
    //客服消息
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCustomerServiceEvent(CustomerServiceEvent event) {
        if (!ActUtil.getInstance().getTopActivity().getClass().equals(ConversationActivity.class)) {
            NotificationHelper.getInstence().sentCoustomerServiceNotification(activity, event, R.mipmap.artboard);
        }
    }
    //app更新是否失败
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onAppVersionEvent(final AppVersion event) {
        CircleDialogHelper.initDefaultDialog(activity, CommonUtils.getString(R.string.update_app_error), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiHeplUtils.startWeb(activity, event.getDownloadAddr());
            }
        })
                .configText(new ConfigText() {
                    @Override
                    public void onConfig(TextParams params) {
                        params.textColor = activity.getResources().getColor(R.color.color_font2);
                    }
                }).setNegative(CommonUtils.getString(R.string.str_cancel), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.appVersion.isMustUpdate()) {
                    ActUtil.getInstance().AppExit(activity);
                }
            }
        }).show();
    }

}
