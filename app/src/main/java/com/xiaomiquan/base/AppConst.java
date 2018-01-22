package com.xiaomiquan.base;

/**
 * 全局配置
 *
 * 缓存key 保存地
 */
public class AppConst {

    /**
     * 测试
     */
    //public static final String httpBaseUrl = "http://192.168.31.12:8888";
    //public static final String httpBaseUrl = "http://192.168.168.118:8888";
    //public static final String httpBaseUrl = "http://192.168.168.114:8888";
    //public static final String httpBaseUrl = "http://116.62.120.179:82";
    //public static final String httpBaseUrl = "http://192.168.0.24:1901";
    public static final String httpBaseUrl = "http://47.97.169.136:1901";
    public static final String httpBaseUrl2 = "http://47.97.169.136:1903";
    public static final String httpBaseUrl3 = "http://47.97.169.136:1901";
    //圈子专用
    //public static final String httpBaseUrl4 = "http://47.97.169.136:1901";
    public static final String httpBaseUrl4 = "http://192.168.0.11:8080";
    //http://192.168.18.190:1903/api/unregisterkeys
    public static final String serviceId = "KEFU151064005015908";
    public static final String rongId = "cpj2xarlc1osn";
    public static final boolean isLog = true;
    public static final boolean isSSL = false;
    public static final boolean isEditUrl = true;


    /**
     * 正式
     */
    //        public static final String httpBaseUrl = "https://otc.forotc.com";
    //        public static final String serviceId = "KEFU151082869574315";
    //        public static final String rongId = "pgyu6atqp9inu";
    //        public static final boolean isLog = false;
    //        public static final boolean isSSL = true;
    //        public static final boolean isEditUrl=false;


    public static final String CACHE_EXCHANGENAME = "cache_exchangeName";//交易所 名称缓存
    public static final String CACHE_EXCHANGE_RATE = "cache_exchange_rate";//汇率缓存
    public static final String CACHE_KLINE = "cache_Kline";//

}
