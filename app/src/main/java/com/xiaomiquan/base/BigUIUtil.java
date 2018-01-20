package com.xiaomiquan.base;

import java.math.BigDecimal;

/**
 * Created by 郭青枫 on 2018/1/18 0018.
 */

public class BigUIUtil {
    private static class helper {
        private static BigUIUtil exchangeRateUtil = new BigUIUtil();
    }

    public static BigUIUtil getinstance() {
        return helper.exchangeRateUtil;
    }

    private BigUIUtil() {

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
    public String bigPrice(BigDecimal bigDecimal) {
        StringBuffer stringBuffer = new StringBuffer();
        if (new BigDecimal("10000").compareTo(bigDecimal) == -1) {
            //大于一万
            stringBuffer.append(bigDecimal.setScale(0,BigDecimal.ROUND_DOWN).toString());
        } else if (new BigDecimal("1000").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(1,BigDecimal.ROUND_DOWN).toString());
        } else if (new BigDecimal("100").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(2,BigDecimal.ROUND_DOWN).toString());
        } else if (new BigDecimal("10").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(3,BigDecimal.ROUND_DOWN).toString());
        } else if (new BigDecimal("1").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(4,BigDecimal.ROUND_DOWN).toString());
        }else{
            stringBuffer.append(bigDecimal.setScale(4,BigDecimal.ROUND_DOWN).toString());
        }
//        if (new BigDecimal("0").compareTo(new BigDecimal(stringBuffer.toString())) == 1) {
//            return stringBuffer.toString();
//        } else {
//            while (true) {
//                if (stringBuffer.charAt(stringBuffer.length() - 1) == '.') {
//                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
//                    break;
//                }
//                if (stringBuffer.charAt(stringBuffer.length() - 1) == '0') {
//                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
//                }
//            }
//            return stringBuffer.toString();
//        }
        return stringBuffer.toString();
    }

    public String bigAmount(BigDecimal bigDecimal) {
        if (new BigDecimal("100000000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("0.00000001")).setScale(2,BigDecimal.ROUND_DOWN).toString() + "亿";
        } else if (new BigDecimal("10000").compareTo(bigDecimal) == -1) {
            //大于一万
            return bigDecimal.multiply(new BigDecimal("0.0001")).setScale(2,BigDecimal.ROUND_DOWN).toString() + "万";
        } else if (new BigDecimal("1000").compareTo(bigDecimal) == -1) {
            return bigDecimal.setScale(1,BigDecimal.ROUND_DOWN).toString();
        } else if (new BigDecimal("100").compareTo(bigDecimal) == -1) {
            return bigDecimal.setScale(2,BigDecimal.ROUND_DOWN).toString();
        } else if (new BigDecimal("10").compareTo(bigDecimal) == -1) {
            return bigDecimal.setScale(3,BigDecimal.ROUND_DOWN).toString();
        } else if (new BigDecimal("1").compareTo(bigDecimal) == -1) {
            return bigDecimal.setScale(4,BigDecimal.ROUND_DOWN).toString();
        } else {
            return "0";
        }
    }

}
