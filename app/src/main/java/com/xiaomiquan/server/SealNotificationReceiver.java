package com.xiaomiquan.server;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.TypeReference;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ListUtils;
import com.xiaomiquan.entity.bean.MessageInfo;
import com.xiaomiquan.greenDB.MessageInfoDao;
import com.xiaomiquan.greenDaoUtils.DaoManager;
import com.xiaomiquan.mvp.activity.circle.ArticleDetailsActivity;
import com.xiaomiquan.mvp.activity.circle.TopicDetailActivity;
import com.xiaomiquan.mvp.activity.user.PersonalDetailsActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

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
        Map<String, String> map = GsonUtil.getInstance().toMap(message.getExtra(), new TypeReference<Map<String, String>>() {
        });
        if (!map.isEmpty()) {
            String type = map.get("type");
            if (!TextUtils.isEmpty(type)) {
                if ("1".equals(type) || "2".equals(type)) {
                    //1:点赞;2:评论或回复
                    //赞和回复
                    MessageInfo messageInfo = new MessageInfo();
                    messageInfo.setIsLook(false);
                    messageInfo.setMessage(message.getPushContent());
                    messageInfo.setPushId(message.getPushId());
                    messageInfo.setTime(message.getReceivedTime());
                    messageInfo.setType(type);
                    saveOrUpdataMessage(messageInfo, true);
                }
            }
        }
        return false; // 返回 false, 会弹出融云 SDK 默认通知; 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义。
    }

    private void saveOrUpdataMessage(final MessageInfo messageInfo, final boolean isSave) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (isSave) {
                    DaoManager.getInstance().getDaoSession().getMessageInfoDao().save(messageInfo);
                } else {
                    DaoManager.getInstance().getDaoSession().getMessageInfoDao().update(messageInfo);
                }
                EventBus.getDefault().post(messageInfo);
            }
        });
        thread.start();
    }

    /* push 通知点击事件 */
    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage message) {
        Map<String, String> map = GsonUtil.getInstance().toMap(message.getExtra(), new TypeReference<Map<String, String>>() {
        });
        if (!map.isEmpty()) {
            String type = map.get("type");
            if (!TextUtils.isEmpty(type)) {
                if ("1".equals(type) || "2".equals(type)) {
                    //赞和回复
                    List<MessageInfo> list = DaoManager.getInstance().getDaoSession().getMessageInfoDao()
                            .queryBuilder()
                            .where(MessageInfoDao.Properties.IsLook.eq(false))
                            .list();
                    if (!ListUtils.isEmpty(list)) {
                        MessageInfo messageInfo = list.get(0);
                        messageInfo.setLook(true);
                        saveOrUpdataMessage(messageInfo, false);
                    }
                    //跳转 帖子明细
                    // TODO: 2018/3/9 0009 帖子明细页面
                    if (map.get("themeType") != null) {
                        switch (map.get("themeType")) {
                            case "1":
                                TopicDetailActivity.startAct((Activity) context, null, map.get("linkId")+"");
                                break;
                            case "2":
                                ArticleDetailsActivity.startAct((Activity) context, null, map.get("linkId")+"");
                                break;
                            case "3":
                                break;
                        }
                    }

                    //                    点赞 回复 消息提醒  额外值
                    //                    JSONObject jsonObject = new JSONObject();
                    //                  jsonObject.put("type",1);//1:点赞;2:评论或回复
//                    jsonObject.put("isSkip",true);//是否要跳转
//                    jsonObject.put("linkId",articleTopic.getId());//帖子/文章id
//                    jsonObject.put("themeType",articleTopic.getType());//类型(1:文章;2:帖子;3:组合)
                    //
                    //                    跳转文章/帖子明细接口
                    //                    获取文章/帖子明细接口  参数为文章/帖子id
                    //                    http://localhost:8080/articleTopic/detail?id=1
                    //                    获取文章/帖子下 评论回复接口  参数：linkId 文章/帖子id；page 第几页
                    //                    http://localhost:8080/comment/listComment?linkId=1&page=1
                }
            }

            return true;
        } else {
            return false;
        }
        // 返回 false, 会走融云 SDK 默认处理逻辑, 即点击该通知会打开会话列表或会话界面; 返回 true, 则由您自定义处理逻辑。
    }
}