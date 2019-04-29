package com.xiaomiquan.entity.bean.group;

/**
 * Created by 郭青枫 on 2018/2/11 0011.
 */

public class HotTeam {
    /**
     * average : 0
     * averageStr :
     * bonusPool : 0
     * bonusPoolStr :
     * createTime : 1518341618000
     * id : 2
     * nickName :
     * ownerId : 3
     * remark : 战队简介
     * teamCount : 0
     * teamName : 琅琊战队
     */

    private String average;
    private String averageStr;
    private int bonusPool;
    private String bonusPoolStr;
    private long createTime;
    private int id;
    private String nickName;
    private int ownerId;
    private String remark;
    private int teamCount;
    private String teamName;
    /**
     * attentionCount : 0
     * avatar : http://topcoin.oss-cn-hangzhou.aliyuncs.com/headimg/5356d7b61c772212f0ae2abd15689b7b.jpg
     * balance : 100000
     * brief : 战队简介
     * currProfit : 0.00
     * gtid : 0
     * isShow : 0
     * name : 琅琊战队
     * passFlag : false
     * reason :
     * sync : 0
     * totalProfit : 0.00
     * type : 0
     * updateTime : null
     */

    private int attentionCount;
    private String avatar;
    private int balance;
    private String brief;
    private String currProfit;
    private int gtid;
    private int isShow;
    private String name;
    private boolean passFlag;
    private String reason;
    private int sync;
    private String totalProfit;
    private int type;
    private long updateTime;
    private boolean isSelf;

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getAverageStr() {
        return averageStr;
    }

    public void setAverageStr(String averageStr) {
        this.averageStr = averageStr;
    }

    public int getBonusPool() {
        return bonusPool;
    }

    public void setBonusPool(int bonusPool) {
        this.bonusPool = bonusPool;
    }

    public String getBonusPoolStr() {
        return bonusPoolStr;
    }

    public void setBonusPoolStr(String bonusPoolStr) {
        this.bonusPoolStr = bonusPoolStr;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(int attentionCount) {
        this.attentionCount = attentionCount;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCurrProfit() {
        return currProfit;
    }

    public void setCurrProfit(String currProfit) {
        this.currProfit = currProfit;
    }

    public int getGtid() {
        return gtid;
    }

    public void setGtid(int gtid) {
        this.gtid = gtid;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPassFlag() {
        return passFlag;
    }

    public void setPassFlag(boolean passFlag) {
        this.passFlag = passFlag;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public String getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(String totalProfit) {
        this.totalProfit = totalProfit;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }
}
