package com.xiaomiquan.widget;

import android.animation.TypeEvaluator;

/**
 * Created by 郭青枫 on 2018/2/1 0001.
 */

public class ColorEvaluator implements TypeEvaluator {

    private int mCurrentRed = -1;

    private int mCurrentGreen = -1;

    private int mCurrentBlue = -1;

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
//        String startColor = (String) startValue;
//        String endColor = (String) endValue;
        int startColor = (int) startValue;
        int endColor = (int) endValue;


        int startRed =(startColor & 0x00ff0000) >> 16;
        int startGreen = (startColor & 0x0000ff00) >> 8;
        int startBlue = (startColor & 0x000000ff);
        int endRed = (endColor & 0x00ff0000) >> 16;
        int endGreen = (endColor & 0x0000ff00) >> 8;
        int endBlue = (endColor & 0x000000ff);
        // 初始化颜色的值
        if (mCurrentRed == -1) {
            mCurrentRed = startRed;
        }
        if (mCurrentGreen == -1) {
            mCurrentGreen = startGreen;
        }
        if (mCurrentBlue == -1) {
            mCurrentBlue = startBlue;
        }
        // 计算初始颜色和结束颜色之间的差值
        int redDiff = Math.abs(startRed - endRed);
        int greenDiff = Math.abs(startGreen - endGreen);
        int blueDiff = Math.abs(startBlue - endBlue);
        int colorDiff = redDiff + greenDiff + blueDiff;
        if (mCurrentRed != endRed) {
            mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0,
                    fraction);
        } else if (mCurrentGreen != endGreen) {
            mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff,
                    redDiff, fraction);
        } else if (mCurrentBlue != endBlue) {
            mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff,
                    redDiff + greenDiff, fraction);
        }
        // 将计算出的当前颜色的值组装返回
        String currentColor = "#" + getHexString(mCurrentRed)
                + getHexString(mCurrentGreen) + getHexString(mCurrentBlue);
        return currentColor;
    }

    /**
     * 根据fraction值来计算当前的颜色。
     */
    private int getCurrentColor(int startColor, int endColor, int colorDiff,
                                int offset, float fraction) {
        int currentColor;
        if (startColor > endColor) {
            currentColor = (int) (startColor - (fraction * colorDiff - offset));
            if (currentColor < endColor) {
                currentColor = endColor;
            }
        } else {
            currentColor = (int) (startColor + (fraction * colorDiff - offset));
            if (currentColor > endColor) {
                currentColor = endColor;
            }
        }
        return currentColor;
    }

    /**
     * 将10进制颜色值转换成16进制。
     */
    private String getHexString(int value) {
        String hexString = Integer.toHexString(value);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }

}
