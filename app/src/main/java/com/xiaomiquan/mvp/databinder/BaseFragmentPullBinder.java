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
        baseMap.put("offset", viewDelegate.page);
        baseMap.put("limit", viewDelegate.pagesize);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getAllMarketCaps)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
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
                .setRequestMode(HttpRequest.RequestMode.POST)
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

    /**
     * 交易所 数据
     */
    public Disposable getAllMarketByExchange(
            String exchangeName,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("exchangeName", exchangeName);
        baseMap.put("offset", viewDelegate.page);
        baseMap.put("limit", viewDelegate.pagesize);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getAllMarketByExchange)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("根据交易所名称获得相关信息")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

    /**
     * 交易所 数据
     */
    public Disposable getAllMarketBySymbol(
            String coinName,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("coinName", coinName);
        baseMap.put("offset", viewDelegate.page);
        baseMap.put("limit", viewDelegate.pagesize);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().getAllMarketBySymbol)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("根据币种名称获得相关信息")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();

    }

    /**
     * 我的组合
     */
    public Disposable listDemo(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().listDemo)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("我的组合")
                .setRequestMode(HttpRequest.RequestMode.GET)
                .setParameterMode(HttpRequest.ParameterMode.KeyValue)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 排行榜
     */
    public Disposable top(
            String type,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("type", type);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().top)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("排行榜")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 币种列表
     */
    public Disposable searchCoin(
            String name,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("name", name);//搜索
        baseMap.put("pageNum", viewDelegate.page);
        baseMap.put("pageSize", viewDelegate.pagesize);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().searchCoin)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("币种列表")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 持有币种列表
     */
    public Disposable myCoin(
            String demoId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("demoId", demoId);
        baseMap.put("pageNum", viewDelegate.page);
        baseMap.put("pageSize", viewDelegate.pagesize);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().myCoin)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("持有币种列表")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }


    /**
     * 成交历史
     */
    public Disposable listDeal(
            String demoId,
            String status,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("demoId", demoId);
        baseMap.put("status", status);// 1：已成交 2：未成交
        baseMap.put("pageNum", viewDelegate.page);
        baseMap.put("pageSize", viewDelegate.pagesize);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().listDeal)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("成交历史")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 委托历史
     */
    public Disposable history(
            String demoId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("demoId", demoId);
        baseMap.put("pageNum", viewDelegate.page);
        baseMap.put("pageSize", viewDelegate.pagesize);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().history)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("委托历史")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 持仓明细
     */
    public Disposable listPosition(
            String demoId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("demoId", demoId);
        baseMap.put("pageNum", viewDelegate.page);
        baseMap.put("pageSize", viewDelegate.pagesize);
        return new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(HttpUrl.getIntance().listPosition)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("持仓明细")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 撤单
     */
    public Disposable cancel(
            String demoId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("dealId", demoId);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().cancel)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("撤单")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 关注组合
     */
    public Disposable demoattention(
            String demoId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("userId", demoId);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().demoattention)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("关注组合")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 取消关注组合
     */
    public Disposable cancelAttention(
            String demoId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("userId", demoId);
        return new HttpRequest.Builder()
                .setRequestCode(0x124)
                .setRequestUrl(HttpUrl.getIntance().cancelAttention)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("取消关注组合")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }
    /**
     * 组合动态
     */
    public Disposable dynamic(
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("pageNum",viewDelegate.page);
        baseMap.put("pageSize",viewDelegate.pagesize);
        return new HttpRequest.Builder()
                .setRequestCode(0x125)
                .setRequestUrl(HttpUrl.getIntance().dynamic)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("组合动态")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

}