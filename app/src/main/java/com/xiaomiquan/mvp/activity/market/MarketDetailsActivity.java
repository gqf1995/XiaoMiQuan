package com.xiaomiquan.mvp.activity.market;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.TimeUtils;
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
import com.xiaomiquan.mvp.databinder.MarketDetailsBinder;
import com.xiaomiquan.mvp.delegate.MarketDetailsDelegate;
import com.xiaomiquan.utils.KlineDaoUtil;
import com.xiaomiquan.utils.UserSet;
import com.xiaomiquan.widget.chart.KCombinedChart;
import com.xiaomiquan.widget.chart.KlineDraw;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.xiaomiquan.base.AppConst.CACHE_CHOOSE;

public class MarketDetailsActivity extends BaseDataBindActivity<MarketDetailsDelegate, MarketDetailsBinder> {
    private DataParse mData;
    KlineDraw klineDraw;
    ExchangeData exchangeData;
    List<KLineBean> lineBeans;//展示在 UI上的数据
    int updataTime = 10;//刷新时间
    String klineValue = "1m";//初始k线时间 1分
    boolean isChange = true;
    boolean isInit = false;//图表是否加载过数据
    boolean isHavaDao = false;
    List<String> timeData;//接口 传递 时间 参数 集合
    List<String> klineTypeData;//显示k线类型
    int uiShowNum = 180;//ui显示多少条
    boolean isUpdataHistory = false;//是否正在更新历史k线
    int updataHistoryNum = 0;//获取历史记录次数
    List<String> userOnlyKeys;
    StringBuffer mylog = new StringBuffer();

    private void setLog(String log) {
        mylog.append(log + "\n");
    }

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
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {//进行延时跳转
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    updata();
                    Log.i("updata", "updata");
                    break;
                case 2:
                    if (!isInit) {
                        isInit = !isInit;
                        loadDao();
                    }
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
        getIntentData();
        //初始化用户之前设置
        initView();
        //删除 无效记录
        KlineDaoUtil.delectHistory(exchangeData.getOnlyKey());
        initToolbar(new ToolbarBuilder().setTitle("").setSubTitle(CommonUtils.getString(R.string.ic_Star) + " " + CommonUtils.getString(R.string.str_add)));
        viewDelegate.getmToolbarTitle().setVisibility(View.GONE);
        viewDelegate.setOnClickListener(this,R.id.lin_global_market,R.id.lin_currency_data,R.id.lin_simulation);
        initCollection();
    }


