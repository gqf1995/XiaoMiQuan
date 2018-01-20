package com.xiaomiquan.entity.bean;

/**
 * Created by 郭青枫 on 2018/1/14 0014.
 */

public class MarketValue {
    //币种全称
    String id;
    //币种名称
    String name;
    //币种简称
    String symbol;
    //币种市值排名
    int rank;
    //币种美元市值
    Double priceUsd;
    //币种比特币市值
    Double priceBtc;
    //币种24小时交易量
    Double allDayVolumeUsd;
    //市场总值
    Double marketCapUsd;
    //可购买量
    Double availableSupply;
    //市场总量
    Double totalSupply;
    //币种总量
    Double maxSupply;
    //一小时价格变化
    Double percentChange1h;
    //二十四小时价格变化
    Double percentChange24h;
    //七天的价格变化
    Double percentChange7d;
    //最后一次更新时间
    String lastUpdated;
    //市场的唯一标识 例如：Okex_ETH_BTC
    private String onlyKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(Double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public Double getPriceBtc() {
        return priceBtc;
    }

    public void setPriceBtc(Double priceBtc) {
        this.priceBtc = priceBtc;
    }

    public Double getAllDayVolumeUsd() {
        return allDayVolumeUsd;
    }

    public void setAllDayVolumeUsd(Double allDayVolumeUsd) {
        this.allDayVolumeUsd = allDayVolumeUsd;
    }

    public Double getMarketCapUsd() {
        return marketCapUsd;
    }

    public void setMarketCapUsd(Double marketCapUsd) {
        this.marketCapUsd = marketCapUsd;
    }

    public Double getAvailableSupply() {
        return availableSupply;
    }

    public void setAvailableSupply(Double availableSupply) {
        this.availableSupply = availableSupply;
    }

    public Double getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(Double totalSupply) {
        this.totalSupply = totalSupply;
    }

    public Double getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(Double maxSupply) {
        this.maxSupply = maxSupply;
    }

    public Double getPercentChange1h() {
        return percentChange1h;
    }

    public void setPercentChange1h(Double percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public Double getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(Double percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public Double getPercentChange7d() {
        return percentChange7d;
    }

    public void setPercentChange7d(Double percentChange7d) {
        this.percentChange7d = percentChange7d;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getOnlyKey() {
        return onlyKey;
    }

    public void setOnlyKey(String onlyKey) {
        this.onlyKey = onlyKey;
    }
}
