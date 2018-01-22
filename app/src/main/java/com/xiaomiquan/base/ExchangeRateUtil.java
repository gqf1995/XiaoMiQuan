package com.xiaomiquan.base;

/**
 * Created by 郭青枫 on 2018/1/18 0018.
 *
 */

public class ExchangeRateUtil {




    private static class helper {
        private static ExchangeRateUtil exchangeRateUtil = new ExchangeRateUtil();
    }

    public static ExchangeRateUtil getinstance() {
        return helper.exchangeRateUtil;
    }

    private ExchangeRateUtil() {

    }



}
