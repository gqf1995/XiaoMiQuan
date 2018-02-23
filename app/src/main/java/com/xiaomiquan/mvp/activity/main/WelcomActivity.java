package com.xiaomiquan.mvp.activity.main;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.mvp.databinder.WelcomeBinder;
import com.xiaomiquan.mvp.delegate.WelcomDelegate;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.utils.UserSet;


public class WelcomActivity extends BaseDataBindActivity<WelcomDelegate, WelcomeBinder> {

    @Override
    public WelcomeBinder getDataBinder(WelcomDelegate viewDelegate) {
        return new WelcomeBinder(viewDelegate);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);//清空消息方便gc回收
        super.onDestroy();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                //更新本地缓存汇率表
                BigUIUtil.getinstance().upData(data);
                break;
        }
    }

    @Override
    protected Class<WelcomDelegate> getDelegateClass() {
        return WelcomDelegate.class;
    }

    private Handler handler = new Handler() {//进行延时跳转
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    doAct();
                    break;
            }
        }
    };

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //viewDelegate.viewHolder.iv_pic.setImageResource(R.drawable.welcom);
        handler.sendEmptyMessageDelayed(1, 1000);
        addRequest(binder.getAllPriceRate(this));
        if (!UserSet.getinstance().isFirst()) {
            UserSet.getinstance().setIsFirst(true);
        }
        //        HttpUrl.setBaseUrl(httpBaseUrl4);
    }

    private void doAct() {
        handler.removeCallbacksAndMessages(null);//清空消息方便gc回收
        startActivity(new Intent(WelcomActivity.this, MainActivity.class));
        finish();
    }

}
