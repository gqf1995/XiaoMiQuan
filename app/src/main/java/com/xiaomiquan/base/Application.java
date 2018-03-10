package com.xiaomiquan.base;

import android.app.Activity;
import android.content.Context;

import com.biv.BigImageViewer;
import com.biv.loader.glide.GlideImageLoader;
import com.blankj.utilcode.util.Utils;
import com.fivefivelike.mybaselibrary.base.BaseApp;
import com.fivefivelike.mybaselibrary.http.WebSocketRequest;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GlobleContext;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.glide.GlideAlbumLoader;
import com.fivefivelike.mybaselibrary.utils.logger.KLog;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.event.ChatControlEvent;
import com.xiaomiquan.entity.bean.event.CustomerServiceEvent;
import com.xiaomiquan.greenDaoUtils.DaoManager;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.user.LoginAndRegisteredActivity;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.nohttp.NoHttp;

import java.util.Locale;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.CSCustomServiceInfo;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;
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
        //设置配置画廊可以加载网络图片
        Album.initialize(
                AlbumConfig.newBuilder(this)
                        .setAlbumLoader(new GlideAlbumLoader()) // 设置Album加载器。
                        .setLocale(Locale.CHINA) // 比如强制设置在任何语言下都用中文显示。
                        .build()
        );
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
    }

    //客户服务
    public void startCustomerService(Activity activity) {
        if (SingSettingDBUtil.getUserLogin() != null) {
            CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
            CSCustomServiceInfo csInfo = csBuilder.nickName(SingSettingDBUtil.getUserLogin().getNickName()).build();
            RongIM.getInstance().startCustomerServiceChat(activity, "KEFU151728371459995", "客服中心", csInfo);
        } else {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
        }
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        WebSocketRequest.getInstance().onDestory();
        super.onTerminate();
    }

    public Class getLoginActivityClass() {
        return LoginAndRegisteredActivity.class;
    }


    @Override
    public boolean onReceived(Message message, int i) {
        MessageContent messageContent = message.getContent();
        if (messageContent instanceof TextMessage) {
            if (message.getConversationType() == Conversation.ConversationType.CUSTOMER_SERVICE) {
                org.greenrobot.eventbus.EventBus.getDefault().post(new CustomerServiceEvent(CommonUtils.getString(R.string.str_get_customer_service_msg), ((TextMessage) messageContent).getContent()));
            } else if (message.getConversationType() == Conversation.ConversationType.GROUP) {
                String extra = ((TextMessage) messageContent).getExtra();
                if ("mgp:open".equals(extra)) {
                    //聊天室开启
                    org.greenrobot.eventbus.EventBus.getDefault().post(new ChatControlEvent(false, extra));
                } else if ("mgp:close".equals(extra)) {
                    //聊天室关闭
                    org.greenrobot.eventbus.EventBus.getDefault().post(new ChatControlEvent(true, extra));
                }
                return true;
            }
        }
        return false;
    }

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }
}
