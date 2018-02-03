package com.xiaomiquan.entity.bean.group;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/2/3 0003.
 */

public class EarningsMovements {
    /**
     * startTime : 1516366351000
     * endTime : 1517207392000
     * rates : [0.01,0.01,1,0.05055]
     */

    private long startTime;
    private long endTime;
    private List<Float> rates;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public List<Float> getRates() {
        return rates;
    }

    public void setRates(List<Float> rates) {
        this.rates = rates;
    }
}
