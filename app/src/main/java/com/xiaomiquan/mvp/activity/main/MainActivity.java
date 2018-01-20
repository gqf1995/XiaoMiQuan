package com.xiaomiquan.mvp.activity.main;

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
import com.xiaomiquan.base.ExchangeRateUtil;
import com.xiaomiquan.server.HttpUrl;

public class MainActivity extends BaseDataBindActivity<MainDelegate, MainBinder> {

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
        initFragment();
        //initSocket();
        if (!ExchangeRateUtil.getinstance().IsHavaData()) {
            //获取汇率
            addRequest(binder.getAllPriceRate(this));
        }
    }

    private void initSocket() {
        WebSocketRequest.getInstance().intiWebSocket("ws://47.97.169.136:1903/ws/" + DeviceUtils.getAndroidID(), this.getClass().getName(), new WebSocketRequest.WebSocketCallBack() {
            @Override
            public void onDataSuccess(String data, String info, int status) {

            }

            @Override
            public void onDataError(String data, String info, int status) {

            }
        });
        WebSocketRequest.getInstance().setRegisterUrl(HttpUrl.getIntance().registerkeys);
        WebSocketRequest.getInstance().setUnregisterUrl(HttpUrl.getIntance().unregisterkeys);
        WebSocketRequest.getInstance().unregister("");
    }

    public void initFragment() {
        viewDelegate.initAddFragment(R.id.fl_root, getSupportFragmentManager());
        viewDelegate.addFragment(new MarketFragment());
        viewDelegate.addFragment(new CircleFragment());
        viewDelegate.addFragment(new Fragment());
        viewDelegate.addFragment(new UserFragment());
        viewDelegate.showFragment(0);
        doubleClickActList.add(this.getClass().getName());//两次返回act注册
    }

    @Override
    protected void onDestroy() {
        WebSocketRequest.getInstance().onDestory();
        super.onDestroy();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                ExchangeRateUtil.getinstance().upData(data);
                break;
        }

    }

}
