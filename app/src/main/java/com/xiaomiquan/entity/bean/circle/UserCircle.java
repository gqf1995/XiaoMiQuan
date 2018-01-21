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

    private String id;
    private String groupNum;
    private String userId;
    private String name;
    private String avatar;
    private String type;
    private String brief;
    private String status;
    private String createTime;
    private String updateTime;
    private String nickName;
    private String memberCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(String memberCount) {
        this.memberCount = memberCount;
    }
}
