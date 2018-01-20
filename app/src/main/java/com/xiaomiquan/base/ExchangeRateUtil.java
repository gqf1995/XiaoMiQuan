package com.xiaomiquan.base;

import android.text.TextUtils;

import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.CacheUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 郭青枫 on 2018/1/18 0018.
 */

public class ExchangeRateUtil {

    boolean isHavaData = false;
    Map<String, BigDecimal> usdRate;
    Map<String, BigDecimal> btcRate;


    private static class helper {
        private static ExchangeRateUtil exchangeRateUtil = new ExchangeRateUtil();
    }

    public static ExchangeRateUtil getinstance() {
        return helper.exchangeRateUtil;
    }

    private ExchangeRateUtil() {
        //初始化 汇率计算
        init();
    }

    public boolean IsHavaData() {
        return isHavaData;
    }

    //初始化汇率
    public void init() {
        String string = CacheUtils.getInstance().getString(AppConst.CACHE_EXCHANGE_RATE);
        if (TextUtils.isEmpty(string)) {
            isHavaData = false;
            return;
        } else {
            isHavaData = true;
        }
        //解析汇率
        Map<String, BigDecimal> data = GsonUtil.getInstance().toMap(string, new TypeReference<Map<String, BigDecimal>>() {
        });
        usdRate = new LinkedHashMap<>();
        btcRate = new LinkedHashMap<>();
        for (Map.Entry<String, BigDecimal> entry : data.entrySet()) {
            String k = entry.getKey();
            BigDecimal v = entry.getValue();
            String[] split = k.split(",");
            if (split.length == 2) {
                if ("USD".equals(split[1])) {
                    usdRate.put(split[0], v);
                } else if ("BTC".equals(split[1])) {
                    btcRate.put(split[0], v);
                }
            }
        }
    }

    //更新汇率
    public void upData(String data) {
        isHavaData = true;
        CacheUtils.getInstance().put(AppConst.CACHE_EXCHANGE_RATE, data, 60 * 60 * 12);
        init();
    }


    public String rateByUSD(String name, String unit) {
        BigDecimal nameBig = usdRate.get(name);
        BigDecimal unitBig = usdRate.get(unit);
        if (nameBig == null || unitBig == null) {
            return "";
        }
        BigDecimal end = nameBig.multiply(unitBig);
        return end.toString();
    }

    public String rateByBTC(String name, String unit) {
        BigDecimal nameBig = btcRate.get(name);
        BigDecimal unitBig = btcRate.get(unit);
        if (nameBig == null || unitBig == null) {
            return "";
        }
        BigDecimal end = nameBig.multiply(unitBig);
        return end.toString();
    }


}
