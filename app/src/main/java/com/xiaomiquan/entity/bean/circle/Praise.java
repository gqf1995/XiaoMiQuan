package com.xiaomiquan.entity.bean.circle;

/**
 * Created by Andy on 2018/1/29.
 */

public class Praise {

    /**
     * linkId : 1
     * userId : 2
     * createTime : 1517228503477
     * praiseQty : 3
     * badEggQty : 0
     * praiseStr : 成龙、叶问、阿拉斯加
     */

    private String linkId;
    private String userId;
    private String createTime;
    private String praiseQty;
    private String badEggQty;
    private String praiseStr;

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPraiseQty() {
        return praiseQty;
    }

    public void setPraiseQty(String praiseQty) {
        this.praiseQty = praiseQty;
    }

    public String getBadEggQty() {
        return badEggQty;
    }

    public void setBadEggQty(String badEggQty) {
        this.badEggQty = badEggQty;
    }

    public String getPraiseStr() {
        return praiseStr;
    }

    public void setPraiseStr(String praiseStr) {
        this.praiseStr = praiseStr;
    }
}