    //右上角添加初始化
    private void initCollection() {
        String string = CacheUtils.getInstance().getString(CACHE_CHOOSE);
        userOnlyKeys = GsonUtil.getInstance().toList(string, String.class);
        if (userOnlyKeys != null) {
            if (userOnlyKeys.contains(exchangeData.getOnlyKey())) {
                viewDelegate.getmToolbarSubTitle().setTextColor(CommonUtils.getColor(R.color.color_F5A623));
            } else {
                viewDelegate.getmToolbarSubTitle().setTextColor(CommonUtils.getColor(R.color.white));
            }
        } else {
            userOnlyKeys = new ArrayList<>();
            viewDelegate.getmToolbarSubTitle().setTextColor(CommonUtils.getColor(R.color.white));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBack();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private void onBack() {
        onBackPressed();
        overridePendingTransition(R.anim.pop_right_enter_anim, R.anim.pop_right_exit_anim);
    }

    List<String> dataset1;//选择 时间
    List<String> dataset3;//选择 背景样式
    List<String> dataset2;//k线显示类型

    @Override
    protected void onResume() {
        super.onResume();
        //从数据库获取数据 并处理
        if (!isInit) {
            isHavaDao = initCache();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isHavaDao) {
            //进入页面后判断是否数据库有无数据 有则进入后就进行数据处理
            handler.sendEmptyMessage(2);
        } else {

        }
    }

    private void initDao() {
        //从数据库获取数据
        isHavaDao = initCache();
        if (isHavaDao) {
            loadDao();
        }
    }

    String lastTime;

    private void loadDao() {
        //加载从数据库中获取的数据
        if (klineDraw != null) {
            klineDraw.setData(this, mData, viewDelegate.viewHolder.combinedchart, viewDelegate.viewHolder.barchart);
            klineDraw.setOnClick(new KlineDraw.OnClick() {
                @Override
                public void click(int xPosition) {
                    viewDelegate.setDetailsData(xPosition, klineDraw.getmData());
                }
            });
            viewDelegate.setDetailsData(klineDraw.getmData().getKLineDatas().size() - 1, klineDraw.getmData());
        }
    }

    private boolean initCache() {
        //缓存数据
        lastTime = "";
        //从数据库中获取
        List<KLineBean> kLineBeans = KlineDaoUtil.getList(exchangeData.getOnlyKey(), klineValue);
        if (kLineBeans.size() > 0) {
            List<KLineBean> lineBeans = new ArrayList<>();
            if (kLineBeans.size() > uiShowNum) {
                lineBeans = kLineBeans.subList(kLineBeans.size() - uiShowNum, kLineBeans.size());
            } else {
                lineBeans.addAll(kLineBeans);
            }
            if (lineBeans != null) {
                if (lineBeans.size() > 0) {
                    Log.i("KlineDraw", "DataParse");
                    mData = new DataParse();
                    if (lineBeans.size() > 0) {
                        mData.parseKLine(lineBeans);
                    }
                    klineDraw = new KlineDraw();
                }
            }
            viewDelegate.viewHolder.combinedchart.setOnMaxLeftLinsener(new KCombinedChart.OnMaxLeftLinsener() {
                @Override
                public void onMaxLeft() {
                    if (!isUpdataHistory && klineDraw.getmData().getKLineDatas().size() % uiShowNum == 0 && updataHistoryNum < 2) {
                        //更新历史k线
                        isUpdataHistory = true;
                        updataHistoryNum++;
                    }
                }
            });
            lastTime = kLineBeans.get(kLineBeans.size() - 1).timestamp + "";
            isChange = false;
            request(lastTime);
            return true;
        } else {
            request(lastTime);
            return false;
        }
    }


    private void request(String lastTime) {
        setLog("请求开始" + TimeUtils.millis2String(System.currentTimeMillis(), new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS")));
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
        //点击添加自选
        binder.singlesubs(exchangeData.getOnlyKey(), userOnlyKeys.contains(exchangeData.getOnlyKey()) ? "0" : "1", null);
        if (userOnlyKeys.contains(exchangeData.getOnlyKey())) {
            userOnlyKeys.remove(userOnlyKeys.indexOf(exchangeData.getOnlyKey()));
            viewDelegate.getmToolbarSubTitle().setTextColor(CommonUtils.getColor(R.color.white));
        } else {
            userOnlyKeys.add(exchangeData.getOnlyKey());
            viewDelegate.getmToolbarSubTitle().setTextColor(CommonUtils.getColor(R.color.color_F5A623));
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                Log.i("KlineDraw", "onServiceSuccess");
                setLog("请求成功" + TimeUtils.millis2String(System.currentTimeMillis(), new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS")));
                List<KLineBean> newkLineBeans = GsonUtil.getInstance().toList(data, KLineBean.class);
                if (isChange) {
                    //初始化所有数据
                    getOffLineData(newkLineBeans);
                    if (mData != null) {
                        if (mData.getKLineDatas() != null) {
                            KlineDaoUtil.addKlineList(mData.getKLineDatas(), exchangeData.getOnlyKey(), klineValue);
                        }
                    }
                } else {
                    //更新k线信息
                    updataKline(newkLineBeans);
                }
                if (mData != null) {
                    //判断是否有数据 定时器10秒更新 更新显示
                    lineBeans = mData.getKLineDatas();
                    if (timeIndex == -1) {
                        handler.sendEmptyMessageDelayed(1, 1000);
                    }
                    viewDelegate.viewHolder.combinedchart.setNoDataText(CommonUtils.getString(R.string.str_chart_nodata));
                    viewDelegate.viewHolder.barchart.setNoDataText(CommonUtils.getString(R.string.str_chart_nodata));
                } else {
                    viewDelegate.viewHolder.combinedchart.setNoDataText(CommonUtils.getString(R.string.str_kline_nodata));
                    viewDelegate.viewHolder.barchart.setNoDataText(CommonUtils.getString(R.string.str_kline_nodata));
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
                klineDraw = new KlineDraw();
                klineDraw.setData(this, mData, viewDelegate.viewHolder.combinedchart, viewDelegate.viewHolder.barchart);
                klineDraw.setOnClick(new KlineDraw.OnClick() {
                    @Override
                    public void click(int xPosition) {
                        viewDelegate.setDetailsData(xPosition, klineDraw.getmData());
                    }
                });
                viewDelegate.setDetailsData(klineDraw.getmData().getKLineDatas().size() - 1, klineDraw.getmData());
            }
        }
        viewDelegate.viewHolder.combinedchart.setOnMaxLeftLinsener(new KCombinedChart.OnMaxLeftLinsener() {
            @Override
            public void onMaxLeft() {
                if (!isUpdataHistory && klineDraw.getmData().getKLineDatas().size() % uiShowNum == 0 && updataHistoryNum < 2) {
                    //更新历史k线
                    isUpdataHistory = true;
                    updataHistoryNum++;
                }
            }
        });
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
        klineDraw.updata(lineBeans, exchangeData.getOnlyKey() + klineValue);
    }

    //获取 前一列表所传数据 订阅websocket
    private void getIntentData() {
        Intent intent = getIntent();
        exchangeData = intent.getParcelableExtra("exchangeData");
        viewDelegate.initData(exchangeData);
        WebSocketRequest.getInstance().addCallBack(MarketDetailsActivity.this.getClass().getName(), new WebSocketRequest.WebSocketCallBack() {
            @Override
            public void onDataSuccess(String name, String data, String info, int status) {
                if (MarketDetailsActivity.this.getClass().getName().equals(name)) {
                    //推送数据
                    ExchangeData edata = GsonUtil.getInstance().toObj(data, ExchangeData.class);
                    if (!TextUtils.isEmpty(edata.getOnlyKey())) {
                        if (exchangeData.getOnlyKey().equals(edata.getOnlyKey())) {
                            viewDelegate.initData(edata);
                        }
                    }
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
        Intent intent = new Intent(activity, MarketDetailsActivity.class);
        intent.putExtra("exchangeData", exchangeData);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.pop_right_enter_anim, R.anim.pop_right_exit_anim);
    }

    @Override
    protected void onDestroy() {
        if (klineDraw != null) {
            klineDraw.cleanData();
            klineDraw = null;
        }
        CacheUtils.getInstance().put(CACHE_CHOOSE, GsonUtil.getInstance().toJson(userOnlyKeys));
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private void initView() {
        timeData = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_kline_value));
        klineTypeData = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_kline_show_type));
        dataset1 = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_kline));
        dataset2 = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_kline_type));
        dataset3 = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_color));
        //用户选择时间
        String kTime = UserSet.getinstance().getKTime();
        klineValue = kTime;
        //用户选择北京
        viewDelegate.viewHolder.lin_kbg.setBackgroundColor(UserSet.getinstance().getKBgColor());
        viewDelegate.viewHolder.lin_time.setSelectPosition(timeData.indexOf(kTime)).setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, int position, Object item) {
                //时间 改变
                if (klineDraw != null) {
                    if (klineDraw.getmData() != null) {
                        klineValue = timeData.get(position);
                        UserSet.getinstance().setKTime(klineValue);
                        isChange = true;
                        timeIndex = 0;
                        //清空数据
                        if (klineDraw != null) {
                            klineDraw.cleanData();
                        }
                        //请求新数据
                        initDao();
                    }
                }
            }
        }).setDatas(dataset1, new GridLayoutManager(this, 4) {
            public boolean canScrollVertically() {
                return false;
            }
        });
        viewDelegate.viewHolder.lin_indicators
                .setSelectPosition(klineTypeData.indexOf(UserSet.getinstance().getKType()))
                .setDefaultClickLinsener(new DefaultClickLinsener() {
                    @Override
                    public void onClick(View view, int position, Object item) {
                        UserSet.getinstance().setKType(klineTypeData.get(position));
                        if (klineDraw != null) {
                            if (klineDraw.getmData() != null) {
                                viewDelegate.setDetailsData(viewDelegate.selectPosition, klineDraw.getmData());
                                klineDraw.selectType();
                            }
                        }
                    }
                })
                .setDatas(dataset2, null);
        viewDelegate.viewHolder.lin_indicators.setText(UserSet.getinstance().getKType());
        viewDelegate.viewHolder.lin_color
                .setDefaultClickLinsener(new DefaultClickLinsener() {
                    @Override
                    public void onClick(View view, int position, Object item) {
                        //修改背景
                        if (position == 0) {
                            //默认
                            UserSet.getinstance().setKBg(CommonUtils.getColor(R.color.colorPrimary) + "", 0);
                        } else if (position == 1) {
                            //暗色
                            UserSet.getinstance().setKBg(CommonUtils.getColor(R.color.kBg_dark) + "", 1);
                        } else if (position == 2) {
                            //亮色
                            UserSet.getinstance().setKBg(CommonUtils.getColor(R.color.kBg_light) + "", 2);
                        }
                        viewDelegate.viewHolder.lin_kbg.setBackgroundColor(UserSet.getinstance().getKBgColor());
                    }
                })
                .setSelectPosition(UserSet.getinstance().getKBgSelectPosition())
                .setDatas(dataset3, null);
        //初始化用户 默认配置
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.lin_global_market:
                //全球行情
                break;
            case R.id.lin_currency_data:
                //币种资料
                
                break;
            case R.id.lin_simulation:
                //模拟交易
                break;
        }
    }
}
