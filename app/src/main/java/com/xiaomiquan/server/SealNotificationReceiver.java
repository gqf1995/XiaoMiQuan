package com.xiaomiquan.server;

import android.content.Context;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by 郭青枫 on 2017/11/16.
 */

public class SealNotificationReceiver extends PushMessageReceiver {
    /* push 通知到达事件*/
    public static final String tag_group = "tag_group";

    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage message) {

        return false; // 返回 false, 会弹出融云 SDK 默认通知; 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义。
    }

    /* push 通知点击事件 */
    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage message) {

        return false;
        // 返回 false, 会走融云 SDK 默认处理逻辑, 即点击该通知会打开会话列表或会话界面; 返回 true, 则由您自定义处理逻辑。
    }
}