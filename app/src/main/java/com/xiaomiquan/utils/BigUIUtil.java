package com.xiaomiquan.utils;

import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.CacheUtils;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.base.AppConst;
import com.xiaomiquan.entity.bean.ExchangeData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 郭青枫 on 2018/1/18 0018.
 */

public class BigUIUtil {
    boolean isHavaData = false;//是否有汇率表
    ConcurrentHashMap<String, BigDecimal> usdRate;
    ConcurrentHashMap<String, BigDecimal> btcRate;


    private static class helper {
        private static BigUIUtil exchangeRateUtil = new BigUIUtil();
    }

    public static BigUIUtil getinstance() {
        return helper.exchangeRateUtil;
    }

    private BigUIUtil() {
        //初始化 汇率计算
        //        String string = CacheUtils.getInstance().getString(AppConst.CACHE_EXCHANGE_RATE);
        //        if (!TextUtils.isEmpty(string)) {
        //            init(string);
        //        }
    }

    public boolean IsHavaData() {
        return isHavaData;
    }

    //初始化汇率
    public void init(String string) {
        if (TextUtils.isEmpty(string)) {
            isHavaData = false;
            return;
        } else {
            isHavaData = true;
        }
        //解析汇率
        Map<String, BigDecimal> data = GsonUtil.getInstance().toMap(string, new TypeReference<Map<String, BigDecimal>>() {
        });
        if (usdRate == null) {
            usdRate = new ConcurrentHashMap<>();
        } else {
            usdRate.clear();
        }
        if (btcRate == null) {
            btcRate = new ConcurrentHashMap<>();
        } else {
            usdRate.clear();
        }
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
        CacheUtils.getInstance().put(AppConst.CACHE_EXCHANGE_RATE, data);
        init(data);
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
        BigDecimal subtract = endPriceBig.subtract(endPriceBig.divide(changeBig.add(new BigDecimal("1")), 8, BigDecimal.ROUND_HALF_DOWN));
        return bigPrice(subtract.toString());
    }

    //获取 对比单位的 简写符号
    public String getUnitSymbol(String unit) {
        if (TextUtils.isEmpty(unit)) {
            return "";
        }
        if ("default".equals(unit)) {
            return "";
        }
        if ("CNY".equals(unit)) {
            return "¥";
        }
        if ("USD".equals(unit)) {
            return "$";
        }
        return "";
    }

    //  默认
    //    平台价格-cny=cny
    //    平台价格-usd=usd
    //    cny-平台价格=cny
    //    usd-平台价格=usd
    //    usd-cny=usd
    //    cny-usd=cny
    //    仅usd=usd
    //    仅cny=cny
    //    仅平台价格=usd
    List<String> rateTwoPrice;
    List<String> setUnits;

    // ETH/BTC=0.5  BTC/USD=10000  ADA/USD=5000

