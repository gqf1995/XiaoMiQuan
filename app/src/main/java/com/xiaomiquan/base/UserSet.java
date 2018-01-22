package com.xiaomiquan.base;

import android.text.TextUtils;

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
 * 语言切换
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
        return TextUtils.isEmpty(unit) ? "default" : unit;
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

    public void setNight(boolean isNight) {
        SaveUtil.getInstance().saveBoolean("isNight", isNight);
        if (!isNight) {
            SkinCompatManager.getInstance().restoreDefaultTheme();
        } else {
            SkinCompatManager.getInstance().loadSkin("night.skin", SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS);
        }
    }

}
