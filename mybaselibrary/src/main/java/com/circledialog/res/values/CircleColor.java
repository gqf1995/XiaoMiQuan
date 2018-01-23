package com.circledialog.res.values;

import com.fivefivelike.mybaselibrary.R;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;

/**
 * Created by hupei on 2017/3/29.
 */
public class CircleColor {

    /**
     * 对话框背景色
     */
    public static final int bgDialog = CommonUtils.getColor(R.color.colorPrimary);

    /**
     * 标题颜色
     */
    public static final int title = CommonUtils.getColor(R.color.color_font2);//0xFF000000;
    /**
     * 消息内容颜色
     */
    public static final int content = 0xFF8F8F8F;
    /**
     * 按钮颜色
     */
    public static final int button = 0xFF007AFF;
    /**
     * 线条颜色
     */
    public static final int divider = CommonUtils.getColor(R.color.base_mask);// 0xFFD7D7DB;
    /**
     * 按钮点击颜色
     */
    public static final int buttonPress = 0xFFEAEAEA;
    /**
     * 输入框边框颜色
     */
    public static final int inputStroke = CommonUtils.getColor(R.color.base_mask);
}
