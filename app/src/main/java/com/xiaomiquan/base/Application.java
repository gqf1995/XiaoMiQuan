package com.xiaomiquan.base;

import android.app.Activity;
import android.graphics.Typeface;

import com.biv.BigImageViewer;
import com.biv.loader.glide.GlideImageLoader;
import com.blankj.utilcode.util.Utils;
import com.fivefivelike.mybaselibrary.base.BaseApp;
import com.fivefivelike.mybaselibrary.utils.GlobleContext;
import com.fivefivelike.mybaselibrary.utils.logger.KLog;
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.squareup.leakcanary.LeakCanary;
import com.xiaomiquan.mvp.activity.user.LoginAndRegisteredActivity;
import com.yanzhenjie.nohttp.NoHttp;

import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

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

    private void initTypefacehelper() {
        TypefaceCollection typeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "font/Exo2-Regular.ttf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(), "font/Exo2-Bold.ttf"))
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(), "font/Exo2-BoldItalic.ttf"))
                .set(Typeface.ITALIC, Typeface.createFromAsset(getAssets(), "font/Exo2-Italic.ttf"))
                .create();
        TypefaceHelper.init(typeface);
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
            //初始化换肤
            initSkin();
            //初始化字体
            initTypefacehelper();
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


}