    //多价格展示页面调用
    public List<String> rateTwoPrice(String price, String symbol, String itemUnit) {

        //多价格汇率
        if (rateTwoPrice == null) {
            rateTwoPrice = new ArrayList<>();
        } else {
            rateTwoPrice.clear();
        }

        if (TextUtils.isEmpty(price) || TextUtils.isEmpty(itemUnit)) {
            rateTwoPrice.add(bigPrice(price));
            rateTwoPrice.add("");
            return rateTwoPrice;
        }


        String unit = UserSet.getinstance().getUnit();
        if (setUnits == null) {
            setUnits = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_unit));
        }
        if (-1 == setUnits.indexOf(unit)) {
            rateTwoPrice.add(bigPrice(price));
            rateTwoPrice.add("");
            return rateTwoPrice;
        } else if (0 == setUnits.indexOf(unit)) {
            //  默认
            rateTwoPrice.add(bigPrice(price));
            rateTwoPrice.add("");
            return rateTwoPrice;
        } else if (1 == setUnits.indexOf(unit)) {
            //    平台价格-cny=cny
            rateTwoPrice.add(bigPrice(price));
            if (usdRate.containsKey(itemUnit)) {
                //可直接换算
                rateTwoPrice.add("¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString()));
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("¥" + bigPrice(rate(itemUnit, "CNY")));
            } else {
                rateTwoPrice.add("");
            }
            return rateTwoPrice;

        } else if (2 == setUnits.indexOf(unit)) {
            //    平台价格-usd=usd
            rateTwoPrice.add(bigPrice(price));
            if (usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString()));
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("$" + bigPrice(rate(itemUnit, "USD")));
            } else {
                rateTwoPrice.add("");
            }
            return rateTwoPrice;
        } else if (3 == setUnits.indexOf(unit)) {
            //    cny-平台价格=cny
            if (usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString()));
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("¥" + bigPrice(rate(itemUnit, "CNY")));
            } else {
                rateTwoPrice.add("");
            }
            rateTwoPrice.add(bigPrice(price));
            return rateTwoPrice;
        } else if (4 == setUnits.indexOf(unit)) {
            //    usd-平台价格=usd
            if (usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString()));
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("$" + bigPrice(rate(itemUnit, "USD")));
            } else {
                rateTwoPrice.add("");
            }
            rateTwoPrice.add(bigPrice(price));
            return rateTwoPrice;
        } else if (5 == setUnits.indexOf(unit)) {
            //    usd-cny=usd
            if (usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString()));
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("$" + bigPrice(rate(itemUnit, "USD")));
            } else {
                rateTwoPrice.add("");
            }
            if (usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString()));
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("¥" + bigPrice(rate(itemUnit, "CNY")));
            } else {
                rateTwoPrice.add("");
            }
            return rateTwoPrice;
        } else if (6 == setUnits.indexOf(unit)) {
            //    cny-usd=cny
            if (usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString()));
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("¥" + bigPrice(rate(itemUnit, "CNY")));
            } else {
                rateTwoPrice.add("");
            }
            if (usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString()));
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("$" + bigPrice(rate(itemUnit, "USD")));
            } else {
                rateTwoPrice.add("");
            }
            return rateTwoPrice;
        } else if (7 == setUnits.indexOf(unit)) {
            //    仅usd=usd
            if (usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString()));
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("$" + bigPrice(rate(itemUnit, "USD")));
            } else {
                rateTwoPrice.add("");
            }
            rateTwoPrice.add("");
            return rateTwoPrice;
        } else if (8 == setUnits.indexOf(unit)) {
            //    仅cny=cny
            if (usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString()));
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("¥" + bigPrice(rate(itemUnit, "CNY")));
            } else {
                rateTwoPrice.add("");
            }
            rateTwoPrice.add("");
            return rateTwoPrice;
        } else if (9 == setUnits.indexOf(unit)) {
            //    仅平台价格=usd
            rateTwoPrice.add(bigPrice(price));
            rateTwoPrice.add("");
            return rateTwoPrice;
        }
        return rateTwoPrice;
    }

    //仅市值页面调用
    public String rateOnePrice(String price, String symbol, String itemUnit) {
        if (TextUtils.isEmpty(price) || TextUtils.isEmpty(itemUnit)) {
            return price;
        }

        //市值页面只显示 单价格
        String unit = UserSet.getinstance().getUnit();
        if (setUnits == null) {
            setUnits = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_unit));
        }
        if (-1 == setUnits.indexOf(unit)) {
            return bigPrice(price);
        } else if (0 == setUnits.indexOf(unit)) {
            //  默认
            return bigPrice(price);
        } else if (1 == setUnits.indexOf(unit)) {
            //    平台价格-cny=cny
            if (usdRate.containsKey(itemUnit)) {
                return "¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "¥" + bigPrice(rate(itemUnit, "CNY"));
            }
        } else if (2 == setUnits.indexOf(unit)) {
            //    平台价格-usd=usd
            if (usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(rate(itemUnit, "USD"));
            }
        } else if (3 == setUnits.indexOf(unit)) {
            //    cny-平台价格=cny
            if (usdRate.containsKey(itemUnit)) {
                return "¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "¥" + bigPrice(rate(itemUnit, "CNY"));
            }
        } else if (4 == setUnits.indexOf(unit)) {
            //    usd-平台价格=usd
            if (usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(rate(itemUnit, "USD"));
            }
        } else if (5 == setUnits.indexOf(unit)) {
            //    usd-cny=usd
            if (usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(rate(itemUnit, "USD"));
            }
        } else if (6 == setUnits.indexOf(unit)) {
            //    cny-usd=cny
            if (usdRate.containsKey(itemUnit)) {
                return "¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "¥" + bigPrice(rate(itemUnit, "CNY"));
            }
        } else if (7 == setUnits.indexOf(unit)) {
            //    仅usd=usd
            if (usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(rate(itemUnit, "USD"));
            }
        } else if (8 == setUnits.indexOf(unit)) {
            //    仅cny=cny
            if (usdRate.containsKey(itemUnit)) {
                return "¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "¥" + bigPrice(rate(itemUnit, "CNY"));
            }
        } else if (9 == setUnits.indexOf(unit)) {
            //    仅平台价格=usd
            if (usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(rate(itemUnit, "USD"));
            }
        }
        return price;
    }

    public String rate(String name, String unit) {
        if (!isHavaData) {
            return "";
        }
        BigDecimal bigDecimal = rateByUSD(name, unit);

        return bigDecimal.toPlainString();
    }

    // BTC/USD 10000
    // ETH/USD 1000
    //
    // ETH/BTC 10
    // CNY/USD 0.15      USD/USD 1

    // 1 */ 0.15 * 10000


    /**
     * 汇率转化
     *
     * @param name 单位
     * @param unit 需要转化的单位
     * @return
     */
    //    public String rate(String price, String name, String unit) {
    //        if (!isHavaData) {
    //            return "";
    //        }
    //        if (TextUtils.isEmpty(price) || TextUtils.isEmpty(name) || TextUtils.isEmpty(unit)) {
    //            return "";
    //        }
    //        BigDecimal bigPrice = new BigDecimal(price);
    //        BigDecimal bigDecimal = rateByUSD(name, unit);
    //        if (bigDecimal.doubleValue() == 0) {
    //            return "";
    //        }
    //        BigDecimal end = bigPrice.multiply(bigDecimal);
    //        return end.toPlainString();
    //    }
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
            BigDecimal end = nameBig.divide(unitBig, 8, BigDecimal.ROUND_HALF_DOWN);
            return end;
        } else {
            return new BigDecimal("0");
        }
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
    public void changeBg(View view, ExchangeData exchangeData) {

    }


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
            stringBuffer.append(bigDecimal.setScale(0, BigDecimal.ROUND_DOWN).toPlainString());
        } else if (new BigDecimal("1000").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(1, BigDecimal.ROUND_DOWN).toPlainString());
        } else if (new BigDecimal("100").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
        } else if (new BigDecimal("10").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(3, BigDecimal.ROUND_DOWN).toPlainString());
        } else if (new BigDecimal("1").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(4, BigDecimal.ROUND_DOWN).toPlainString());
        } else {
            stringBuffer.append(bigDecimal.setScale(4, BigDecimal.ROUND_DOWN).toPlainString());
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
            return bigDecimal.multiply(new BigDecimal("0.00000001")).setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "亿";
        } else if (new BigDecimal("10000").compareTo(bigDecimal) == -1) {
            //大于一万
            return bigDecimal.multiply(new BigDecimal("0.0001")).setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "万";
        } else if (new BigDecimal("1000").compareTo(bigDecimal) == -1) {
            return bigDecimal.setScale(1, BigDecimal.ROUND_DOWN).toPlainString();
        } else if (new BigDecimal("100").compareTo(bigDecimal) == -1) {
            return bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
        } else if (new BigDecimal("10").compareTo(bigDecimal) == -1) {
            return bigDecimal.setScale(3, BigDecimal.ROUND_DOWN).toPlainString();
        } else if (new BigDecimal("1").compareTo(bigDecimal) == -1) {
            return bigDecimal.setScale(4, BigDecimal.ROUND_DOWN).toPlainString();
        } else {
            return "0";
        }
    }

    //百分比显示
    public String changeAmount(String change) {
        if (TextUtils.isEmpty(change)) {
            return "";
        }
        BigDecimal bigDecimal = new BigDecimal(change);
        return bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
    }
}
