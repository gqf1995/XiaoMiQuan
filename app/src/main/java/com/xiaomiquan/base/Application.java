package com.xiaomiquan.base;

import android.app.Activity;

import com.biv.BigImageViewer;
import com.biv.loader.glide.GlideImageLoader;
import com.blankj.utilcode.util.Utils;
import com.fivefivelike.mybaselibrary.base.BaseApp;
import com.fivefivelike.mybaselibrary.utils.GlobleContext;
import com.fivefivelike.mybaselibrary.utils.logger.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.yanzhenjie.nohttp.NoHttp;

import static com.xiaomiquan.base.AppConst.isLog;


/**
 * Created by 郭青枫 on 2017/9/25.
 */

public class Application extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        initClient();
    }

    private void initClient() {
        //客户端进程中初始化操作
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            //初始化工具类集合
            Utils.init(this);
            GlobleContext.init(this);
            //initNohttp();
            NoHttp.initialize(this);
            BigImageViewer.initialize(GlideImageLoader.with(this));
            //开启log日志
            KLog.init(isLog);
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
        return null;
    }



}
