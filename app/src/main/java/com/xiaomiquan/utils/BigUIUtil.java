package com.xiaomiquan.utils;

import android.animation.ValueAnimator;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.xiaomiquan.base.AppConst.CACHE_CUSTOM_RATE;

/**
 * Created by 郭青枫 on 2018/1/18 0018.
 */
//1.实时价格自动刷新，几个地方都要自动更新（更新频率5s）
//        2.没有k先的页面，要换一种显示方式
//        3.币种排序
//        4.币种资料补充（包括扒mytoken的一些数据）
//        5.bittrex币种和货币单位搞反了，
//        9.市值错误
//
//
//        ios：
//        我的持仓名字没有显示币种名称
//        未成交委托无法撤销
//        Ios端明显卡顿
//        添加自选的时候需要加上（计价单位）
//        二级页面需要增加左侧滑入返回上级菜单
//
//        安卓端：
//
//        模拟组合未走通
//
//
//        （1）bitfinex交易所推送数据有问题，无法推送（可能还有其他交易所）；
//        （2）首页交易所图标
//        （4）有些交易所无数据，排查原因，或考虑该版本是否需要该交易所
//        （5）tab中选择交易所，跳转到相关的交易所有误，比如选择huobi.pro跳转到ZB
public class BigUIUtil {
    boolean isHavaData = false;//是否有汇率表
    ConcurrentHashMap<String, BigDecimal> usdRate;
    ConcurrentHashMap<String, BigDecimal> btcRate;
    ConcurrentHashMap<String, ValueAnimator> animatorConcurrentHashMap;


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

