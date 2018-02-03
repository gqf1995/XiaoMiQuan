package com.xiaomiquan.entity.bean.group;

/**
 * Created by 郭青枫 on 2018/2/3 0003.
 */

public class HistoryTrading {
    /**
     * coinId : 0
     * count : 4.4511
     * createTime : 1517302461000
     * dealTime : 1517302461000
     * demoId : 0
     * id : 3
     * positionRateAfter : 0
     * positionRateBefore : 0
     * price : 11233.1
     * priceType : 0
     * status : 2
     * statusText : 已成交
     * symbol : BTC
     * type : 1
     * userId : 0
     */

    private String coinId;
    private String count;
    private long createTime;
    private long dealTime;
    private String demoId;
    private String id;
    private String positionRateAfter;
    private String positionRateBefore;
    private String price;
    private String priceType;
    private String status;
    private String statusText;
    private String symbol;
    private String type;
    private String userId;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
