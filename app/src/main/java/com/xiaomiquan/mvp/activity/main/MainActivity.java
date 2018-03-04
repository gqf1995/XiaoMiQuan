package com.xiaomiquan.mvp.activity.main;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.circledialog.CircleDialogHelper;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.http.WebSocketRequest;
import com.fivefivelike.mybaselibrary.utils.ActUtil;
import com.fivefivelike.mybaselibrary.utils.AppUtil;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.UUIDS;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.AppVersion;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.databinder.MainBinder;
import com.xiaomiquan.mvp.delegate.IMDelegate;
import com.xiaomiquan.mvp.delegate.MainDelegate;
import com.xiaomiquan.mvp.dialog.UpdateDialog;
import com.xiaomiquan.mvp.fragment.HomeFragment;
import com.xiaomiquan.mvp.fragment.MarketFragment;
import com.xiaomiquan.mvp.fragment.UserFragment;
import com.xiaomiquan.mvp.fragment.circle.CircleFragment;
import com.xiaomiquan.mvp.fragment.group.InvestGroupFragment;
import com.xiaomiquan.server.HttpUrl;
import com.xiaomiquan.server.UpdateService;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.utils.HandlerHelper;
import com.xiaomiquan.utils.PingUtil;
import com.xiaomiquan.utils.UiHeplUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends BaseDataBindActivity<MainDelegate, MainBinder> implements MarketFragment.OnHttpChangeLinsener, UserFragment.Linsener {

    String uid;
    UserLogin userLogin;
    MainEventBusHelper mainEventBusHelper;
    AppVersion appVersion;
    int showPosition = 0;

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
        mainEventBusHelper = new MainEventBusHelper(this, viewDelegate, binder);
        uid = UUIDS.getUUID() + System.currentTimeMillis();
        initSocket();
        updata();
        netWorkLinsener();
        addRequest(binder.getlatestversion(AppUtils.getAppVersionName(), this));
    }

    @Override
    protected void bindEvenListenerBuyState(Bundle savedInstanceState) {
        super.bindEvenListenerBuyState(savedInstanceState);
        if (savedInstanceState != null) {
            showPosition = savedInstanceState.getInt("position", 0);
        }
        initFragment(savedInstanceState == null);
    }

    PingUtil.NetworkConnectChangedReceiver mNetworkChangeListener;

    private void netWorkLinsener() {
        mNetworkChangeListener = new PingUtil.NetworkConnectChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(mNetworkChangeListener, filter);
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

    @Override
    protected void onStop() {
        super.onStop();
        isLoad = false;
        DeviceUtils.getAndroidID();
        WebSocketRequest.getInstance().sendData(new ArrayList<String>());
    }

    boolean isLoad = true;//是否更新汇率

    private void updata() {
        if (isLoad) {
            addRequest(binder.getAllPriceRate(this));
        }
        handler.sendEmptyMessageDelayed(1, 15000);
    }

    public void initSocket() {
        WebSocketRequest.getInstance().intiWebSocket("ws:" + "//topcoin.bicoin.com.cn/ws/" + uid, this.getClass().getName(), new WebSocketRequest.WebSocketCallBack() {
            @Override
            public void onDataSuccess(String name, String data, String info, int status) {

            }

            @Override
            public void onDataError(String name, String data, String info, int status) {

            }
        });
        WebSocketRequest.getInstance().setRegisterUrl(HttpUrl.getIntance().registerkeys);
        WebSocketRequest.getInstance().setUid(uid);
    }

    MarketFragment marketFragment;
    CircleFragment squareFragment;
    InvestGroupFragment investGroupFragment;
    UserFragment userFragment;
    HomeFragment homeFragment;

    //添加主页4个基础页面
    public void initFragment(boolean isInit) {
        //设置 以哪个FrameLayout 作为展示
        viewDelegate.initAddFragment(R.id.fl_root, getSupportFragmentManager());
        //viewDelegate.addFragment(squareFragment = new CircleFragment());
        if (isInit) {
            viewDelegate.addFragment(homeFragment = new HomeFragment());
            viewDelegate.addFragment(marketFragment = new MarketFragment());
            viewDelegate.addFragment(userFragment = new UserFragment());
            loadDrawerLayout(true);
        } else {
            viewDelegate.initFromSave();
            homeFragment = (HomeFragment) viewDelegate.getFragmentByIndex(0);
            marketFragment = (MarketFragment) viewDelegate.getFragmentByIndex(1);
            userFragment = (UserFragment) viewDelegate.getFragmentByIndex(2);
            loadDrawerLayout(false);
        }
        viewDelegate.showFragment(showPosition);
        initBottom();
        doubleClickActList.add(this.getClass().getName());//两次返回推出act注册
    }

    private void loadDrawerLayout(boolean isFirst) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isFirst) {
            userFragment = new UserFragment();
            transaction.add(R.id.fl_left, userFragment, "UserFragment");
        } else {
            userFragment = (UserFragment) getSupportFragmentManager().findFragmentByTag("UserFragment");
            transaction.show(userFragment);
        }
        transaction.commitAllowingStateLoss();
    }


    private void initBottom() {
        viewDelegate.initBottom(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewDelegate.showFragment(position);
                if (position != 1) {
                    WebSocketRequest.getInstance().sendData(new ArrayList<String>());
                } else {
                    marketFragment.sendWebsocket();
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewDelegate.viewHolder.tl_2.setCurrentTab(showPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("position", viewDelegate.viewHolder.tl_2.getCurrentTab());
        super.onSaveInstanceState(outState);
    }

    public void toPage(int pagePosition, int childPosition) {
        viewDelegate.showFragment(pagePosition);
        viewDelegate.viewHolder.tl_2.setCurrentTab(pagePosition);
        if (pagePosition == 0) {
            InvestGroupFragment fragment = (InvestGroupFragment) viewDelegate.getFragmentList().get(pagePosition);
            fragment.toPage(childPosition);
        }
    }

    @Override
    protected void onDestroy() {
        HandlerHelper.getinstance().onDestory();
        mainEventBusHelper.onDestroy();
        super.onDestroy();
    }

    IMDelegate.IMLinsener imLinsener = new IMDelegate.IMLinsener() {
        @Override
        public void ImError() {
            addRequest(binder.imToken(MainActivity.this));
        }

        @Override
        public void ImSuccess() {
            if (userLogin != null) {
                //添加用户信息 到消息体中
                RongIM.getInstance().setCurrentUserInfo(new UserInfo(userLogin.getId() + "u", userLogin.getNickName(), Uri.parse(GlideUtils.getBaseUrl() + userLogin.getAvatar())));
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        initIm();
        isLoad = true;
        if (viewDelegate.getCuurentFragmentPosition() == 0) {
            marketFragment.sendWebsocket();
        }
    }

    private void initIm() {
        userLogin = SingSettingDBUtil.getUserLogin();
        if (userLogin != null) {
            if (!TextUtils.isEmpty(userLogin.getImToken())) {
                viewDelegate.setImLinsener(imLinsener);
                binder.connect(userLogin.getImToken());
            } else {
                addRequest(binder.imToken(this));
            }
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                //更新本地缓存汇率表
                BigUIUtil.getinstance().upData(data);
                break;
            case 0x125:
                String token = GsonUtil.getInstance().getValue(data, "token");
                if (!TextUtils.isEmpty(token)) {
                    if (userLogin != null) {
                        userLogin.setImToken(token);
                        SingSettingDBUtil.setNewUserLogin(userLogin);
                        initIm();
                    }
                }
                break;
            case 0x126:
                //版本更新
                appVersion = GsonUtil.getInstance().toObj(data, AppVersion.class);
                version();
        }
    }

    private void updataApp() {
        UpdateService.
                Builder.create(appVersion.getDownloadAddr())
                .setStoreDir("update")
                .setIcoResId(R.mipmap.artboard)
                .setDownloadSuccessNotificationFlag(Notification.DEFAULT_ALL)
                .setDownloadErrorNotificationFlag(Notification.DEFAULT_ALL)
                .setAppVersion(appVersion)
                .build(mContext);
    }

    private void version() {
        AndPermission.with(this)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        if (UiHeplUtils.compareVersion(appVersion.getSystemVersion(), AppUtils.getAppVersionName()) == 1) {
                            new UpdateDialog(MainActivity.this)
                                    .setAppVersion(appVersion)
                                    .setDefaultClickLinsener(new DefaultClickLinsener() {
                                        @Override
                                        public void onClick(View view, int position, Object item) {
                                            if (position == 0) {
                                                //取消
                                                if (appVersion.isMustUpdate()) {
                                                    ActUtil.getInstance().AppExit(MainActivity.this);
                                                }
                                            } else {
                                                //确认
                                                if (AppUtil.isWifi(mContext)) {
                                                    updataApp();
                                                } else {
                                                    CircleDialogHelper.initDefaultDialog(MainActivity.this, "当前处于非wifi模式，是否继续下载?", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            updataApp();
                                                        }
                                                    }).setNegative("取消", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            if (appVersion.isMustUpdate()) {
                                                                ActUtil.getInstance().AppExit(MainActivity.this);
                                                            }
                                                        }
                                                    }).show();
                                                }
                                            }
                                        }
                                    }).showDialog();
                        } else {
                            //新手引导
                        }
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        ToastUtil.show("没有读写权限,请开启权限");
                    }
                }).start();
    }

    @Override
    public void logout() {
        RongIMClient.getInstance().logout();
        RongIM.getInstance().logout();
    }

    public void ignoreBatteryOptimization(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            boolean hasIgnored = powerManager.isIgnoringBatteryOptimizations(activity.getPackageName());
            //  判断当前APP是否有加入电池优化的白名单，如果没有，弹出加入电池优化的白名单的设置对话框。
            if (!hasIgnored) {
                try {
                    Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + activity.getPackageName()));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
