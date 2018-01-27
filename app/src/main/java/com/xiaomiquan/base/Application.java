package com.xiaomiquan.base;

import android.app.Activity;
import android.content.Context;

import com.biv.BigImageViewer;
import com.biv.loader.glide.GlideImageLoader;
import com.blankj.utilcode.util.Utils;
import com.fivefivelike.mybaselibrary.base.BaseApp;
import com.fivefivelike.mybaselibrary.utils.GlobleContext;
import com.fivefivelike.mybaselibrary.utils.logger.KLog;
import com.scichart.charting.visuals.SciChartSurface;
import com.xiaomiquan.greenDaoUtils.DaoManager;
import com.xiaomiquan.mvp.activity.user.LoginAndRegisteredActivity;
import com.yanzhenjie.nohttp.NoHttp;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
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
        mContext = getApplicationContext();
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
            String license = "<LicenseContract>" +
                    "<Customer>gqf1995@qq.com</Customer>" +
                    "<OrderId>Trial</OrderId>" +
                    "<LicenseCount>1</LicenseCount>" +
                    "<IsTrialLicense>true</IsTrialLicense>" +
                    "<SupportExpires>02/26/2018 00:00:00</SupportExpires>" +
                    "<ProductCode>SC-ANDROID-2D-ENTERPRISE-SRC</ProductCode>" +
                    "<KeyCode>ac58819db3418493bf1c4f9c80e95509a912d1debbf004bf2ad277de68c68066dc561c982dff0f20656003cdd4f6b3f52ca7cc71f5f81283b759d2945aa85fd7dee6f17a5149f784645c101e841939555439315dd199c367fe12fe124eefef1c3a84077a860a0b81e63681b00661972d181c3495201bb89fe12e0cf362b7124dcf6632e38b15e7fd0541e28b5933b451cc9fe31dcd394173c3712ea2caa9016249a4056f56e6dda0</KeyCode>" +
                    "</LicenseContract>";
            try {
                SciChartSurface.setRuntimeLicenseKey(license);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }
}
