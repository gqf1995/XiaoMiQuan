package com.xiaomiquan.entity;

import com.xiaomiquan.entity.bean.ExchangeData;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Created by 郭青枫 on 2018/1/21 0021.
 */

public class RiseChangeSort implements Comparator<ExchangeData> {
    @Override
    public int compare(ExchangeData exchangeData, ExchangeData t1) {
        int thislv = new BigDecimal(exchangeData.getChange()).compareTo(new BigDecimal(t1.getChange()));
        return thislv;
    }
}
