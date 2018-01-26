package com.xiaomiquan.entity;

import android.text.TextUtils;

import com.xiaomiquan.entity.bean.ExchangeData;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Created by 郭青枫 on 2018/1/21 0021.
 */

public class RiseChangeSort implements Comparator<ExchangeData> {
    @Override
    public int compare(ExchangeData exchangeData, ExchangeData t1) {
        if (TextUtils.isEmpty(exchangeData.getChange()) || TextUtils.isEmpty(exchangeData.getChange())) {
            return 0;
        }
        int thislv = new BigDecimal(t1.getChange()).compareTo(new BigDecimal(exchangeData.getChange()));
        return thislv;
    }
}
