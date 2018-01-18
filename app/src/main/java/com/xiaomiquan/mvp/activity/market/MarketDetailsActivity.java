package com.xiaomiquan.mvp.activity.market;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.CacheUtils;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.entity.bean.kline.DataParse;
import com.xiaomiquan.entity.bean.kline.KLineBean;
import com.xiaomiquan.mvp.databinder.MarketDetailsBinder;
import com.xiaomiquan.mvp.delegate.MarketDetailsDelegate;
import com.xiaomiquan.widget.chart.KlineDraw;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.xiaomiquan.base.AppConst.CACHE_KLINE;

public class MarketDetailsActivity extends BaseDataBindActivity<MarketDetailsDelegate, MarketDetailsBinder> {
    private DataParse mData;
    KlineDraw klineDraw;
    ExchangeData exchangeData;
    List<KLineBean> lineBeans;

    int updataTime = 10;//刷新时间

    //    涨幅是这一根k线的【收盘/开盘-1】
    //    振幅是【（最高-最低）/开盘】
    @Override
    protected Class<MarketDetailsDelegate> getDelegateClass() {
        return MarketDetailsDelegate.class;
    }

    @Override
    public MarketDetailsBinder getDataBinder(MarketDetailsDelegate viewDelegate) {
        return new MarketDetailsBinder(viewDelegate);
    }

    int timeIndex = -1;
    private Handler handler = new Handler() {//进行延时跳转
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    updata();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private void updata() {
        if (timeIndex == -1) {
            timeIndex = 0;
        }
        timeIndex++;
        if (timeIndex % updataTime == 0) {
            request(lineBeans.get(lineBeans.size() - 1).timestamp + "");
            timeIndex = 0;
        }
        handler.sendEmptyMessageDelayed(1, 1000);
        viewDelegate.viewHolder.tv_title.setText(exchangeData.getExchange() + "(" + (10 - timeIndex) + ")");
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("").setSubTitle(CommonUtils.getString(R.string.ic_Star) + " " + CommonUtils.getString(R.string.str_add)));
        viewDelegate.getmToolbarTitle().setVisibility(View.GONE);
        getIntentData();
        initCache();
    }

    private void initCache() {
        //缓存数据
        String lastTime;
        String string = CacheUtils.getInstance().getString(CACHE_KLINE + exchangeData.getOnlyKey());
        if (TextUtils.isEmpty(string)) {
            lastTime = "";
            lineBeans = new ArrayList<>();
        } else {
            lineBeans = GsonUtil.getInstance().toList(string, KLineBean.class);
            lastTime = lineBeans.get(lineBeans.size() - 1).timestamp + "";
            getOffLineData(lineBeans);
        }
        request(lastTime);
    }


    private void request(String lastTime) {
        if (exchangeData != null) {
            if (!TextUtils.isEmpty(exchangeData.getOnlyKey())) {
                addRequest(binder.getKlineByOnlyKey(exchangeData.getOnlyKey(), lastTime, this));
            }
        }
    }

    private void getOffLineData(List<KLineBean> lineBeans) {
        if (mData == null) {
            mData = new DataParse();
        }
        if (klineDraw == null) {
            if (lineBeans.size() > 0) {
                mData.parseKLine(lineBeans);
            }
            klineDraw = new KlineDraw();
            klineDraw.setData(this, mData, viewDelegate.viewHolder.combinedchart, viewDelegate.viewHolder.barchart);
        } else {
            klineDraw.updata(lineBeans);
        }
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                List<KLineBean> datas = GsonUtil.getInstance().toList(data, KLineBean.class);
                if (lineBeans.size() > 0) {
                    Iterator<KLineBean> it = datas.iterator();
                    while (it.hasNext()) {
                        KLineBean x = it.next();
                        if (x.timestamp <= lineBeans.get(lineBeans.size() - 1).timestamp) {
                            it.remove();
                        }
                    }
                }
                getOffLineData(datas);
                lineBeans = mData.getKLineDatas();
                if (timeIndex == -1) {
                    handler.sendEmptyMessageDelayed(1, 1000);
                }
                break;
        }
    }

    public static void startAct(Activity activity,
                                ExchangeData exchangeData) {
        Intent intent = new Intent(activity, MarketDetailsActivity.class);
        intent.putExtra("exchangeData", exchangeData);
        activity.startActivity(intent);
    }


    private void getIntentData() {
        Intent intent = getIntent();
        exchangeData = intent.getParcelableExtra("exchangeData");
        viewDelegate.viewHolder.tv_title.setText(exchangeData.getExchange());
    }
}
