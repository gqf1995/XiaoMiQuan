package com.xiaomiquan.entity.bean;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 郭青枫 on 2018/2/6 0006.
 */
@Entity
public class CoinMarketValue {
    /**
     * 24h_volume_usd : 1.03929E10
     * availableSupply : 1.6848937E7
     * id : bitcoin
     * lastUpdated : 0
     * marketCapUsd : 1.05678723226E11
     * maxSupply : 2.1E7
     * name : Bitcoin
     * onlyKey :
     * percentChange1h : -2.03
     * percentChange24h : -23.08
     * percentChange7d : -43.38
     * priceBtc : 1
     * priceUsd : 6272.13
     * rank : 1
     * symbol : BTC
     * totalSupply : 1.6848937E7
     */

    @Id(autoincrement = true)
    Long cid;

    @SerializedName("24h_volume_usd")
    private double _$24h_volume_usd;
    private double availableSupply;
    private String id;
    private int lastUpdated;
    private double marketCapUsd;
    private double maxSupply;
    private String name;

    @Unique
    private String onlyKey;
    private double percentChange1h;
    private double percentChange24h;
    private double percentChange7d;
    private int priceBtc;
    private double priceUsd;
    private int rank;
    private String symbol;
    private double totalSupply;

    @Generated(hash = 1845901079)
    public CoinMarketValue(Long cid, double _$24h_volume_usd,
            double availableSupply, String id, int lastUpdated, double marketCapUsd,
            double maxSupply, String name, String onlyKey, double percentChange1h,
            double percentChange24h, double percentChange7d, int priceBtc,
            double priceUsd, int rank, String symbol, double totalSupply) {
        this.cid = cid;
        this._$24h_volume_usd = _$24h_volume_usd;
        this.availableSupply = availableSupply;
        this.id = id;
        this.lastUpdated = lastUpdated;
        this.marketCapUsd = marketCapUsd;
        this.maxSupply = maxSupply;
        this.name = name;
        this.onlyKey = onlyKey;
        this.percentChange1h = percentChange1h;
        this.percentChange24h = percentChange24h;
        this.percentChange7d = percentChange7d;
        this.priceBtc = priceBtc;
        this.priceUsd = priceUsd;
        this.rank = rank;
        this.symbol = symbol;
        this.totalSupply = totalSupply;
    }

    @Generated(hash = 1940338968)
    public CoinMarketValue() {
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public double get_$24h_volume_usd() {
        return _$24h_volume_usd;
    }

    public void set_$24h_volume_usd(double _$24h_volume_usd) {
        this._$24h_volume_usd = _$24h_volume_usd;
    }

    public double getAvailableSupply() {
        return availableSupply;
    }

    public void setAvailableSupply(double availableSupply) {
        this.availableSupply = availableSupply;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(int lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public double getMarketCapUsd() {
        return marketCapUsd;
    }

    public void setMarketCapUsd(double marketCapUsd) {
        this.marketCapUsd = marketCapUsd;
    }

    public double getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(double maxSupply) {
        this.maxSupply = maxSupply;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnlyKey() {
        return onlyKey;
    }

    public void setOnlyKey(String onlyKey) {
        this.onlyKey = onlyKey;
    }

    public double getPercentChange1h() {
        return percentChange1h;
    }

    public void setPercentChange1h(double percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public double getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(double percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public double getPercentChange7d() {
        return percentChange7d;
    }

    public void setPercentChange7d(double percentChange7d) {
        this.percentChange7d = percentChange7d;
    }

    public int getPriceBtc() {
        return priceBtc;
    }

    public void setPriceBtc(int priceBtc) {
        this.priceBtc = priceBtc;
    }

    public double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(double totalSupply) {
        this.totalSupply = totalSupply;
    }
}
