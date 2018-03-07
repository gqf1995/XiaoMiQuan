package com.xiaomiquan.entity.bean.event;

/**
 * Created by 郭青枫 on 2018/3/7 0007.
 */

public class ChatControlEvent {
    boolean isClose;
    String extra;
    String id;

    public ChatControlEvent(
            boolean isClose,
            String extra) {
        this.extra = extra;
        this.isClose = isClose;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
