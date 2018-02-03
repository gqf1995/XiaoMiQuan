package com.xiaomiquan.utils;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;

import com.xiaomiquan.entity.bean.ExchangeData;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 郭青枫 on 2018/2/1 0001.
 */

public class HandlerHelper {

    String nowWhat;
    ConcurrentHashMap<String, ExchangeData> nowExchangeDataMap;
    RecyclerView nowRecyclerView;
    Message message;

    private static class helper {
        private static HandlerHelper helper = new HandlerHelper();
    }

    public static HandlerHelper getinstance() {
        return HandlerHelper.helper.helper;
    }

    private HandlerHelper() {
    }

    private Handler handler = new Handler() {//进行延时跳转
        public void handleMessage(Message msg) {
            if (((String) msg.obj).equals(nowWhat)) {
                if (nowExchangeDataMap == null) {
                    return;
                }
                if (nowRecyclerView == null) {
                    return;
                }
                message = new Message();
                message.what = 0;
                message.obj = nowWhat;
                if (nowRecyclerView.getScrollState() != 0) {
                    //recycleView正在滑动
                } else {
                    //更新数据
                    Iterator iter = nowExchangeDataMap.entrySet().iterator();
                    while (iter.hasNext()) {
                        if (nowRecyclerView.getScrollState() != 0) {
                            handler.sendMessageDelayed(message, 1000);
                            return;
                        }
                        Map.Entry entry = (Map.Entry) iter.next();
                        ExchangeData val = (ExchangeData) entry.getValue();
                        String key = (String) entry.getKey();
                        if (val != null) {
                            mOnUpdataLinsener.onUpdataLinsener(val);
                            nowExchangeDataMap.remove(key);
                        } else {
                        }
                    }
                }
                handler.sendMessageDelayed(message, 1000);
            }
        }
    };

    public interface OnUpdataLinsener {
        void onUpdataLinsener(ExchangeData val);
    }

    OnUpdataLinsener mOnUpdataLinsener;

    public void initHander(String what, ConcurrentHashMap<String, ExchangeData> exchangeDataMap, RecyclerView recyclerView, OnUpdataLinsener onUpdataLinsener) {
        nowWhat = what;
        nowExchangeDataMap = exchangeDataMap;
        nowRecyclerView = recyclerView;
        mOnUpdataLinsener = onUpdataLinsener;
        message = new Message();
        message.what = 0;
        message.obj = nowWhat;
        handler.sendMessageDelayed(message, 1000);
    }

    public void put(String onlyKey, ExchangeData exchangeData) {
        nowExchangeDataMap.put(onlyKey, exchangeData);
    }

    public void onDestory() {
        handler.removeCallbacksAndMessages(null);//清空消息方便gc回收
    }

}
