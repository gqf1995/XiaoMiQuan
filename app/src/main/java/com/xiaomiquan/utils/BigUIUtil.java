package com.xiaomiquan.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.CacheUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.base.AppConst;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 郭青枫 on 2018/1/18 0018.
 */

public class BigUIUtil {
    boolean isHavaData = false;//是否有汇率表
    Map<String, BigDecimal> usdRate;
    Map<String, BigDecimal> btcRate;


    private static class helper {
        private static BigUIUtil exchangeRateUtil = new BigUIUtil();
    }

    public static BigUIUtil getinstance() {
        return helper.exchangeRateUtil;
    }

    private BigUIUtil() {
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

    //根据涨幅计算 所涨价格
    public String risePrice(String endPrice, String change) {
        if (TextUtils.isEmpty(endPrice)) {
            return "";
        }
        if (TextUtils.isEmpty(change)) {
            return "";
        }
        BigDecimal endPriceBig = new BigDecimal(endPrice);
        BigDecimal changeBig = new BigDecimal(change);
        BigDecimal subtract = endPriceBig.subtract(endPriceBig.divide(changeBig.add(new BigDecimal("1"))));
        return bigPrice(subtract.toString());
    }

    /**
     * 汇率转化
     *
     * @param price 价格
     * @param name  单位
     * @param unit  需要转化的单位
     * @return
     */
    public String rate(String price, String name, String unit) {
        if (!isHavaData) {
            return "";
        }
        if (TextUtils.isEmpty(price) || TextUtils.isEmpty(name) || TextUtils.isEmpty(unit)) {
            return "";
        }
        BigDecimal bigPrice = new BigDecimal(price);
        BigDecimal bigDecimal = rateByUSD(name, unit);
        if (bigDecimal.doubleValue() == 0) {
            return "";
        }
        BigDecimal end = bigPrice.multiply(bigDecimal);

        return bigPrice(end.toString());
    }

    public BigDecimal rateByUSD(String name, String unit) {
        BigDecimal nameBig, unitBig;
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(unit)) {
            return new BigDecimal("0");
        }
        if (usdRate.containsKey(name)) {
            nameBig = usdRate.get(name);
        } else {
            return new BigDecimal("0");
        }
        if (usdRate.containsKey(unit)) {
            unitBig = usdRate.get(unit);
        } else {
            return new BigDecimal("0");
        }
        if (nameBig == null || unitBig == null) {
            return new BigDecimal("0");
        }
        BigDecimal end = nameBig.multiply(unitBig);
        return end;
    }

    public BigDecimal rateByBTC(String name, String unit) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(unit)) {
            return new BigDecimal("0");
        }
        BigDecimal nameBig = btcRate.get(name);
        BigDecimal unitBig = btcRate.get(unit);
        if (nameBig == null || unitBig == null) {
            return new BigDecimal("0");
        }
        BigDecimal end = nameBig.multiply(unitBig);
        return end;
    }


    //背景渐变 并回归原色

    //文字颜色渐变 并回归原色


    //    数据显示规则：
    //
    //
    //            >>价格
    //
    //    大于1万：取整数；
    //
    //    整数部分4位数时：小数点保留1位有效；
    //    例如1234.001显示为1234；
    //
    //    整数部分3位数时：小数点保留2位有效；
    //    例如123.1234显示为123.12；
    //
    //    整数部分2位数时：小数点保留3位有效；
    //    例如12.1235显示为123.124；
    //
    //    整数部分为1为数时，小数点保留4位；
    //    例如1.123567显示为123.1235；
    //
    //
    //
    //
    //            >>量：
    //
    //    小于1的话，精确到0.001；四舍五入，量小舍后可为0；
    //
    //            1到100的话，精确到0.01；
    //
    //            100到10000的话，精确到1；
    //
    //            1万到1亿的话，显示x.xx万，精确到0.01；
    //
    //    大于一亿的话，显示x.xx亿，精确到0.01；

    //价格单位 显示规则
    public String bigPrice(String price) {
        if (TextUtils.isEmpty(price)) {
            return "";
        }
        BigDecimal bigDecimal = new BigDecimal(price);
        StringBuffer stringBuffer = new StringBuffer();
        if (new BigDecimal("10000").compareTo(bigDecimal) == -1) {
            //大于一万
            stringBuffer.append(bigDecimal.setScale(0, BigDecimal.ROUND_DOWN).toString());
        } else if (new BigDecimal("1000").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(1, BigDecimal.ROUND_DOWN).toString());
        } else if (new BigDecimal("100").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).toString());
        } else if (new BigDecimal("10").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(3, BigDecimal.ROUND_DOWN).toString());
        } else if (new BigDecimal("1").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(4, BigDecimal.ROUND_DOWN).toString());
        } else {
            stringBuffer.append(bigDecimal.setScale(4, BigDecimal.ROUND_DOWN).toString());
        }
        return stringBuffer.toString();
    }

    //量 单位 显示规则
    public String bigAmount(String amount) {
        if (TextUtils.isEmpty(amount)) {
            return "";
        }
        BigDecimal bigDecimal = new BigDecimal(amount);
        if (new BigDecimal("100000000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("0.00000001")).setScale(2, BigDecimal.ROUND_DOWN).toString() + "亿";
        } else if (new BigDecimal("10000").compareTo(bigDecimal) == -1) {
            //大于一万
            return bigDecimal.multiply(new BigDecimal("0.0001")).setScale(2, BigDecimal.ROUND_DOWN).toString() + "万";
        } else if (new BigDecimal("1000").compareTo(bigDecimal) == -1) {
            return bigDecimal.setScale(1, BigDecimal.ROUND_DOWN).toString();
        } else if (new BigDecimal("100").compareTo(bigDecimal) == -1) {
            return bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).toString();
        } else if (new BigDecimal("10").compareTo(bigDecimal) == -1) {
            return bigDecimal.setScale(3, BigDecimal.ROUND_DOWN).toString();
        } else if (new BigDecimal("1").compareTo(bigDecimal) == -1) {
            return bigDecimal.setScale(4, BigDecimal.ROUND_DOWN).toString();
        } else {
            return "0";
        }
    }

}
