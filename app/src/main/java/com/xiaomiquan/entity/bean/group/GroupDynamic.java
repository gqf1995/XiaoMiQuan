package com.xiaomiquan.entity.bean.group;

/**
 * Created by 郭青枫 on 2018/2/8 0008.
 */

public class GroupDynamic {
    /**
     * brief : 这是关于币圈交易的组合
     * coinId : 1
     * count : 1.0000
     * createTime : 1517233270000
     * dealTime : 1518008982000
     * demoId : 2
     * isAttention : 0
     * isSelf : 0
     * name : 吃胡胡
     * nickName : hyk
     * positionRateAfter : 1.28
     * positionRateBefore : 0.00
     * symbol : BTC
     * totalRate : 7628.57
     * type : 2
     * userId : 0
     */

    private String brief;
    private String coinId;
    private String count;
    private long createTime;
    private long dealTime;
    private String demoId;
    private int isAttention;
    private int isSelf;
    private String name;
    private String nickName;
    private String positionRateAfter;
    private String positionRateBefore;
    private String symbol;
    private String totalRate;
    private int type;
    private String userId;
    private String avatar;
    private String price;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getDealTime() {
        return dealTime;
    }

    public void setDealTime(long dealTime) {
        this.dealTime = dealTime;
    }

    public String getDemoId() {
        return demoId;
    }

    public void setDemoId(String demoId) {
        this.demoId = demoId;
    }

    public int getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(int isAttention) {
        this.isAttention = isAttention;
    }

    public int getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(int isSelf) {
        this.isSelf = isSelf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPositionRateAfter() {
        return positionRateAfter;
    }

    public void setPositionRateAfter(String positionRateAfter) {
        this.positionRateAfter = positionRateAfter;
    }

    public String getPositionRateBefore() {
        return positionRateBefore;
    }

    public void setPositionRateBefore(String positionRateBefore) {
        this.positionRateBefore = positionRateBefore;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(String totalRate) {
        this.totalRate = totalRate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
