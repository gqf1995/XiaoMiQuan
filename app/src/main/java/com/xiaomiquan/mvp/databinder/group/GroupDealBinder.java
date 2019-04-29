package com.xiaomiquan.mvp.databinder.group;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;
import com.xiaomiquan.mvp.delegate.group.GroupDealDelegate;
import com.xiaomiquan.server.HttpUrl;

import io.reactivex.disposables.Disposable;

/**
 * Created by Andy on 2018/1/25.
 */

public class GroupDealBinder extends BaseDataBind<GroupDealDelegate> {
    public GroupDealBinder(GroupDealDelegate viewDelegate) {
        super(viewDelegate);
    }


    //    /**
    //     * 获取余额
    //     */
    //    public Disposable getBalance(
    //            String demoId,
    //            RequestCallback requestCallback) {
    //        getBaseMapWithUid();
    //        baseMap.put("demoId", demoId);
    //        return new HttpRequest.Builder()
    //                .setRequestCode(0x123)
    //                .setRequestUrl(HttpUrl.getIntance().getBalance)
    //                .setShowDialog(true)
    //                .setDialog(viewDelegate.getNetConnectDialog())
    //                .setRequestName("获取余额")
    //                .setRequestMode(HttpRequest.RequestMode.POST)
    //                .setParameterMode(HttpRequest.ParameterMode.Json)
    //                .setRequestObj(baseMap)
    //                .setRequestCallback(requestCallback)
    //                .build()
    //                .RxSendRequest();
    //    }

    /**
     * 申请买卖
     * {
     * "demoId": 1,		//组合ID
     * "type": 1,			//1：买 2：卖
     * "coinId": 1,		// 币种ID
     * "price": 10180,		//委托价格
     * "priceType": 2,		// 1：限价 2：市价
     * "count": 2.2,		//交易数量
     * "tradeType": 1      // new 1：做多 2：做空
     * }
     */
    public Disposable deal(
            String demoId,
            String type,
            String coinId,
            String price,
            String priceType,
            String count,
            String tradeType,
            int code,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("demoId", demoId);
        baseMap.put("type", type);
        baseMap.put("coinId", coinId);
        baseMap.put("price", price);
        baseMap.put("priceType", priceType);
        baseMap.put("count", count);
        baseMap.put("tradeType", tradeType);
        return new HttpRequest.Builder()
                .setRequestCode(code)
                .setRequestUrl(HttpUrl.getIntance().deal)
                .setShowDialog(true)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("申请买卖")
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
     * 组合详情
     */
    public Disposable getDemoByUserId(
            String userId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("userId", userId);
        return new HttpRequest.Builder()
                .setRequestCode(0x125)
                .setRequestUrl(HttpUrl.getIntance().getDemoByUserId)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("组合详情")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }

    /**
     * 我的资产
     */
    public Disposable myAsset(
            String demoId,
            RequestCallback requestCallback) {
        getBaseMapWithUid();
        baseMap.put("demoId", demoId);
        return new HttpRequest.Builder()
                .setRequestCode(0x126)
                .setRequestUrl(HttpUrl.getIntance().myAsset)
                .setShowDialog(false)
                .setDialog(viewDelegate.getNetConnectDialog())
                .setRequestName("我的资产")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(requestCallback)
                .build()
                .RxSendRequest();
    }
}
