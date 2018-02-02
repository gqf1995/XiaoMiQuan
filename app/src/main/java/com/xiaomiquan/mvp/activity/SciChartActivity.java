package com.xiaomiquan.mvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.http.WebSocketRequest;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.entity.bean.kline.DataParse;
import com.xiaomiquan.entity.bean.kline.KLineBean;
import com.xiaomiquan.mvp.databinder.SciChartBinder;
import com.xiaomiquan.mvp.delegate.SciChartDelegate;
import com.xiaomiquan.widget.chart.SciChartDraw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SciChartActivity extends BaseDataBindActivity<SciChartDelegate, SciChartBinder> {

    private DataParse mData;
    SciChartDraw klineDraw;
    ExchangeData exchangeData;
    List<KLineBean> lineBeans;//展示在 UI上的数据
    int updataTime = 10;//刷新时间
    String klineValue = "1m";//初始k线时间 1分
    boolean isChange = true;
    List<String> timeData;//接口 传递 时间 参数 集合
    int uiShowNum = 180;//ui显示多少条
    boolean isUpdataHistory = false;//是否正在更新历史k线
    int updataHistoryNum = 0;//获取历史记录次数

    //    涨幅是这一根k线的【收盘/开盘-1】
    //    振幅是【（最高-最低）/开盘】
    @Override
    protected Class<SciChartDelegate> getDelegateClass() {
        return SciChartDelegate.class;
    }

    @Override
    public SciChartBinder getDataBinder(SciChartDelegate viewDelegate) {
        return new SciChartBinder(viewDelegate);
    }

    int timeIndex = -1;
    private Handler handler = new Handler() {//进行延时跳转
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    updata();
                    Log.i("updata", "updata");
                    break;
            }
        }
    };

    //时间更新 每隔1秒更新ui
    private void updata() {
        if (timeIndex == -1) {
            timeIndex = 0;
        }
        timeIndex++;
        lineBeans = mData.getKLineDatas();
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
        initView();
    }

    List<String> dataset1;//选择 时间
    //01-25 17:06:38.717  request 请求
    //01-25 17:06:40.988  请求到数据 json解析
    //01-25 17:06:41.061  整理数据
    //01-25 17:06:41.108  开始绘制
    //01-25 17:06:42.311  绘制结束

    private void initCache() {
        //缓存数据
        String lastTime = "";
        //从数据库中获取
        //lastTime = CacheUtils.getInstance().getString(CACHE_KLINE + exchangeData.getOnlyKey() + klineValue);
        //        List<KLineBean> kLineBeans = DaoManager.getInstance().getDaoSession().getKLineBeanDao()
        //                .queryBuilder()
        //                .where(KLineBeanDao.Properties.Key.eq(exchangeData.getOnlyKey() + klineValue))
        //                .orderAsc(KLineBeanDao.Properties.Timestamp)
        //                .list();
        //        if (kLineBeans.size() > 0) {
        //            getOffLineData(kLineBeans);
        //            lastTime = kLineBeans.get(kLineBeans.size() - 1).timestamp + "";
        //            isChange = false;
        //        }
        request(lastTime);
    }


    private void request(String lastTime) {
        if (exchangeData != null) {
            if (!TextUtils.isEmpty(exchangeData.getOnlyKey())) {
                Log.i("KlineDraw", "request");
                addRequest(binder.getKlineByOnlyKey(exchangeData.getOnlyKey(), klineValue, lastTime, this));
            }
        }
    }


    @Override
    protected void clickRightTv() {
        super.clickRightTv();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                Log.i("KlineDraw", "onServiceSuccess");
                List<KLineBean> newkLineBeans = GsonUtil.getInstance().toList(data, KLineBean.class);
                if (isChange) {
                    //初始化所有数据
                    getOffLineData(newkLineBeans);
                    //                    for (int i = 0; i < mData.getKLineDatas().size(); i++) {
                    //                        mData.getKLineDatas().get(i).key = exchangeData.getOnlyKey() + klineValue;
                    //                        //添加到数据库
                    //                        DaoManager.getInstance().getDaoSession().getKLineBeanDao()
                    //                                .save(mData.getKLineDatas().get(i));
                    //                    }
                } else {
                    //更新k线信息
                    updataKline(newkLineBeans);
                }
                lineBeans = mData.getKLineDatas();
                if (timeIndex == -1) {
                    handler.sendEmptyMessageDelayed(1, 1000);
                }
                isChange = false;

                break;
        }
    }

    //初始化 k线
    private void getOffLineData(List<KLineBean> datas) {
        //筛选前180条数据 添加进ui
        List<KLineBean> lineBeans = new ArrayList<>();
        if (datas.size() > uiShowNum) {
            lineBeans = datas.subList(datas.size() - uiShowNum, datas.size());
        } else {
            lineBeans.addAll(datas);
        }
        if (lineBeans != null) {
            if (lineBeans.size() > 0) {
                Log.i("KlineDraw", "DataParse");
                mData = new DataParse();
                if (lineBeans.size() > 0) {
                    mData.parseKLine(lineBeans);
                }
                klineDraw = new SciChartDraw();
                klineDraw.setData(this, mData, viewDelegate.viewHolder.combinedchart, viewDelegate.viewHolder.barchart);
                //                klineDraw.setOnClick(new KlineDraw.OnClick() {
                //                    @Override
                //                    public void click(int xPosition) {
                //                        viewDelegate.setDetailsData(xPosition, klineDraw.getmData());
                //                    }
                //                });
                //viewDelegate.setDetailsData(klineDraw.getmData().getKLineDatas().size() - 1, klineDraw.getmData());
            }
        }


        //        viewDelegate.viewHolder.combinedchart.setOnMaxLeftLinsener(new KCombinedChart.OnMaxLeftLinsener() {
        //            @Override
        //            public void onMaxLeft() {
        //                if (!isUpdataHistory && klineDraw.getmData().getKLineDatas().size() % uiShowNum == 0 && updataHistoryNum < 2) {
        //                    //更新历史k线
        //                    isUpdataHistory = true;
        //                    updataHistoryNum++;
        //                }
        //            }
        //        });
        Log.i("KlineDraw", "getOffLineData");
        //绘制时间

    }

    //更新历史 k线
    private void updataHistoryKline(List<KLineBean> lineBeans) {

    }

    //更新 k线
    private void updataKline(List<KLineBean> lineBeans) {
        this.lineBeans = mData.getKLineDatas();
        Iterator<KLineBean> it = lineBeans.iterator();
        while (it.hasNext()) {
            KLineBean x = it.next();
            if (x.timestamp < this.lineBeans.get(this.lineBeans.size() - 1).timestamp) {
                it.remove();
            }
        }
        //klineDraw.updata(lineBeans, exchangeData.getOnlyKey() + klineValue);
    }

    //获取 前一列表所传数据 订阅websocket
    private void getIntentData() {
        Intent intent = getIntent();
        exchangeData = intent.getParcelableExtra("exchangeData");
        viewDelegate.initData(exchangeData);
        WebSocketRequest.getInstance().addCallBack(this.getClass().getName(), new WebSocketRequest.WebSocketCallBack() {
            @Override
            public void onDataSuccess(String name, String data, String info, int status) {
                if (this.getClass().getName().equals(name)) {
                    //推送数据
                    exchangeData = GsonUtil.getInstance().toObj(data, ExchangeData.class);
                    viewDelegate.initData(exchangeData);
                }
            }

            @Override
            public void onDataError(String name, String data, String info, int status) {

            }
        });
        sendWebSocket();
    }

    List<String> sendKeys;

    //发送 订阅信息
    private void sendWebSocket() {
        if (exchangeData != null) {
            if (sendKeys == null) {
                sendKeys = new ArrayList<>();
            } else {
                sendKeys.clear();
            }
            sendKeys.add(exchangeData.getOnlyKey());
            WebSocketRequest.getInstance().sendData(sendKeys);
        }
    }

    public static void startAct(Activity activity,
                                ExchangeData exchangeData) {
        Intent intent = new Intent(activity, SciChartActivity.class);
        intent.putExtra("exchangeData", exchangeData);
        activity.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        klineDraw = null;
        super.onDestroy();
    }

    private void initView() {
        dataset1 = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_kline));
        List<String> dataset2 = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_color));
        List<String> dataset3 = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_color));
        viewDelegate.viewHolder.lin_time.setDatas(dataset1, new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        }).setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, int position, Object item) {
                //时间 改变
                klineValue = timeData.get(position);
                isChange = true;
                timeIndex = 0;
                //清空数据
                //klineDraw.cleanData();
                //请求新数据
                initCache();
            }
        });
        viewDelegate.viewHolder.lin_indicators.setDatas(dataset2, null);
        viewDelegate.viewHolder.lin_color.setDatas(dataset3, null);
        timeData = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_kline_value));
    }

}
