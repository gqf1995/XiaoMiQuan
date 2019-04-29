package com.xiaomiquan.entity;

import android.text.TextUtils;

import com.xiaomiquan.entity.bean.ExchangeData;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Created by 郭青枫 on 2018/1/21 0021.
 */

public class Drop24ChangeSort implements Comparator<ExchangeData> {
    @Override
    public int compare(ExchangeData exchangeData, ExchangeData t1) {
        if (exchangeData == null || t1 == null) {
            return 0;
        }
        if (TextUtils.isEmpty(exchangeData.getPercentChange24h()) || TextUtils.isEmpty(t1.getPercentChange24h())) {
            return 0;
        }
        int thislv = new BigDecimal(exchangeData.getPercentChange24h()).compareTo(new BigDecimal(t1.getPercentChange24h()));
        return thislv;
    }
}
