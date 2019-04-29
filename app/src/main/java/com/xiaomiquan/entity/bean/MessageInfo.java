package com.xiaomiquan.entity.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 郭青枫 on 2018/3/9 0009.
 */
@Entity
public class MessageInfo {

    @Id(autoincrement = true)
    Long id;
    String message;
    long time;
    boolean isLook;
    String pushId;
    String type;

    @Generated(hash = 1300516598)
    public MessageInfo(Long id, String message, long time, boolean isLook,
            String pushId, String type) {
        this.id = id;
        this.message = message;
        this.time = time;
        this.isLook = isLook;
        this.pushId = pushId;
        this.type = type;
    }

    @Generated(hash = 1292770546)
    public MessageInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isLook() {
        return isLook;
    }

    public void setLook(boolean look) {
        isLook = look;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsLook() {
        return this.isLook;
    }

    public void setIsLook(boolean isLook) {
        this.isLook = isLook;
    }
}
