package com.xiaomiquan.entity.bean.kline;

import java.math.BigDecimal;

/**
 * Created by loro on 2017/2/8.
 */
public class KLineBean {
    public String date;
    public long timestamp;

    public BigDecimal open;
    public BigDecimal close;
    public BigDecimal high;
    public BigDecimal low;
    public BigDecimal volume;




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
}