    //百分比
    public void rateTextView(double rate, TextView textView) {
        if (rate > 0) {
            textView.setTextColor(CommonUtils.getColor(UserSet.getinstance().getRiseColor()));
            textView.setText("+" + rate + "%");
        } else {
            textView.setTextColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));
            textView.setText(rate + "%");
        }
    }

    //没动画变色
    public void noAnim(final TextView textView, String oldnum, String newnum, final int endColor, String onlyKey) {
        if (TextUtils.isEmpty(oldnum) || TextUtils.isEmpty(newnum) || TextUtils.isEmpty(onlyKey) || textView == null) {
            return;
        }
        if (oldnum.contains("¥") || oldnum.contains("$")) {
            oldnum = oldnum.substring(1);
        }
        if (newnum.contains("¥") || newnum.contains("$")) {
            newnum = oldnum.substring(1);
        }
        if (new BigDecimal(oldnum).compareTo(new BigDecimal(newnum)) == 1) {
            //降价
            textView.setTextColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));
        } else if (new BigDecimal(oldnum).compareTo(new BigDecimal(newnum)) == -1) {
            //涨价
            textView.setTextColor(CommonUtils.getColor(UserSet.getinstance().getRiseColor()));
        } else {
            return;
        }
    }

    //动画
    public void anim(final TextView textView, String oldnum, String newnum, final int endColor, String onlyKey) {
        if (TextUtils.isEmpty(oldnum) || TextUtils.isEmpty(newnum) || TextUtils.isEmpty(onlyKey) || textView == null) {
            return;
        }
        if (animatorConcurrentHashMap == null) {
            animatorConcurrentHashMap = new ConcurrentHashMap<>();
        }
        Iterator it = animatorConcurrentHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            ValueAnimator val = (ValueAnimator) entry.getValue();
            String key = (String) entry.getKey();
            if (!val.isRunning()) {
                animatorConcurrentHashMap.remove(key);
            } else if (key.equals(textView.getId() + onlyKey)) {
                val.cancel();
                animatorConcurrentHashMap.remove(key);
            }
        }
        if (oldnum.contains("¥") || oldnum.contains("$")) {
            oldnum = oldnum.substring(1);
        }
        if (newnum.contains("¥") || newnum.contains("$")) {
            newnum = oldnum.substring(1);
        }
        if (new BigDecimal(oldnum).compareTo(new BigDecimal(newnum)) == 1) {
            //降价
            textView.setTextColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));
        } else if (new BigDecimal(oldnum).compareTo(new BigDecimal(newnum)) == -1) {
            //涨价
            textView.setTextColor(CommonUtils.getColor(UserSet.getinstance().getRiseColor()));
        } else {
            return;
        }
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.setDuration(3000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                if (currentValue == 1f) {
                    //动画结束
                    if (textView != null) {
                        textView.setTextColor(endColor);
                    }
                }
            }
        });
        anim.start();
        animatorConcurrentHashMap.put(textView.getId() + onlyKey, anim);
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
        if (UserSet.getinstance().isUseCustomRate()) {
            useCustomRate();
        }
    }

    //获取自定义汇率
    public Map<String, BigDecimal> getCustomRate() {
        String string = CacheUtils.getInstance().getString(CACHE_CUSTOM_RATE);
        if (TextUtils.isEmpty(string)) {
            Map<String, BigDecimal> data = new LinkedHashMap<>();
            for (Map.Entry<String, BigDecimal> entry : usdRate.entrySet()) {
                String k = entry.getKey();
                BigDecimal v = entry.getValue();
                String[] split = k.split(",");
                if (split[0].contains("CNY") || split[0].contains("JPY") || split[0].contains("KRW") || split[0].contains("EUR")) {
                    data.put(k, v);
                }
            }
            CacheUtils.getInstance().put(CACHE_CUSTOM_RATE, GsonUtil.getInstance().toJson(data));
            return data;
        } else {
            return GsonUtil.getInstance().toMap(string, new TypeReference<Map<String, BigDecimal>>() {
            });
        }
    }

    //单个保存 自定义汇率
    public void putCustomRate(String key, BigDecimal value) {
        String string = CacheUtils.getInstance().getString(CACHE_CUSTOM_RATE);
        Map<String, BigDecimal> stringBigDecimalMap = GsonUtil.getInstance().toMap(string, new TypeReference<Map<String, BigDecimal>>() {
        });
        stringBigDecimalMap.put(key, value);
        CacheUtils.getInstance().put(CACHE_CUSTOM_RATE, GsonUtil.getInstance().toJson(stringBigDecimalMap));
    }

    //保存全部自定义汇率
    public void putCustomRate(Map<String, BigDecimal> stringBigDecimalMap) {
        CacheUtils.getInstance().put(CACHE_CUSTOM_RATE, GsonUtil.getInstance().toJson(stringBigDecimalMap));
    }

    //使用自定义汇率
    public void useCustomRate() {
        if (usdRate == null) {
            usdRate = new ConcurrentHashMap<>();
        }
        String string = CacheUtils.getInstance().getString(CACHE_CUSTOM_RATE);
        //解析汇率
        Map<String, BigDecimal> data = GsonUtil.getInstance().toMap(string, new TypeReference<Map<String, BigDecimal>>() {
        });
        for (Map.Entry<String, BigDecimal> entry : data.entrySet()) {
            String k = entry.getKey();
            BigDecimal v = entry.getValue();
            String[] split = k.split(",");
            if (split.length == 2) {
                if ("USD".equals(split[1])) {
                    usdRate.put(split[0], v);
                }
            }
        }
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

    public List<String> rateUSDAndCNY(String price, String symbol, String itemUnit) {
        if ("USDT".equals(itemUnit)) {
            itemUnit = "USD";
        }
        if ("USDT".equals(symbol)) {
            symbol = "USD";
        }
        //多价格汇率
        if (rateTwoPrice == null) {
            rateTwoPrice = new ArrayList<>();
        } else {
            rateTwoPrice.clear();
        }
        if (usdRate == null) {
            rateTwoPrice.add("");
            rateTwoPrice.add("");
        }
        if (TextUtils.isEmpty(price) || TextUtils.isEmpty(itemUnit)) {
            rateTwoPrice.add(bigPrice(price));
            rateTwoPrice.add("");
            return rateTwoPrice;
        }
        if (usdRate.containsKey(itemUnit)) {
            rateTwoPrice.add("$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString()));
        } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
            rateTwoPrice.add("$" + bigPrice(rate(symbol, "USD")));
        } else {
            rateTwoPrice.add("");
        }
        if (usdRate.containsKey(itemUnit)) {
            rateTwoPrice.add("¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString()));
        } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
            rateTwoPrice.add("¥" + bigPrice(rate(symbol, "CNY")));
        } else {
            rateTwoPrice.add("");
        }
        return rateTwoPrice;
    }

    //多价格展示页面调用
    public List<String> rateTwoPrice(String price, String symbol, String itemUnit) {

        if ("USDT".equals(itemUnit)) {
            itemUnit = "USD";
        }
        if ("USDT".equals(symbol)) {
            symbol = "USD";
        }


        //多价格汇率
        if (rateTwoPrice == null) {
            rateTwoPrice = new ArrayList<>();
        } else {
            rateTwoPrice.clear();
        }
        if (usdRate == null) {
            rateTwoPrice.add("");
            rateTwoPrice.add("");
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
                rateTwoPrice.add("¥" + bigPrice(rate(symbol, "CNY")));
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
                rateTwoPrice.add("$" + bigPrice(rate(symbol, "USD")));
            } else {
                rateTwoPrice.add("");
            }
            return rateTwoPrice;
        } else if (3 == setUnits.indexOf(unit)) {
            //    cny-平台价格=cny
            if (usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString()));
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("¥" + bigPrice(rate(symbol, "CNY")));
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
                rateTwoPrice.add("$" + bigPrice(rate(symbol, "USD")));
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
                rateTwoPrice.add("$" + bigPrice(rate(symbol, "USD")));
            } else {
                rateTwoPrice.add("");
            }
            if (usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString()));
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("¥" + bigPrice(rate(symbol, "CNY")));
            } else {
                rateTwoPrice.add("");
            }
            return rateTwoPrice;
        } else if (6 == setUnits.indexOf(unit)) {
            //    cny-usd=cny
            if (usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString()));
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("¥" + bigPrice(rate(symbol, "CNY")));
            } else {
                rateTwoPrice.add("");
            }
            if (usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString()));
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("$" + bigPrice(rate(symbol, "USD")));
            } else {
                rateTwoPrice.add("");
            }
            return rateTwoPrice;
        } else if (7 == setUnits.indexOf(unit)) {
            //    仅usd=usd
            if (usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString()));
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                rateTwoPrice.add("$" + bigPrice(rate(symbol, "USD")));
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
                rateTwoPrice.add("¥" + bigPrice(rate(symbol, "CNY")));
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
        if ("USDT".equals(itemUnit)) {
            itemUnit = "USD";
        }
        if ("USDT".equals(symbol)) {
            symbol = "USD";
        }
        //市值页面只显示 单价格
        String unit = UserSet.getinstance().getUnit();
        if (setUnits == null) {
            setUnits = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_unit));
        }
        if (-1 == setUnits.indexOf(unit)) {
            return "$" +bigMarkValue(price);
        } else if (0 == setUnits.indexOf(unit)) {
            //  默认
            return "$" +bigMarkValue(price);
        } else if (1 == setUnits.indexOf(unit)) {
            //    平台价格-cny=cny
            if (usdRate.containsKey(itemUnit)) {
                return "¥" + bigMarkValue(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "¥" + bigMarkValue(rate(symbol, "CNY"));
            }
        } else if (2 == setUnits.indexOf(unit)) {
            //    平台价格-usd=usd
            if (usdRate.containsKey(itemUnit)) {
                return "$" + bigMarkValue(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "$" + bigMarkValue(rate(symbol, "USD"));
            }
        } else if (3 == setUnits.indexOf(unit)) {
            //    cny-平台价格=cny
            if (usdRate.containsKey(itemUnit)) {
                return "¥" + bigMarkValue(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "¥" + bigMarkValue(rate(symbol, "CNY"));
            }
        } else if (4 == setUnits.indexOf(unit)) {
            //    usd-平台价格=usd
            if (usdRate.containsKey(itemUnit)) {
                return "$" + bigMarkValue(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "$" + bigMarkValue(rate(symbol, "USD"));
            }
        } else if (5 == setUnits.indexOf(unit)) {
            //    usd-cny=usd
            if (usdRate.containsKey(itemUnit)) {
                return "$" + bigMarkValue(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "$" + bigMarkValue(rate(symbol, "USD"));
            }
        } else if (6 == setUnits.indexOf(unit)) {
            //    cny-usd=cny
            if (usdRate.containsKey(itemUnit)) {
                return "¥" + bigMarkValue(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "¥" + bigMarkValue(rate(symbol, "CNY"));
            }
        } else if (7 == setUnits.indexOf(unit)) {
            //    仅usd=usd
            if (usdRate.containsKey(itemUnit)) {
                return "$" + bigMarkValue(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "$" + bigMarkValue(rate(symbol, "USD"));
            }
        } else if (8 == setUnits.indexOf(unit)) {
            //    仅cny=cny
            if (usdRate.containsKey(itemUnit)) {
                return "¥" + bigMarkValue(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "¥" + bigMarkValue(rate(symbol, "CNY"));
            }
        } else if (9 == setUnits.indexOf(unit)) {
            //    仅平台价格=usd
            if (usdRate.containsKey(itemUnit)) {
                return "$" + bigMarkValue(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "$" + bigMarkValue(rate(symbol, "USD"));
            }
        }
        return price;
    }
    //仅市值页面调用
    public String rateMarketPrice(String price, String symbol, String itemUnit) {
        if (TextUtils.isEmpty(price) || TextUtils.isEmpty(itemUnit)) {
            return price;
        }
        if ("USDT".equals(itemUnit)) {
            itemUnit = "USD";
        }
        if ("USDT".equals(symbol)) {
            symbol = "USD";
        }
        //市值页面只显示 单价格
        String unit = UserSet.getinstance().getUnit();
        if (setUnits == null) {
            setUnits = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_unit));
        }
        if (-1 == setUnits.indexOf(unit)) {
            return "$" +bigPrice(price);
        } else if (0 == setUnits.indexOf(unit)) {
            //  默认
            return "$" +bigPrice(price);
        } else if (1 == setUnits.indexOf(unit)) {
            //    平台价格-cny=cny
            if (usdRate.containsKey(itemUnit)) {
                return "¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "¥" + bigPrice(rate(symbol, "CNY"));
            }
        } else if (2 == setUnits.indexOf(unit)) {
            //    平台价格-usd=usd
            if (usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(rate(symbol, "USD"));
            }
        } else if (3 == setUnits.indexOf(unit)) {
            //    cny-平台价格=cny
            if (usdRate.containsKey(itemUnit)) {
                return "¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "¥" + bigPrice(rate(symbol, "CNY"));
            }
        } else if (4 == setUnits.indexOf(unit)) {
            //    usd-平台价格=usd
            if (usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(rate(symbol, "USD"));
            }
        } else if (5 == setUnits.indexOf(unit)) {
            //    usd-cny=usd
            if (usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(rate(symbol, "USD"));
            }
        } else if (6 == setUnits.indexOf(unit)) {
            //    cny-usd=cny
            if (usdRate.containsKey(itemUnit)) {
                return "¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "¥" + bigPrice(rate(symbol, "CNY"));
            }
        } else if (7 == setUnits.indexOf(unit)) {
            //    仅usd=usd
            if (usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(rate(symbol, "USD"));
            }
        } else if (8 == setUnits.indexOf(unit)) {
            //    仅cny=cny
            if (usdRate.containsKey(itemUnit)) {
                return "¥" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "CNY"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "¥" + bigPrice(rate(symbol, "CNY"));
            }
        } else if (9 == setUnits.indexOf(unit)) {
            //    仅平台价格=usd
            if (usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(new BigDecimal(price).multiply(new BigDecimal(rate(itemUnit, "USD"))).toPlainString());
            } else if (usdRate.containsKey(symbol) && !usdRate.containsKey(itemUnit)) {
                return "$" + bigPrice(rate(symbol, "USD"));
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
            if (unitBig.compareTo(new BigDecimal("0")) == 0) {
                return new BigDecimal("0");
            }
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


    //价格单位 显示规则
    //    >>>价格
    //        123456.12
    //        1234.12
    //        123.12
    //        12.1234
    //        1.1234
    //        0.1111
    //        0.01111
    //        0.001111
    //        0.0001111
    //        0.00001111
    //        0.00000111
    //        0.00000011
    //        0.00000001
    //        0
    //
    //
    //
    public String bigPrice(String price) {
        if (TextUtils.isEmpty(price) || "NaN".equals(price)) {
            return "";
        }
        BigDecimal bigDecimal = new BigDecimal(price);
        StringBuffer stringBuffer = new StringBuffer();
        if (new BigDecimal("100").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
        } else if (new BigDecimal("0.1").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(4, BigDecimal.ROUND_DOWN).toPlainString());
        } else if (new BigDecimal("0.01").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(5, BigDecimal.ROUND_DOWN).toPlainString());
        } else if (new BigDecimal("0.001").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(6, BigDecimal.ROUND_DOWN).toPlainString());
        } else if (new BigDecimal("0.00000001").compareTo(bigDecimal) == -1) {
            stringBuffer.append(bigDecimal.setScale(8, BigDecimal.ROUND_DOWN).toPlainString());
        } else {
            return "0";
        }
        return stringBuffer.toString();
    }

    //量 单位 显示规则
    //        >>>量
    //0
    //        0.0001
    //        0.0011
    //        0.011
    //        0.11
    //        1.23
    //        12.35
    //        123.46
    //        1235
    //        1.23万
    //        12.3万
    //        123万
    //        1235万
    //        1.2亿
    //        12.3亿
    //        123亿
    //        1235亿
    //
    //
    public String bigAmount(String amount) {
        if (TextUtils.isEmpty(amount)) {
            return "";
        }
        if ("NaN".equals(amount)) {
            return "";
        }
        BigDecimal bigDecimal = new BigDecimal(amount);
        if (new BigDecimal("10000000000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("0.00000001")).setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "亿";
        } else if (new BigDecimal("100000000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("0.00000001")).setScale(1, BigDecimal.ROUND_DOWN).toPlainString() + "亿";
        } else if (new BigDecimal("1000000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("0.0001")).setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "万";
        } else if (new BigDecimal("100000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("0.0001")).setScale(1, BigDecimal.ROUND_DOWN).toPlainString() + "万";
        } else if (new BigDecimal("10000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("0.0001")).setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "万";
        } else if (new BigDecimal("1000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("1")).setScale(0, BigDecimal.ROUND_DOWN).toPlainString();
        } else if (new BigDecimal("0").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("1")).setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
        } else if (new BigDecimal("0.01").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("1")).setScale(3, BigDecimal.ROUND_DOWN).toPlainString();
        } else if (new BigDecimal("0.001").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("1")).setScale(4, BigDecimal.ROUND_DOWN).toPlainString();
        } else {
            return "0";
        }
    }

    //  市值
    //1111万亿
    //111万亿
    //11.11万亿
    //1.1111万亿
    //1111亿
    //111亿
    //11.11亿
    //1.1111亿
    //1111万
    //111万
    //11.11万
    //1.1111万
    //1111
    //111
    //11.11
    //1.1111
    //0
    public String bigMarkValue(String amount) {
        if (TextUtils.isEmpty(amount)) {
            return "";
        }
        BigDecimal bigDecimal = new BigDecimal(amount);
        if (new BigDecimal("100000000000000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("0.000000000001")).setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "万亿";
        } else if (new BigDecimal("10000000000000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("0.000000000001")).setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "万亿";
        } else if (new BigDecimal("1000000000000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("0.000000000001")).setScale(4, BigDecimal.ROUND_DOWN).toPlainString() + "万亿";
        } else if (new BigDecimal("10000000000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("0.00000001")).setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "亿";
        } else if (new BigDecimal("1000000000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("0.00000001")).setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "亿";
        } else if (new BigDecimal("100000000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("0.00000001")).setScale(4, BigDecimal.ROUND_DOWN).toPlainString() + "亿";
        } else if (new BigDecimal("1000000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("0.0001")).setScale(0, BigDecimal.ROUND_DOWN).toPlainString() + "万";
        } else if (new BigDecimal("100000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("0.0001")).setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "万";
        } else if (new BigDecimal("10000").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("0.0001")).setScale(4, BigDecimal.ROUND_DOWN).toPlainString() + "万";
        } else if (new BigDecimal("100").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("1")).setScale(0, BigDecimal.ROUND_DOWN).toPlainString();
        } else if (new BigDecimal("10").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("1")).setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
        } else if (new BigDecimal("1").compareTo(bigDecimal) == -1) {
            return bigDecimal.multiply(new BigDecimal("1")).setScale(4, BigDecimal.ROUND_DOWN).toPlainString();
        } else {
            return "0";
        }
    }


    //百分比显示
    public String changeAmount(String change) {
        if (TextUtils.isEmpty(change)) {
            return "--";
        }
        BigDecimal bigDecimal = new BigDecimal(change);
        return bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
    }
}
