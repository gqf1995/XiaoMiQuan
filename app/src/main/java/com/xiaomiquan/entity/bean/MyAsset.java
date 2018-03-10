package com.xiaomiquan.entity.bean;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/3/10 0010.
 */

public class MyAsset {
    /**
     * totalAmount : 111110.0
     * balance : 100000
     * coins : [{"amount":0,"coinId":2,"count":0.1,"priceUsd":731.907,"ratio":0,"symbol":"ETH"},{"amount":0,"coinId":20,"count":1,"priceUsd":19.6946,"ratio":0,"symbol":"QTUM"}]
     */

    private String totalAmount;
    private String balance;
    private List<CoinsBean> coins;


    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<CoinsBean> getCoins() {
        return coins;
    }

    public void setCoins(List<CoinsBean> coins) {
        this.coins = coins;
    }

    public static class CoinsBean {
        /**
         * amount : 0
         * coinId : 2
         * count : 0.1
         * priceUsd : 731.907
         * ratio : 0
         * symbol : ETH
         */

        private String amount;
        private int coinId;
        private double count;
        private double priceUsd;
        private float ratio;
        private String symbol;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getCoinId() {
            return coinId;
        }

        public void setCoinId(int coinId) {
            this.coinId = coinId;
        }

        public double getCount() {
            return count;
        }

        public void setCount(double count) {
            this.count = count;
        }

        public double getPriceUsd() {
            return priceUsd;
        }

        public void setPriceUsd(double priceUsd) {
            this.priceUsd = priceUsd;
        }

        public float getRatio() {
            return ratio;
        }

        public void setRatio(float ratio) {
            this.ratio = ratio;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
    }
}
