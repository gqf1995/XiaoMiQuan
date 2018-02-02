package com.xiaomiquan.entity.bean.circle;

/**
 * Created by Andy on 2018/1/29.
 */

public class CirclePerson {

    /**
     * id : 3
     * groupId : 1
     * userId : 2
     * groupRole : 2
     * status : nulls
     * createTime : 1517201009000
     * updateTime : null
     * nickName : 阿拉斯加
     * avatar : https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1121475478,2545730346&fm=27&gp=0.jpg
     * statusStr : null
     * groupRoleStr : null
     */

    private String id;
    private String groupId;
    private String userId;
    private String groupRole;
    private String status;
    private String createTime;
    private String updateTime;
    private String nickName;
    private String avatar;
    private String statusStr;
    private String groupRoleStr;
    private String banned;

    public String getBanned() {
        return banned;
    }

    public void setBanned(String banned) {
        this.banned = banned;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupRole() {
        return groupRole;
    }

    public void setGroupRole(String groupRole) {
        this.groupRole = groupRole;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getGroupRoleStr() {
        return groupRoleStr;
    }

    public void setGroupRoleStr(String groupRoleStr) {
        this.groupRoleStr = groupRoleStr;
    }
}
