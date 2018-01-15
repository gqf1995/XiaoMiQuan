package com.xiaomiquan.base;

import android.app.Activity;

import com.biv.BigImageViewer;
import com.biv.loader.glide.GlideImageLoader;
import com.blankj.utilcode.util.Utils;
import com.dhh.websocket.RxWebSocketUtil;
import com.fivefivelike.mybaselibrary.base.BaseApp;
import com.fivefivelike.mybaselibrary.utils.GlobleContext;
import com.fivefivelike.mybaselibrary.utils.logger.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.xiaomiquan.greenDaoUtils.DaoManager;
import com.xiaomiquan.mvp.activity.user.LoginAndRegisteredActivity;
import com.yanzhenjie.nohttp.NoHttp;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import okhttp3.OkHttpClient;
import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

import static com.xiaomiquan.base.AppConst.isLog;
import static com.xiaomiquan.base.AppConst.rongId;


/**
 * Created by 郭青枫 on 2017/9/25.
 */

public class Application extends BaseApp implements RongIMClient.OnReceiveMessageListener {

    @Override
    public void onCreate() {
        super.onCreate();
        //融云初始化
        initRongCloud();
        initClient();
    }

    private void initSkin() {
        SkinCompatManager.withoutActivity(this)                         // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(true)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(true)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
    }

    private void initRongCloud() {
        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIMClient 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))
                || "io.rong.push".equals(getCurProcessName(getApplicationContext()))
                ) {
            RongIM.init(this, rongId);
            RongIMClient.init(this, rongId);
            RongIM.getInstance().setMessageAttachedUserInfo(true);
        }

        //监听接收到的消息
        //RongIMClient.setOnReceiveMessageListener(this);
        RongIM.setOnReceiveMessageListener(this);
    }

    private void initClient() {
        //客户端进程中初始化操作
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            //初始化工具类集合
            Utils.init(this);
            GlobleContext.init(this);
            //initNohttp();
            //初始化数据库
            DaoManager.getInstance().init(this);
            NoHttp.initialize(this);
            BigImageViewer.initialize(GlideImageLoader.with(this));
            //开启log日志
            KLog.init(isLog);
            //初始化换肤
            initSkin();
        }
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        super.onTerminate();
    }

    @Override
    public void startCustomerService(Activity activity) {

    }

    public Class getLoginActivityClass() {
        return LoginAndRegisteredActivity.class;
    }


    @Override
    public boolean onReceived(Message message, int i) {
        return false;
    }
}
