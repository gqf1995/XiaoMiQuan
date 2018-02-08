package com.xiaomiquan.entity.bean.group;

/**
 * Created by 郭青枫 on 2018/2/8 0008.
 */

public class TradingResult {
    /**
     * balance : 40058.5980000000
     * coinId : 1
     * count : 7.2000
     * dealId : 6
     * demoId : 1
     * priceUsd : 8049.6200000000
     * symbol : BTC
     */

    private String balance;
    private int coinId;
    private String count;
    private int dealId;
    private int demoId;
    private String priceUsd;
    private String symbol;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getCoinId() {
        return coinId;
    }

    public void setCoinId(int coinId) {
        this.coinId = coinId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getDealId() {
        return dealId;
    }

    public void setDealId(int dealId) {
        this.dealId = dealId;
    }

    public int getDemoId() {
        return demoId;
    }

    public void setDemoId(int demoId) {
        this.demoId = demoId;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(String priceUsd) {
        this.priceUsd = priceUsd;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
