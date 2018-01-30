package com.xiaomiquan.entity.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 郭青枫 on 2018/1/16 0016.
 */

public class ExchangeData implements Parcelable {



    private String id;
    private String exchange; //交易所
    private String name;//symbol的全称
    private String symbol;//e.g. btc,eth...
    private String unit;//兑换货币单位 BTC/USD 中的 USD
    private String tradePair;//e.g. BTC/USD symbol/unit
    private String side;//1买2卖
    private String last;//最新价
    private String high;//最高价
    private String low;//最低价
    private String open;//24小时开盘价
    private String close;//24小时收盘价
    private String volume; //24成交量
    private String amount;//24小时成交额
    private String ask;//卖一
    private String bid;//买一
    private String change;//24小时涨跌幅
    private String timestamp;


    private String onlyKey;//市场的唯一标识 例如：Okex_ETH_BTC "choicePrice": null// 用户选择的市场价格


    /**
     * 24h_volume_usd : 8932820000
     * availableSupply : 16829075
     * lastUpdated : 0
     * marketCapUsd : 188882806170
     * maxSupply : 21000000
     * percentChange1h : -0.28
     * percentChange24h : 3.38
     * percentChange7d : -10.65
     * priceBtc : 1
     * priceUsd : 11223.6
     * rank : 1
     * totalSupply : 16829075
     */

    private String availableSupply;
    private String lastUpdated;
    private String marketCapUsd;
    private String maxSupply;
    private String percentChange1h;
    private String percentChange24h;
    private String percentChange7d;
    private String priceBtc;
    private String priceUsd;
    private String rank;
    private String totalSupply;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTradePair() {
        return tradePair;
    }

    public void setTradePair(String tradePair) {
        this.tradePair = tradePair;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOnlyKey() {
        return onlyKey;
    }

    public void setOnlyKey(String onlyKey) {
        this.onlyKey = onlyKey;
    }

    public String getAvailableSupply() {
        return availableSupply;
    }

    public void setAvailableSupply(String availableSupply) {
        this.availableSupply = availableSupply;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getMarketCapUsd() {
        return marketCapUsd;
    }

    public void setMarketCapUsd(String marketCapUsd) {
        this.marketCapUsd = marketCapUsd;
    }

    public String getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(String maxSupply) {
        this.maxSupply = maxSupply;
    }

    public String getPercentChange1h() {
        return percentChange1h;
    }

    public void setPercentChange1h(String percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public String getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(String percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public String getPercentChange7d() {
        return percentChange7d;
    }

    public void setPercentChange7d(String percentChange7d) {
        this.percentChange7d = percentChange7d;
    }

    public String getPriceBtc() {
        return priceBtc;
    }

    public void setPriceBtc(String priceBtc) {
        this.priceBtc = priceBtc;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(String priceUsd) {
        this.priceUsd = priceUsd;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(String totalSupply) {
        this.totalSupply = totalSupply;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.exchange);
        dest.writeString(this.name);
        dest.writeString(this.symbol);
        dest.writeString(this.unit);
        dest.writeString(this.tradePair);
        dest.writeString(this.side);
        dest.writeString(this.last);
        dest.writeString(this.high);
        dest.writeString(this.low);
        dest.writeString(this.open);
        dest.writeString(this.close);
        dest.writeString(this.volume);
        dest.writeString(this.amount);
        dest.writeString(this.ask);
        dest.writeString(this.bid);
        dest.writeString(this.change);
        dest.writeString(this.timestamp);
        dest.writeString(this.onlyKey);
        dest.writeString(this.availableSupply);
        dest.writeString(this.lastUpdated);
        dest.writeString(this.marketCapUsd);
        dest.writeString(this.maxSupply);
        dest.writeString(this.percentChange1h);
        dest.writeString(this.percentChange24h);
        dest.writeString(this.percentChange7d);
        dest.writeString(this.priceBtc);
        dest.writeString(this.priceUsd);
        dest.writeString(this.rank);
        dest.writeString(this.totalSupply);
    }

    public ExchangeData() {
    }

    protected ExchangeData(Parcel in) {
        this.id = in.readString();
        this.exchange = in.readString();
        this.name = in.readString();
        this.symbol = in.readString();
        this.unit = in.readString();
        this.tradePair = in.readString();
        this.side = in.readString();
        this.last = in.readString();
        this.high = in.readString();
        this.low = in.readString();
        this.open = in.readString();
        this.close = in.readString();
        this.volume = in.readString();
        this.amount = in.readString();
        this.ask = in.readString();
        this.bid = in.readString();
        this.change = in.readString();
        this.timestamp = in.readString();
        this.onlyKey = in.readString();
        this.availableSupply = in.readString();
        this.lastUpdated = in.readString();
        this.marketCapUsd = in.readString();
        this.maxSupply = in.readString();
        this.percentChange1h = in.readString();
        this.percentChange24h = in.readString();
        this.percentChange7d = in.readString();
        this.priceBtc = in.readString();
        this.priceUsd = in.readString();
        this.rank = in.readString();
        this.totalSupply = in.readString();
    }

    public static final Parcelable.Creator<ExchangeData> CREATOR = new Parcelable.Creator<ExchangeData>() {
        @Override
        public ExchangeData createFromParcel(Parcel source) {
            return new ExchangeData(source);
        }

        @Override
        public ExchangeData[] newArray(int size) {
            return new ExchangeData[size];
        }
    };

    @Override
    public String toString() {
        return "ExchangeData{" +
                "id='" + id + '\'' +
                ", exchange='" + exchange + '\'' +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", unit='" + unit + '\'' +
                ", tradePair='" + tradePair + '\'' +
                ", side='" + side + '\'' +
                ", last='" + last + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", open='" + open + '\'' +
                ", close='" + close + '\'' +
                ", volume='" + volume + '\'' +
                ", amount='" + amount + '\'' +
                ", ask='" + ask + '\'' +
                ", bid='" + bid + '\'' +
                ", change='" + change + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", onlyKey='" + onlyKey + '\'' +
                ", availableSupply='" + availableSupply + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", marketCapUsd='" + marketCapUsd + '\'' +
                ", maxSupply='" + maxSupply + '\'' +
                ", percentChange1h='" + percentChange1h + '\'' +
                ", percentChange24h='" + percentChange24h + '\'' +
                ", percentChange7d='" + percentChange7d + '\'' +
                ", priceBtc='" + priceBtc + '\'' +
                ", priceUsd='" + priceUsd + '\'' +
                ", rank='" + rank + '\'' +
                ", totalSupply='" + totalSupply + '\'' +
                '}';
    }
}
