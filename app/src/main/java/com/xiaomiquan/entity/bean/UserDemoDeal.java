package com.xiaomiquan.entity.bean;

/**
 * Created by 郭青枫 on 2018/3/10 0010.
 */

public class UserDemoDeal {
    String price;
    long createTime;
    String type;
    String symbol;
    double positionRetaBefore;
    double positionRetaAfter;


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPositionRetaBefore() {
        return positionRetaBefore;
    }

    public void setPositionRetaBefore(double positionRetaBefore) {
        this.positionRetaBefore = positionRetaBefore;
    }

    public double getPositionRetaAfter() {
        return positionRetaAfter;
    }

    public void setPositionRetaAfter(double positionRetaAfter) {
        this.positionRetaAfter = positionRetaAfter;
    }
}
