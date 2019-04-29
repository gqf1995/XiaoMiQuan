package com.xiaomiquan.entity.bean.chat;

/**
 * Created by 郭青枫 on 2018/3/5 0005.
 */

public class ChatLiveItem {
    /**
     * avatar : http://topcoin.oss-cn-hangzhou.aliyuncs.com/headimg/13.png
     * brief : 一根风趣幽默的老韭菜
     * canJoin : true
     * canSpeak : true
     * groupId : 17
     * groupName : btc319RgU
     * nickName : btc319RgU
     * onlineTotal : 2000
     * onlineTotalStr : 2000在线
     * reason :
     * sort : 2
     * sortStr : 大赛排名 02
     * status : 0
     * statusStr :
     * title : 垃圾项目这么多
     * userId : 17
     */

    private String avatar;
    private String brief;
    private boolean canJoin;
    private boolean canSpeak;
    private String groupId;
    private String groupName;
    private String nickName;
    private int onlineTotal;
    private String onlineTotalStr;
    private String reason;
    private int sort;
    private String sortStr;
    private int status;
    private String statusStr;
    private String title;
    private String backgroundImg;
    private int userId;

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public boolean isCanJoin() {
        return canJoin;
    }

    public void setCanJoin(boolean canJoin) {
        this.canJoin = canJoin;
    }

    public boolean isCanSpeak() {
        return canSpeak;
    }

    public void setCanSpeak(boolean canSpeak) {
        this.canSpeak = canSpeak;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getOnlineTotal() {
        return onlineTotal;
    }

    public void setOnlineTotal(int onlineTotal) {
        this.onlineTotal = onlineTotal;
    }

    public String getOnlineTotalStr() {
        return onlineTotalStr;
    }

    public void setOnlineTotalStr(String onlineTotalStr) {
        this.onlineTotalStr = onlineTotalStr;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getSortStr() {
        return sortStr;
    }

    public void setSortStr(String sortStr) {
        this.sortStr = sortStr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
