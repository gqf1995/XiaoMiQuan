package com.xiaomiquan.base;

/**
 * 全局配置
 */
public class AppConst {

    /**
     * websocket  域名为http://topcoin.bicoin.com.cn/
     * topcoin的域名为  http://topcoin.bicoin.com.cn/topcoin/
     * 正式
     */
    public static final String httpBaseUrl = "http://topcoin.bicoin.com.cn/topcoin";
    public static final String serviceId = "KEFU151849750997586";
    public static final String rongId = "pgyu6atqp96nu";
    public static final boolean isLog = false;
    public static final boolean isEditUrl = false;

    /**
     * 测试
     */
//        //public static final String httpBaseUrl = "http://47.96.180.179:1904";
//        public static final String httpBaseUrl = "http://test.bicoin.com.cn/topcoin";
//        //public static final String httpBaseUrl = "http://192.168.0.28:8080";
//        //public static final String httpBaseUrl = "http://192.168.0.18:8080";
//        //public static final String httpBaseUrl = "http://192.168.0.1:8080";
//        public static final String serviceId = "KEFU151728371459995";
//        public static final String rongId = "cpj2xarlc1xsn";
//        public static final boolean isLog = false;
//        public static final boolean isEditUrl = false;
//        public static final boolean isSSL = true;


    public static final String CACHE_EXCHANGENAME = "cache_exchangeName";//交易所 名称缓存
    public static final String CACHE_EXCHANGE_RATE = "cache_exchange_rate";//汇率缓存
    public static final String CACHE_KLINE = "cache_Kline";//
    public static final String CACHE_CHOOSE = "cache_choose";//用户自选 onlykey
    public static final String CACHE_SEARCH_HISTORY = "cache_search_history";//搜索历史
    public static final String CACHE_CUSTOM_RATE = "cache_custom_rate";//搜索历史
    public static final String rulesUrl = "http://rule.bicoin.com.cn";
    public static final String myRewardUrl = "http://rule.bicoin.com.cn/35/16/p501606933c9693";

}
