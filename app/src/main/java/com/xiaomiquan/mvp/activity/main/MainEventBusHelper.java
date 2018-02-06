package com.xiaomiquan.mvp.activity.main;

import com.fivefivelike.mybaselibrary.entity.ResultDialogEntity;
import com.fivefivelike.mybaselibrary.utils.ActUtil;
import com.xiaomiquan.mvp.databinder.MainBinder;
import com.xiaomiquan.mvp.delegate.MainDelegate;

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
            activity.toPage(2,3);
        }
    }
}
