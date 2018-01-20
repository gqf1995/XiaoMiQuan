package com.xiaomiquan.entity.bean.circle;

import java.io.Serializable;

/**
 * Created by Andy on 2018/1/19.
 */

public class UserCircle implements Serializable {

    /**
     * id : 3
     * groupNum : 20180120100750
     * userId : 1
     * name : 圈子名称
     * avatar : 圈子头像
     * type : 圈子类型
     * brief : 圈子简介
     * status : 1
     * createTime : 1516414070000
     * updateTime : null
     * nickName : null
     * memberCount : 3
     */

    private int id;
    private String groupNum;
    private int userId;
    private String name;
    private String avatar;
    private String type;
    private String brief;
    private int status;
    private long createTime;
    private Object updateTime;
    private Object nickName;
    private int memberCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getNickName() {
        return nickName;
    }

    public void setNickName(Object nickName) {
        this.nickName = nickName;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }
}
