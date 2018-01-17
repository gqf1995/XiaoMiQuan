package com.xiaomiquan.entity.bean.kline;

/**
 * Created by loro on 2017/2/8.
 */
public class KLineBean {
    public String date;
    public long timestamp;
    public float open;
    public float close;
    public float high;
    public float low;
    public float volume;

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
