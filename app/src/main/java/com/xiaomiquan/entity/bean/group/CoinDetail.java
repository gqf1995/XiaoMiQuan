package com.xiaomiquan.entity.bean.group;

/**
 * Created by 郭青枫 on 2018/2/1 0001.
 */

public class CoinDetail {
    /**
     * 持有
     * symbol : BTC
     * count : 8.9022
     * coinId : 1
     */

    private String symbol;
    private String count;
    private String coinId;

    /**
     * id : 1
     * name : Bitcoin
     * priceUsd : 11280.6
     * createTime : 1516960448000
     * updateTime : 1517040680000
     * "type": 1,     						// 1：做多 2：做空
     */

    private String name;
    private String priceUsd;
    private String createTime;
    private String updateTime;
    private String type;
    /**
     * coinId : 1
     * count : 9.474
     * fee : 20
     * picUrl : http://topcoin.oss-cn-hangzhou.aliyuncs.com/symbol/bitcoin.png
     * priceUsd : 8850.59
     */

    private float fee;
    private String picUrl;

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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(String priceUsd) {
        this.priceUsd = priceUsd;
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

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
