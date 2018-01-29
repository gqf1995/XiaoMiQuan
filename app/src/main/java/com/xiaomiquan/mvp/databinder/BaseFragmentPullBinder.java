package com.xiaomiquan.mvp.databinder;


import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.xiaomiquan.server.HttpUrl;
import com.yanzhenjie.nohttp.rest.CacheMode;

import io.reactivex.disposables.Disposable;

/**
 * Created by 郭青枫 on 2017/9/27.
 * 统一的 fragment列表接口 代理
 */

public class BaseFragmentPullBinder extends BaseDataBind<BaseFragentPullDelegate> {
    public BaseFragmentPullBinder(BaseFragentPullDelegate viewDelegate) {
        super(viewDelegate);
    }


    /**
     * 市值
     */
    public Disposable getAllMarketCaps(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getAllMarketCaps)
                .setShowDialog(false)
                .setRequestName("获得所有市值信息 未做排序")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }


    /**
     * 交易所
     */
    public Disposable getAllEXchange(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getAllEXchange)
                .setShowDialog(false)
                .setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE)
                .setRequestName("获得所有库中拥有的交易所列表")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 添加自选页面
     */
    public Disposable show(
            String name,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("name", name);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().show)
                .setShowDialog(false)
                .setCacheMode(CacheMode.ONLY_REQUEST_NETWORK)
                .setRequestName("添加自选页面")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 订阅数据展示
     */
    public Disposable marketdata(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().marketdata)
                .setShowDialog(false)
                .setRequestName("订阅数据展示")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 文章
     */
    public Disposable listArticleByPage(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("platform", "1");
        baseMap.put("pageNum", viewDelegate.page);
        baseMap.put("pageSize", viewDelegate.pagesize);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().listArticleByPage)
                .setShowDialog(false)
                .setRequestName("订阅数据展示")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 搜索
     */
    public Disposable getAllMarketByExchangeOrSymbol(
            String name,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("name", name);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getAllMarketByExchangeOrSymbol)
                .setShowDialog(false)
                .setRequestName("搜索")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

    /**
     * 单独订阅/取消
     */
    public Disposable singlesubs(
            String onlykey,
            String symbol,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("onlykey", onlykey);
        baseMap.put("symbol", symbol);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().singlesubs)
                .setShowDialog(false)
                .setRequestName("单独订阅/取消")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

}