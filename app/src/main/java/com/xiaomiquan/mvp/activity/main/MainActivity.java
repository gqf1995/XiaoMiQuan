package com.xiaomiquan.mvp.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;

import com.blankj.utilcode.util.DeviceUtils;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.http.WebSocketRequest;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.MainBinder;
import com.xiaomiquan.mvp.delegate.MainDelegate;
import com.xiaomiquan.mvp.fragment.CircleFragment;
import com.xiaomiquan.mvp.fragment.MarketFragment;
import com.xiaomiquan.mvp.fragment.UserFragment;
import com.xiaomiquan.server.HttpUrl;
import com.xiaomiquan.utils.BigUIUtil;

public class MainActivity extends BaseDataBindActivity<MainDelegate, MainBinder> {

    String uid;

    @Override
    protected Class<MainDelegate> getDelegateClass() {
        return MainDelegate.class;
    }

    @Override
    public MainBinder getDataBinder(MainDelegate viewDelegate) {
        return new MainBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        //提示是否电池优化
        ignoreBatteryOptimization(this);
        initFragment();
        uid = DeviceUtils.getAndroidID() + System.currentTimeMillis();
        //initSocket();
        updata();
    }

    private Handler handler = new Handler() {//进行延时跳转
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    updata();
                    break;
            }
        }
    };

    private void updata() {
        addRequest(binder.getAllPriceRate(this));
        handler.sendEmptyMessageDelayed(1, 15000);
    }

    private void initSocket() {
        WebSocketRequest.getInstance().intiWebSocket("ws://13.231.38.47:1903/ws/" + uid, this.getClass().getName(), new WebSocketRequest.WebSocketCallBack() {
            @Override
            public void onDataSuccess(String name, String data, String info, int status) {

            }

            @Override
            public void onDataError(String name, String data, String info, int status) {

            }
        });
        WebSocketRequest.getInstance().setRegisterUrl(HttpUrl.getIntance().registerkeys);
        WebSocketRequest.getInstance().setUnregisterUrl(HttpUrl.getIntance().unregisterkeys);
        WebSocketRequest.getInstance().setUid(uid);
        WebSocketRequest.getInstance().unregister("");
    }

    //添加主页4个基础页面
    public void initFragment() {
        //设置 以哪个FrameLayout 作为展示
        viewDelegate.initAddFragment(R.id.fl_root, getSupportFragmentManager());
        viewDelegate.addFragment(new MarketFragment());
        viewDelegate.addFragment(new CircleFragment());
        viewDelegate.addFragment(new Fragment());
        viewDelegate.addFragment(new UserFragment());
        //显示第0个
        viewDelegate.showFragment(0);
        doubleClickActList.add(this.getClass().getName());//两次返回推出act注册
    }

    @Override
    protected void onDestroy() {
        WebSocketRequest.getInstance().onDestory();
        super.onDestroy();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                //更新本地缓存汇率表
                BigUIUtil.getinstance().upData(data);
                break;
            case 0x124:

                break;
        }
    }

    public void ignoreBatteryOptimization(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            boolean hasIgnored = powerManager.isIgnoringBatteryOptimizations(activity.getPackageName());
            //  判断当前APP是否有加入电池优化的白名单，如果没有，弹出加入电池优化的白名单的设置对话框。
            if (!hasIgnored) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                startActivity(intent);
            }
        }
    }

}
