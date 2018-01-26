package com.xiaomiquan.entity.bean.kline;

import com.xiaomiquan.greenDaoUtils.BigDecimalConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.math.BigDecimal;

/**
 * Created by loro on 2017/2/8.
 */
@Entity
public class KLineBean {

    @Id(autoincrement = true)
    long id;

    public String date;
    public long timestamp;
    @Convert(converter = BigDecimalConverter.class, columnType = String.class)
    public BigDecimal open;
    @Convert(converter = BigDecimalConverter.class, columnType = String.class)
    public BigDecimal close;
    @Convert(converter = BigDecimalConverter.class, columnType = String.class)
    public BigDecimal high;
    @Convert(converter = BigDecimalConverter.class, columnType = String.class)
    public BigDecimal low;
    @Convert(converter = BigDecimalConverter.class, columnType = String.class)
    public BigDecimal volume;

    public String key;


    @Generated(hash = 1039934603)
    public KLineBean(long id, String date, long timestamp, BigDecimal open,
            BigDecimal close, BigDecimal high, BigDecimal low, BigDecimal volume,
            String key) {
        this.id = id;
        this.date = date;
        this.timestamp = timestamp;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.key = key;
    }


    @Generated(hash = 521295344)
    public KLineBean() {
    }


    @Override
    public String toString() {
        return "KLineBean{" +
                "date='" + date + '\'' +
                ", timestamp=" + timestamp +
                ", open=" + open +
                ", close=" + close +
                ", high=" + high +
                ", low=" + low +
                ", volume=" + volume +
                '}';
    }


    public long getId() {
        return this.id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getDate() {
        return this.date;
    }


    public void setDate(String date) {
        this.date = date;
    }


    public long getTimestamp() {
        return this.timestamp;
    }


    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public BigDecimal getOpen() {
        return this.open;
    }


    public void setOpen(BigDecimal open) {
        this.open = open;
    }


    public BigDecimal getClose() {
        return this.close;
    }


    public void setClose(BigDecimal close) {
        this.close = close;
    }


    public BigDecimal getHigh() {
        return this.high;
    }


    public void setHigh(BigDecimal high) {
        this.high = high;
    }


    public BigDecimal getLow() {
        return this.low;
    }


    public void setLow(BigDecimal low) {
        this.low = low;
    }


    public BigDecimal getVolume() {
        return this.volume;
    }


    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }


    public String getKey() {
        return this.key;
    }


    public void setKey(String key) {
        this.key = key;
    }


    public void setId(Long id) {
        this.id = id;
    }
}
