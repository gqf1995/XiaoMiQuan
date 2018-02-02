package com.xiaomiquan.utils;

import android.text.TextUtils;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.SaveUtil;
import com.xiaomiquan.R;

import skin.support.SkinCompatManager;

/**
 * Created by 郭青枫 on 2018/1/12 0012.
 * 用户设置单例
 * <p>
 * 红涨绿跌
 * 夜间模式
 * 统一默认单位
 * 语言切换]
 * 开关自定义汇率
 * k线用户操作保存
 */

public class UserSet {


    private static class userSet {
        private static UserSet userSet = new UserSet();
    }

    public static UserSet getinstance() {
        return userSet.userSet;
    }

    public int getDropColor() {
        return isRedRise() ? R.color.decreasing_color : R.color.increasing_color;
    }

    public int getRiseColor() {
        return isRedRise() ? R.color.increasing_color : R.color.decreasing_color;
    }

    public boolean isRedRise() {
        return SaveUtil.getInstance().getBoolean("isRedRise");
    }

    public void setRedRise(boolean isRedRise) {
        SaveUtil.getInstance().saveBoolean("isRedRise", isRedRise);
    }

    public String getLanguage() {
        String language = SaveUtil.getInstance().getString("language");
        return TextUtils.isEmpty(language) ? "zh-cn" : language;
    }

    public String getUnit() {
        String unit = SaveUtil.getInstance().getString("unit");
        return TextUtils.isEmpty(unit) ? CommonUtils.getString(R.string.str_default) : unit;
    }

    public String getShowUnit() {
        String unit = SaveUtil.getInstance().getString("unit");
        return TextUtils.isEmpty(unit) ? CommonUtils.getString(R.string.str_default) : unit;
    }

    public String getCNYUnit() {
        return "CNY";
    }

    public String getUSDUnit() {
        return "USD";
    }

    public void setLanguage(String language) {
        SaveUtil.getInstance().saveString("language", language);
    }

    public void setUnit(String unit) {
        SaveUtil.getInstance().saveString("unit", unit);
    }

    public boolean isNight() {
        return SaveUtil.getInstance().getBoolean("isNight");
    }

    public boolean isUnitDefalt() {
        return "default".equals(getUnit());
    }

    //用户设置k线背景
    public void setKBg(String kBg, int position) {
        SaveUtil.getInstance().saveString("kBg", kBg);
        SaveUtil.getInstance().saveString("kBgPosition", position + "");
    }

    public int getKBgSelectPosition() {
        String position = SaveUtil.getInstance().getString("kBgPosition");
        int p = 0;
        if (!TextUtils.isEmpty(position)) {
            p = Integer.parseInt(position);
        }
        return p;
    }

    public int getKBgColor() {
        String kBg = SaveUtil.getInstance().getString("kBg");
        if (TextUtils.isEmpty(kBg)) {
            return CommonUtils.getColor(R.color.colorPrimary);
        } else {
            return Integer.parseInt(kBg);
        }
    }

    //用户设置k线缩放级别
    public float getKlineScale() {
        String KlineScale = SaveUtil.getInstance().getString("KlineScale");
        if (TextUtils.isEmpty(KlineScale)) {
            return 1f;
        } else {
            return Float.parseFloat(KlineScale);
        }
    }

    public void setKlineScale(float KlineScale) {
        SaveUtil.getInstance().saveString("KlineScale", KlineScale + "");
    }


    //用户设置k线分钟
    public void setKTime(String kTime) {
        SaveUtil.getInstance().saveString("kTime", kTime);
    }

    public String getKTime() {
        String kTime = SaveUtil.getInstance().getString("kTime");
        return TextUtils.isEmpty(kTime) ? "1m" : kTime;
    }

    //用户设置k线显示数据


    public void setNight(boolean isNight) {
        SaveUtil.getInstance().saveBoolean("isNight", isNight);
        if (!isNight) {
            SkinCompatManager.getInstance().restoreDefaultTheme();
        } else {
            SkinCompatManager.getInstance().loadSkin("light.skin", SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS);
        }
    }

    //自定义汇率
    public void setIsUseCustomRate(boolean isUseCustomRate) {
        if (isUseCustomRate) {
            BigUIUtil.getinstance().useCustomRate();
        }
        SaveUtil.getInstance().saveBoolean("isUseCustomRate", isUseCustomRate);
    }

    public boolean isUseCustomRate() {
        return SaveUtil.getInstance().getBoolean("isUseCustomRate");
    }


}
