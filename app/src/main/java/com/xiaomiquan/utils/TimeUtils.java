package com.xiaomiquan.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @创建者 gqf
 * @描述 时间工具
 * <p>
 * 时间工具调用统一入口
 */
public class TimeUtils {

    public static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public String toYearMonthsDay(long data) {
        return com.blankj.utilcode.util.TimeUtils.millis2String(data, DEFAULT_FORMAT);

    }


}
