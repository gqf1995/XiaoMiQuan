package com.xiaomiquan.mvp.activity.market;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ResultDialogEntity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.http.WebSocketRequest;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ListUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.utils.logger.KLog;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.entity.bean.kline.DataParse;
import com.xiaomiquan.entity.bean.kline.KLineBean;
import com.xiaomiquan.mvp.databinder.MarketDetailsBinder;
import com.xiaomiquan.mvp.delegate.MarketDetailsDelegate;
import com.xiaomiquan.utils.KlineDaoUtil;
import com.xiaomiquan.utils.UserSet;
import com.xiaomiquan.widget.chart.CoupleChartGestureListener;
import com.xiaomiquan.widget.chart.KCombinedChart;
import com.xiaomiquan.widget.chart.MyBottomMarkerView;
import com.xiaomiquan.widget.chart.MyHMarkerView;
import com.xiaomiquan.widget.chart.MyLeftMarkerView;
import com.xiaomiquan.widget.chart.MyUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.xiaomiquan.base.AppConst.CACHE_CHOOSE;

public class MarketDetailsXActivity extends BaseDataBindActivity<MarketDetailsDelegate, MarketDetailsBinder> {
    ExchangeData exchangeData;
    int timeIndex = -1;//刷新进度
    int updataTime = 10;//刷新时间
    List<String> klineTypeData;
    boolean isLoadDao = false;
    List<String> userOnlyKeys;
    List<String> dataset1;//选择 时间
    List<String> dataset3;//选择 背景样式
    List<String> dataset2;//k线显示类型
    List<String> timeData;//接口 传递 时间 参数 集合
    boolean isChange = true;
    String klineValue = "1m";//初始k线时间 1分


    @Override
    protected Class<MarketDetailsDelegate> getDelegateClass() {
        return MarketDetailsDelegate.class;
    }

    @Override
    public MarketDetailsBinder getDataBinder(MarketDetailsDelegate viewDelegate) {
        return new MarketDetailsBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        //初始化用户之前设置
        initChartKline();
        initChartVolume();
        setChartListener();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !ListUtils.isEmpty(newkLineBeans)) {
            //进入页面后判断是否数据库有无数据 有则进入后就进行数据处理
            loadDao();
        }
    }

    private void loadDao() {
        Disposable daoSubscribe;//数据库查询操作
        daoSubscribe = Observable.create(new ObservableOnSubscribe<List<KLineBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<KLineBean>> e) throws Exception {
                //同步请求
                if (!e.isDisposed()) {
                    KlineDaoUtil.delectHistory(exchangeData.getOnlyKey());
                    List<KLineBean> newkLineBeans = KlineDaoUtil.getList(exchangeData.getOnlyKey(), klineValue);
                    if (ListUtils.isEmpty(newkLineBeans)) {
                        if (!e.isDisposed()) {
                            isLoadDao = true;
                            e.onNext(newkLineBeans);
                        }
                    } else {
                        if (!e.isDisposed()) {
                            isLoadDao = false;
                        }
                    }
                    e.onComplete();//onComplete 之后可继下游可继续接受 onNext
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .subscribe(new Consumer<List<KLineBean>>() {//onNext()
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull List<KLineBean> stringResponse) throws Exception {
                        //调用成功 返回json
                        Log.i("subscribe", "subscribe");
                        if (ListUtils.isEmpty(newkLineBeans)) {
                            newkLineBeans.clear();
                        } else {
                            newkLineBeans = new ArrayList<>();
                        }
                        newkLineBeans.addAll(stringResponse);
                    }
                }, new Consumer<Throwable>() {//onError()
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        //调用失败
                        throwable.printStackTrace();
                        if (ListUtils.isEmpty(newkLineBeans)) {
                            newkLineBeans.clear();
                        }
                    }
                }, new Action() {//onCompleted()
                    @Override
                    public void run() throws Exception {
                        loadDatas();
                    }
                });
        addRequest(daoSubscribe);
    }

    private void loadDatas() {
        if (ListUtils.isEmpty(newkLineBeans)) {
            initAllDatas();
        } else {
            addRequest(binder.getKlineByOnlyKey(exchangeData.getOnlyKey(), klineValue, "", 0x123, this));
        }
    }

    protected KCombinedChart mChartKline;
    protected KCombinedChart mChartVolume;
    //X轴标签的类
    protected XAxis xAxisKline, xAxisVolume;
    //Y轴左侧的线
    protected YAxis axisLeftKline, axisLeftVolume;
    //Y轴右侧的线
    protected YAxis axisRightKline, axisRightVolume;

    //解析数据
    private DataParse mData;
    //K线图数据
    private ArrayList<KLineBean> kLineDatas;
    boolean isRefresh = true;
    //private DataParse mCacheData;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {//进行延时跳转
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:
                    //websocket推送更新
                    if (viewDelegate != null) {
                        viewDelegate.initData((ExchangeData) msg.obj);
                    }
                    break;
                case 0x124:
                    //更新
                    updata();
                    Log.i("updata", "updata");
                    break;
                case 0x125:
                    loadDatas();
                    break;
                case 0x126:
                    loadInMP();
                    break;
            }
        }
    };

    private void loadInMP() {
        mChartKline.setAutoScaleMinMaxEnabled(true);
        mChartVolume.setAutoScaleMinMaxEnabled(true);
        mChartKline.notifyDataSetChanged();
        mChartVolume.notifyDataSetChanged();
        mChartKline.invalidate();
        mChartVolume.invalidate();
        setOffset();
        initSelectDataShow();
        if (!isLoadDao) {
            //网络加载的数据  添加数据库
            KlineDaoUtil.addKlineList(mData.getKLineDatas(), exchangeData.getOnlyKey(), klineValue);
        } else {
            addRequest(binder.getKlineByOnlyKey(exchangeData.getOnlyKey(), klineValue, newkLineBeans.get(newkLineBeans.size() - 1).timestamp + "", 0x124, MarketDetailsXActivity.this));
        }
        isRefresh = false;
    }

    //获取 前一列表所传数据 订阅websocket
    private void getIntentData() {
        Intent intent = getIntent();
        exchangeData = intent.getParcelableExtra("exchangeData");
        viewDelegate.initData(exchangeData);
        mChartKline = viewDelegate.viewHolder.combinedchart;
        mChartVolume = viewDelegate.viewHolder.barchart;
        klineTypeData = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_kline_show_type));
        WebSocketRequest.getInstance().addCallBack(MarketDetailsXActivity.this.getClass().getName(), new WebSocketRequest.WebSocketCallBack() {
            @Override
            public void onDataSuccess(String name, String data, String info, int status) {
                if (MarketDetailsXActivity.this.getClass().getName().equals(name)) {
                    //推送数据
                    ExchangeData edata = GsonUtil.getInstance().toObj(data, ExchangeData.class);
                    if (!TextUtils.isEmpty(edata.getOnlyKey())) {
                        if (exchangeData.getOnlyKey().equals(edata.getOnlyKey())) {
                            Message message = new Message();
                            message.what = 0x123;
                            message.obj = edata;
                            handler.sendMessage(message);
                        }
                    }
                }
            }

            @Override
            public void onDataError(String name, String data, String info, int status) {

            }
        });
        initToolbar(new ToolbarBuilder().setTitle("").setSubTitle("10s").setBackTxt(exchangeData.getExchange() + "  " + exchangeData.getSymbol() + "/" + exchangeData.getUnit()));//.setSubTitle(CommonUtils.getString(R.string.ic_Star) + " " + CommonUtils.getString(R.string.str_add)));
        viewDelegate.getmToolbarBackTxt().setText(Html.fromHtml("<big>" + exchangeData.getExchange() + "</big>  <small>" + exchangeData.getSymbol() + "/" + exchangeData.getUnit() + "<small/>"));
        viewDelegate.getmToolbarBack().setText(CommonUtils.getString(R.string.ic_Left));
        viewDelegate.getmToolbarBack().setVisibility(View.VISIBLE);
        viewDelegate.getmToolbarTitle().setVisibility(View.GONE);
        initCollection();
        viewDelegate.setOnClickListener(this, R.id.lin_add, R.id.lin_global_market, R.id.lin_currency_data, R.id.lin_simulation);
        initView();
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
        overridePendingTransition(R.anim.pop_no, R.anim.pop_right_exit_anim);
    }

    List<KLineBean> newkLineBeans;

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        boolean isUpdata = true;
        switch (requestCode) {
            case 0x123:
                //全部k线
                Log.i("KlineDraw", "onServiceSuccess");
                newkLineBeans = GsonUtil.getInstance().toList(data, KLineBean.class);
                if (!ListUtils.isEmpty(newkLineBeans)) {
                    viewDelegate.noKlineView();
                    isUpdata = false;
                } else {
                    initAllDatas();
                }
                break;
            case 0x124:
                //更新k线
                List<KLineBean> newkLineBeans = GsonUtil.getInstance().toList(data, KLineBean.class);
                if (!ListUtils.isEmpty(newkLineBeans)) {
                    viewDelegate.noKlineView();
                    isUpdata = false;
                } else {
                    updataKline(newkLineBeans);
                }
                break;
        }
        //更新提示
        if (timeIndex == -1 && isUpdata) {
            handler.sendEmptyMessageDelayed(0x124, 1000);
        }
    }

    //更新 k线
    private void updataKline(List<KLineBean> lineBeans) {
        if (mData != null) {
            if (mData.getKLineDatas() != null) {
                Iterator<KLineBean> it = lineBeans.iterator();
                while (it.hasNext()) {
                    KLineBean x = it.next();
                    if (x.timestamp < mData.getKLineDatas().get(mData.getKLineDatas().size() - 1).timestamp) {
                        it.remove();
                    }
                }
                updata(lineBeans, exchangeData.getOnlyKey() + klineValue);
            }
        }
    }

    //时间更新 每隔1秒更新ui
    private void updata() {
        if (timeIndex == -1) {
            timeIndex = 0;
        }
        timeIndex++;
        List<KLineBean> lineBeans = mData.getKLineDatas();
        if (timeIndex % updataTime == 0) {
            addRequest(binder.getKlineByOnlyKey(exchangeData.getOnlyKey(), klineValue, lineBeans.get(lineBeans.size() - 1).timestamp + "", 0x124, this));
            timeIndex = 0;
        } else if (timeIndex == 1) {
            sendWebSocket();
        }
        handler.removeCallbacksAndMessages(null);//清空消息方便gc回收
        handler.sendEmptyMessageDelayed(0x124, 1000);
        viewDelegate.getmToolbarSubTitle().setText((10 - timeIndex) + "s");
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

    private void initAllDatas() {
        initCharData();
        setKLineByChart(mChartKline);
        setVolumeByChart(mChartVolume);
        mChartKline.moveViewToX(kLineDatas.size() - 1);
        mChartVolume.moveViewToX(kLineDatas.size() - 1);
        handler.sendEmptyMessageDelayed(0x126, 300);
    }

    /**
     * 初始化公共数据
     */
    private void initCharData() {
        mData = new DataParse();
        mData.parseKLine(newkLineBeans);
        kLineDatas = mData.getKLineDatas();
        mData.initLineDatas(kLineDatas);
        setMarkerViewButtom(mData, mChartVolume);
        setMarkerView(mData, mChartKline);
    }

    private void setMarkerViewButtom(DataParse mData, KCombinedChart combinedChart) {
        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(MarketDetailsXActivity.this, R.layout.mymarkerview);
        MyHMarkerView hMarkerView = new MyHMarkerView(MarketDetailsXActivity.this, R.layout.mymarkerview_line);
        MyBottomMarkerView bottomMarkerView = new MyBottomMarkerView(MarketDetailsXActivity.this, R.layout.mymarkerview);
        combinedChart.setMarker(leftMarkerView, bottomMarkerView, hMarkerView, mData);
    }

    private void setMarkerView(DataParse mData, KCombinedChart combinedChart) {
        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(MarketDetailsXActivity.this, R.layout.mymarkerview_price);
        MyHMarkerView hMarkerView = new MyHMarkerView(MarketDetailsXActivity.this, R.layout.mymarkerview_line);
        combinedChart.setMarker(leftMarkerView, hMarkerView, mData);
    }

    private void setKLineByChart(KCombinedChart combinedChart) {
        CandleDataSet set = new CandleDataSet(mData.getCandleEntries(), "");
        set.setDrawHorizontalHighlightIndicator(false);
        set.setHighlightEnabled(true);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setShadowWidth(1f);
        set.setValueTextSize(10f);
        set.setDecreasingColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));//设置开盘价高于收盘价的颜色
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setIncreasingColor(CommonUtils.getColor(UserSet.getinstance().getRiseColor()));//设置开盘价地狱收盘价的颜色
        set.setIncreasingPaintStyle(Paint.Style.FILL);
        set.setNeutralColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));//设置开盘价等于收盘价的颜色
        set.setShadowColorSameAsCandle(true);
        set.setHighlightLineWidth(1f);
        set.setHighLightColor(getResources().getColor(R.color.color_e5e5e5));
        set.setDrawValues(true);
        set.setValueTextColor(getResources().getColor(R.color.marker_text_bg));
        CandleData candleData = new CandleData(mData.getXVals(), set);

        mData.initKLineMA(kLineDatas);
        mData.initBOLL(kLineDatas);
        mData.initEXPMA(kLineDatas);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        /******此处修复如果显示的点的个数达不到MA均线的位置所有的点都从0开始计算最小值的问题******************************/
        LineDataSet lineDataSetMA7 = MyUtils.setMaLine(7, mData.getXVals(), mData.getMa7DataL());
        LineDataSet lineDataSetMA30 = MyUtils.setMaLine(30, mData.getXVals(), mData.getMa30DataL());
        LineDataSet lineDataSetEMA7 = setKDJMaLine(3, mData.getXVals(), (ArrayList<Entry>) mData.getExpmaData7());
        LineDataSet lineDataSetEMA30 = setKDJMaLine(1, mData.getXVals(), (ArrayList<Entry>) mData.getExpmaData30());
        //        LineDataSet lineDataSetUB = setKDJMaLine(1, mData.getXVals(), (ArrayList<Entry>) mData.getBollDataUP());//UP 上轨 UB
        //        LineDataSet lineDataSetBOLL = setKDJMaLine(3, mData.getXVals(), (ArrayList<Entry>) mData.getBollDataMB());//MB 中轨 BOLL
        //        LineDataSet lineDataSetLB = setKDJMaLine(0, mData.getXVals(), (ArrayList<Entry>) mData.getBollDataDN());//DN 下轨 LB

        boolean isMA = false;
        boolean isEMA = false;
        boolean isBOLL = false;
        if (klineTypeData.indexOf(UserSet.getinstance().getKType()) == 0) {
            //MA
            isMA = true;
        } else if (klineTypeData.indexOf(UserSet.getinstance().getKType()) == 1) {
            //EMA
            isEMA = true;
        }
        //        else if (klineTypeData.indexOf(UserSet.getinstance().getKType()) == 2) {
        //            //BOLL
        //            isBOLL = true;
        //        }
        else if (klineTypeData.indexOf(UserSet.getinstance().getKType()) == 2) {
            //均线
        }
        lineDataSetMA7.setVisible(isMA);
        lineDataSetMA30.setVisible(isMA);
        lineDataSetEMA7.setVisible(isEMA);
        lineDataSetEMA30.setVisible(isEMA);
        //        lineDataSetUB.setVisible(isBOLL);
        //        lineDataSetBOLL.setVisible(isBOLL);
        //        lineDataSetLB.setVisible(isBOLL);
        sets.add(lineDataSetMA7);
        sets.add(lineDataSetMA30);
        sets.add(lineDataSetEMA7);
        sets.add(lineDataSetEMA30);
        //        sets.add(lineDataSetUB);
        //        sets.add(lineDataSetBOLL);
        //        sets.add(lineDataSetLB);

        LineData lineData = new LineData(mData.getXVals(), sets);

        CombinedData combinedData = new CombinedData(mData.getXVals());
        combinedData.setData(candleData);
        combinedData.setData(lineData);

        combinedChart.setData(combinedData);
        combinedChart.setDrawHighlightArrow(true);

        setHandler(combinedChart);
    }

    private void setVolumeByChart(KCombinedChart combinedChart) {
        BarDataSet barSet = new BarDataSet(mData.getBarEntries(), "成交量");
        barSet.setBarSpacePercent(20); //bar空隙
        barSet.setHighlightEnabled(true);
        barSet.setHighLightAlpha(255);
        barSet.setHighLightColor(getResources().getColor(R.color.marker_line_bg));
        barSet.setDrawValues(false);


        List<Integer> list = new ArrayList<>();
        list.add(CommonUtils.getColor(UserSet.getinstance().getDropColor()));
        for (int i = 1; i < mData.getBarEntries().size(); i++) {
            if (mData.getBarEntries().get(i).getVal() > mData.getBarEntries().get(i - 1).getVal()) {
                list.add(CommonUtils.getColor(UserSet.getinstance().getRiseColor()));
            } else {
                list.add(CommonUtils.getColor(UserSet.getinstance().getDropColor()));
            }
        }
        barSet.setColors(list);

        BarData barData = new BarData(mData.getXVals(), barSet);

        mData.initVlumeMA(kLineDatas);
        ArrayList<ILineDataSet> sets = new ArrayList<>();

        /******此处修复如果显示的点的个数达不到MA均线的位置所有的点都从0开始计算最小值的问题******************************/
        sets.add(MyUtils.setMaLine(7, mData.getXVals(), mData.getMa7DataV()));
        sets.add(MyUtils.setMaLine(10, mData.getXVals(), mData.getMa10DataV()));

        LineData lineData = new LineData(mData.getXVals(), sets);

        CombinedData combinedData = new CombinedData(mData.getXVals());
        combinedData.setData(barData);
        combinedData.setData(lineData);

        combinedChart.setData(combinedData);
        combinedChart.setDrawHighlightArrow(true);

        setHandler(combinedChart);
    }

    private void setHandler(KCombinedChart combinedChart) {
        final ViewPortHandler viewPortHandlerBar = combinedChart.getViewPortHandler();
        viewPortHandlerBar.setMaximumScaleX(MyUtils.culcMaxscale(mData.getXVals().size()));
        Matrix touchmatrix = viewPortHandlerBar.getMatrixTouch();
        final float xscale = 20 * UserSet.getinstance().getKlineScale();
        touchmatrix.postScale(xscale, 1f);
    }

    @NonNull
    private LineDataSet setMaLine(int ma, ArrayList<String> xVals, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + ma);
        if (ma == 5) {
            lineDataSetMa.setHighlightEnabled(true);
            lineDataSetMa.setDrawHorizontalHighlightIndicator(false);
            lineDataSetMa.setHighLightColor(getResources().getColor(R.color.marker_line_bg));
        } else {/*此处必须得写*/
            lineDataSetMa.setHighlightEnabled(false);
        }
        lineDataSetMa.setDrawValues(false);
        if (ma == 5) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma5));
        } else if (ma == 10) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma10));
        } else if (ma == 20) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma20));
        } else {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma30));
        }
        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);

        lineDataSetMa.setHighlightEnabled(false);
        return lineDataSetMa;
    }

    @NonNull
    private LineDataSet setKDJMaLine(int type, ArrayList<String> xVals, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + type);
        lineDataSetMa.setHighlightEnabled(false);
        lineDataSetMa.setDrawValues(false);

        //DEA
        if (type == 0) {
            lineDataSetMa.setColor(CommonUtils.getColor(R.color.ma5));
        } else if (type == 1) {
            lineDataSetMa.setColor(CommonUtils.getColor(R.color.ma10));
        } else if (type == 2) {
            lineDataSetMa.setColor(CommonUtils.getColor(R.color.ma20));
        } else {
            lineDataSetMa.setColor(CommonUtils.getColor(R.color.ma30));
        }

        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);

        return lineDataSetMa;
    }

    /**
     * 初始化上面的chart公共属性
     */
    private void initChartKline() {
        mChartKline.setScaleEnabled(true);//启用图表缩放事件
        mChartKline.setDrawBorders(false);//是否绘制边线
        mChartKline.setBorderWidth(1);//边线宽度，单位dp
        mChartKline.setDragEnabled(true);//启用图表拖拽事件
        mChartKline.setScaleYEnabled(false);//启用Y轴上的缩放
        mChartKline.setBorderColor(getResources().getColor(R.color.border_color));//边线颜色
        mChartKline.setDescription("");//右下角对图表的描述信息
        mChartKline.setMinOffset(0f);
        mChartKline.setExtraOffsets(0f, 0f, 1f, 0f);

        Legend lineChartLegend = mChartKline.getLegend();
        lineChartLegend.setEnabled(false);//是否绘制 Legend 图例
        lineChartLegend.setForm(Legend.LegendForm.CIRCLE);

        //bar x y轴
        xAxisKline = mChartKline.getXAxis();
        xAxisKline.setEnabled(false);
        //        xAxisKline.setDrawLabels(false); //是否显示X坐标轴上的刻度，默认是true
        //        xAxisKline.setDrawGridLines(false);//是否显示X坐标轴上的刻度竖线，默认是true
        //        xAxisKline.setDrawAxisLine(false); //是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        //        xAxisKline.enableGridDashedLine(10f, 10f, 0f);//虚线表示X轴上的刻度竖线(float lineLength, float spaceLength, float phase)三个参数，1.线长，2.虚线间距，3.虚线开始坐标
        //        xAxisKline.setTextColor(getResources().getColor(R.color.color_font2));//设置字的颜色
        //        xAxisKline.setPosition(XAxis.XAxisPosition.BOTTOM);//设置值显示在什么位置
        //        xAxisKline.setAvoidFirstLastClipping(true);//设置首尾的值是否自动调整，避免被遮挡

        axisLeftKline = mChartKline.getAxisRight();
        axisLeftKline.setDrawGridLines(false);
        axisLeftKline.setDrawAxisLine(false);
        axisLeftKline.setDrawZeroLine(false);
        axisLeftKline.setDrawLabels(true);
        axisLeftKline.enableGridDashedLine(10f, 10f, 0f);
        axisLeftKline.setTextColor(getResources().getColor(R.color.color_font2));
        //        axisLeftKline.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftKline.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeftKline.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisLeftKline.setSpaceTop(10);//距离顶部留白

        axisRightKline = mChartKline.getAxisLeft();
        axisRightKline.setDrawLabels(false);
        axisRightKline.setDrawGridLines(false);
        axisRightKline.setDrawAxisLine(false);
        axisRightKline.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布

        mChartKline.setDragDecelerationEnabled(true);
        mChartKline.setDragDecelerationFrictionCoef(0.2f);

    }

    /**
     * 初始化下面的chart公共属性
     */
    private void initChartVolume() {
        mChartVolume.setDrawBorders(false);  //边框是否显示
        mChartVolume.setBorderWidth(1);//边框的宽度，float类型，dp单位
        mChartVolume.setBorderColor(getResources().getColor(R.color.border_color));//边框颜色
        mChartVolume.setDescription(""); //图表默认右下方的描述，参数是String对象
        mChartVolume.setDragEnabled(true);// 是否可以拖拽
        mChartVolume.setScaleYEnabled(false); //是否可以缩放 仅y轴
        mChartVolume.setMinOffset(3f);
        mChartVolume.setExtraOffsets(0f, 0f, 0f, 10f);

        Legend combinedchartLegend = mChartVolume.getLegend(); // 设置比例图标示，就是那个一组y的value的
        combinedchartLegend.setEnabled(false);//是否绘制比例图

        //bar x y轴
        xAxisVolume = mChartVolume.getXAxis();
        xAxisVolume.setDrawGridLines(false);//是否显示X坐标轴上的刻度竖线，默认是true
        xAxisVolume.setDrawAxisLine(false); //是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        xAxisVolume.setDrawLabels(true); //是否显示X坐标轴上的刻度，默认是true
        xAxisVolume.setTextColor(getResources().getColor(R.color.color_font2));//设置字的颜色
        xAxisVolume.setPosition(XAxis.XAxisPosition.BOTTOM);//设置值显示在什么位置
        xAxisVolume.setAvoidFirstLastClipping(true);//设置首尾的值是否自动调整，避免被遮挡


        axisLeftVolume = mChartVolume.getAxisRight();
        axisLeftVolume.setAxisMinValue(0);//设置Y轴坐标最小为多少
        //        axisLeftVolume.setShowOnlyMinMax(true);//设置Y轴坐标最小为多少
        axisLeftVolume.setDrawGridLines(false);
        axisLeftVolume.setDrawAxisLine(false);
        //        axisLeftVolume.setShowOnlyMinMax(true);
        axisLeftVolume.setDrawLabels(true);
        axisLeftVolume.enableGridDashedLine(10f, 10f, 0f);
        axisLeftVolume.setTextColor(getResources().getColor(R.color.color_font2));
        //        axisLeftVolume.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftVolume.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeftVolume.setLabelCount(1, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        axisLeftVolume.setSpaceTop(0);//距离顶部留白
        //        axisLeftVolume.setSpaceBottom(0);//距离顶部留白

        axisRightVolume = mChartVolume.getAxisLeft();
        axisRightVolume.setDrawLabels(false);
        axisRightVolume.setDrawGridLines(false);
        axisRightVolume.setDrawAxisLine(false);

        mChartVolume.setDragDecelerationEnabled(true);
        mChartVolume.setDragDecelerationFrictionCoef(0.2f);

    }

    private void setChartListener() {
        // 将K线控的滑动事件传递给交易量控件
        mChartKline.setOnChartGestureListener(new CoupleChartGestureListener(mChartKline, new Chart[]{mChartVolume}));
        // 将交易量控件的滑动事件传递给K线控件
        mChartVolume.setOnChartGestureListener(new CoupleChartGestureListener(mChartVolume, new Chart[]{mChartKline}));

        mChartKline.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                mChartVolume.highlightValues(new Highlight[]{h});
            }

            @Override
            public void onNothingSelected() {
                mChartVolume.highlightValue(null);
            }
        });

        mChartVolume.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                mChartKline.highlightValues(new Highlight[]{h});

            }

            @Override
            public void onNothingSelected() {
                mChartKline.highlightValue(null);
            }
        });

    }

    /*设置量表对齐*/
    private void setOffset() {
        float lineLeft = mChartKline.getViewPortHandler().offsetLeft();
        float kbLeft = mChartVolume.getViewPortHandler().offsetLeft();
        float lineRight = mChartKline.getViewPortHandler().offsetRight();
        float kbRight = mChartVolume.getViewPortHandler().offsetRight();
        float kbBottom = mChartVolume.getViewPortHandler().offsetBottom();

        float offsetLeft, offsetRight;
        float transLeft = 0, transRight = 0;
 /*注：setExtraLeft...函数是针对图表相对位置计算，比如A表offLeftA=20dp,B表offLeftB=30dp,则A.setExtraLeftOffset(10),并不是30，还有注意单位转换*/
        if (kbLeft < lineLeft) {
           /* offsetLeft = Utils.convertPixelsToDp(lineLeft - barLeft);
            barChart.setExtraLeftOffset(offsetLeft);*/
            transLeft = lineLeft;
        } else {
            offsetLeft = Utils.convertPixelsToDp(kbLeft - lineLeft);
            //mChartKline.setExtraLeftOffset(offsetLeft);
            //mChartVolume.setExtraLeftOffset(offsetLeft);
            transLeft = kbLeft;
        }
  /*注：setExtraRight...函数是针对图表绝对位置计算，比如A表offRightA=20dp,B表offRightB=30dp,则A.setExtraLeftOffset(30),并不是10，还有注意单位转换*/
        if (kbRight < lineRight) {
          /*  offsetRight = Utils.convertPixelsToDp(lineRight);
            barChart.setExtraRightOffset(offsetRight);*/
            transRight = lineRight;
        } else {
            offsetRight = Utils.convertPixelsToDp(kbRight - lineRight);
            //mChartKline.setExtraRightOffset(offsetRight);
            //mChartVolume.setExtraRightOffset(offsetRight);
            transRight = kbRight;
        }
        mChartKline.setViewPortOffsets(transLeft, 15, transRight, mChartKline.getViewPortHandler().offsetBottom());
        mChartVolume.setViewPortOffsets(transLeft, 15, transRight, mChartVolume.getViewPortHandler().offsetBottom());

    }

    private void initSelectDataShow() {
        mChartKline.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, int position, Object item) {
                viewDelegate.setDetailsData(position, mData);
            }
        });
        mChartVolume.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, int position, Object item) {
                viewDelegate.setDetailsData(position, mData);
            }
        });
        viewDelegate.setDetailsData(mData.getKLineDatas().size() - 1, mData);
    }


    public static void startAct(Activity activity,
                                ExchangeData exchangeData) {
        Intent intent = new Intent(activity, MarketDetailsXActivity.class);
        intent.putExtra("exchangeData", exchangeData);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.pop_right_enter_anim, R.anim.pop_no);
    }

    public void cleanData() {
        if (mData != null) {
            if (mData.getKLineDatas() != null) {
                if (mData.getKLineDatas().size() > 0) {
                    //每次清除前记录缩放级别
                    float v = mChartKline.getViewPortHandler().getScaleX() / 20;
                    UserSet.getinstance().setKlineScale(v);
                    //清除
                    mChartKline.clear();
                    mChartVolume.clear();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        cleanData();
        CacheUtils.getInstance().put(CACHE_CHOOSE, GsonUtil.getInstance().toJson(userOnlyKeys));
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }


    public void updata(List<KLineBean> lineBeans, String key) {
        for (int i = 0; i < lineBeans.size(); i++) {
            lineBeans.get(i).date = TimeUtils.millis2String(lineBeans.get(i).timestamp * 1000, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        }

        if (lineBeans.size() > 0) {
            //mData.initLineDatas(lineBeans);
            if (mData == null) {
                return;
            }
            if (mData.getKLineDatas() == null) {
                return;
            }
            kLineDatas = mData.getKLineDatas();

            //更新 当前 到数第一根 k线
            KLineBean kLineBean = new KLineBean();
            kLineBean.date = kLineDatas.get(kLineDatas.size() - 1).date;
            kLineBean.low = kLineDatas.get(kLineDatas.size() - 1).low;
            kLineBean.high = kLineDatas.get(kLineDatas.size() - 1).high;
            kLineBean.open = kLineDatas.get(kLineDatas.size() - 1).open;

            kLineBean.close = lineBeans.get(0).close;
            kLineBean.volume = lineBeans.get(0).volume;
            kLineBean.timestamp = lineBeans.get(0).timestamp;


            if (kLineBean.high.compareTo(lineBeans.get(0).high) == -1) {
                kLineBean.high = lineBeans.get(0).high;
            }
            if (kLineBean.low.compareTo(lineBeans.get(0).low) == 1) {
                kLineBean.low = lineBeans.get(0).low;
            }

            kLineDatas.remove(kLineDatas.size() - 1);
            kLineDatas.add(kLineBean);

            mData.initKLineMA(kLineDatas);
            mData.initVlumeMA(kLineDatas);

            updatLastKline(kLineBean);
            updatLastVolume(kLineBean);

            KlineDaoUtil.updataKline(kLineBean, key);

            if (lineBeans.size() > 1) {
                for (int i = 1; i < lineBeans.size(); i++) {
                    kLineDatas.add(lineBeans.get(i));
                    mData.getXVals().add(lineBeans.get(i).date);
                }

                mData.initKLineMA(kLineDatas);
                mData.initVlumeMA(kLineDatas);
                mData.initBOLL(kLineDatas);
                mData.initEXPMA(kLineDatas);

                for (int i = 1; i < lineBeans.size(); i++) {
                    addVolumeData(lineBeans.size() - i);
                    addKlineData(lineBeans.size() - i);

                    KLineBean kLineBean1 = lineBeans.get(i);
                    KlineDaoUtil.addKline(kLineBean1, key);
                }
            }

            setOffset();

            mChartKline.setAutoScaleMinMaxEnabled(true);
            mChartVolume.setAutoScaleMinMaxEnabled(true);

            mChartKline.notifyDataSetChanged();
            mChartVolume.notifyDataSetChanged();

            mChartKline.invalidate();
            mChartVolume.invalidate();


        }

    }

    private void updatLastKline(KLineBean kLineBean) {
        CandleData candleData = mChartKline.getCandleData();
        LineData lineData = mChartKline.getLineData();

        if (candleData != null) {
            int indexLast = getLastDataSetIndex(candleData);
            CandleDataSet lastSet = (CandleDataSet) candleData.getDataSetByIndex(indexLast);

            if (lastSet == null) {
                lastSet = createCandleDataSet();
                candleData.addDataSet(lastSet);
            }
            //count = lastSet.getEntryCount();
            // 位最后一个DataSet添加entry
            KLog.i("chart", kLineBean.toString());
            CandleEntry candleEntry = new CandleEntry(lastSet.getYVals().size() - 1, kLineBean.high.floatValue(), kLineBean.low.floatValue(), kLineBean.open.floatValue(), kLineBean.close.floatValue());

            lastSet.getYVals().remove(lastSet.getYVals().size() - 1);
            lastSet.getYVals().add(candleEntry);

        }


    }

    private void updatLastVolume(KLineBean kLineBean) {
        BarData barData = mChartVolume.getBarData();
        LineData lineData = mChartVolume.getLineData();
        if (barData != null) {
            int indexLast = getLastDataSetIndex(barData);
            BarDataSet lastSet = (BarDataSet) barData.getDataSetByIndex(indexLast);

            if (lastSet == null) {
                lastSet = createBarDataSet();
                barData.addDataSet(lastSet);
            }

            lastSet.getYVals().remove(lastSet.getYVals().size() - 1);
            lastSet.getYVals().add(new BarEntry(kLineBean.volume.floatValue(), lastSet.getYVals().size()));

        }
    }

    private void addKlineData(int index) {
        CandleData candleData = mChartKline.getCandleData();
        LineData lineData = mChartKline.getLineData();

        int count = 0;
        int i = kLineDatas.size() - index;
        KLineBean kLineBean = kLineDatas.get(i);

        String xVals = kLineDatas.get(kLineDatas.size() - index).date;//mData.getXVals().get(mData.getXVals().size() - index);
        if (candleData != null) {
            int indexLast = getLastDataSetIndex(candleData);
            CandleDataSet lastSet = (CandleDataSet) candleData.getDataSetByIndex(indexLast);

            if (lastSet == null) {
                lastSet = createCandleDataSet();
                candleData.addDataSet(lastSet);
            }
            //count = lastSet.getEntryCount();
            count = i;

            // 位最后一个DataSet添加entry

            KLog.i("chart", kLineBean.toString());

            CandleEntry candleEntry = new CandleEntry(candleData.getYValCount(), kLineBean.high.floatValue(), kLineBean.low.floatValue(), kLineBean.open.floatValue(), kLineBean.close.floatValue());
            //combinedData.addEntry(candleEntry, indexLast);
            candleData.addEntry(candleEntry, indexLast);

            //candleData.addXValue(kLineBean.getDate());
            //candleData.addXValue(xVals);
            //mChartKline.setData();
            //combinedData.addEntry(mData.getCandleEntries().get(mData.getCandleEntries().size() - index - 1), indexLast);

        }

        if (lineData != null) {
            LineDataSet lineDataSetMA7 = (LineDataSet) lineData.getDataSetByIndex(0);//五日均线;
            LineDataSet lineDataSetMA30 = (LineDataSet) lineData.getDataSetByIndex(1);//十日均线;
            LineDataSet lineDataSetEMA7 = (LineDataSet) lineData.getDataSetByIndex(2);
            LineDataSet lineDataSetEMA30 = (LineDataSet) lineData.getDataSetByIndex(3);
            //            LineDataSet lineDataSetUB = (LineDataSet) lineData.getDataSetByIndex(4);//UP 上轨 UB
            //            LineDataSet lineDataSetBOLL = (LineDataSet) lineData.getDataSetByIndex(5);//MB 中轨 BOLL
            //            LineDataSet lineDataSetLB = (LineDataSet) lineData.getDataSetByIndex(6);//DN 下轨 LB

            //lineData.addXValue(kLineBean.getDate());

            if (lineDataSetMA7 != null) {
                //mData.getMa5DataL().add(new Entry(KMAEntity.getLastMA(kLineDatas, 5), count));
                lineData.addEntry(mData.getMa7DataL().get(mData.getMa7DataL().size() - index), 0);
            }
            if (lineDataSetMA30 != null) {
                //mData.getMa30DataL().add(new Entry(KMAEntity.getLastMA(kLineDatas, 30), count));
                lineData.addEntry(mData.getMa30DataL().get(mData.getMa30DataL().size() - index), 3);
            }
            if (lineDataSetEMA7 != null) {
                //mData.getMa30DataL().add(new Entry(KMAEntity.getLastMA(kLineDatas, 30), count));
                lineData.addEntry(mData.getExpmaData7().get(mData.getExpmaData7().size() - index), 3);
            }
            if (lineDataSetEMA30 != null) {
                //mData.getMa30DataL().add(new Entry(KMAEntity.getLastMA(kLineDatas, 30), count));
                lineData.addEntry(mData.getExpmaData30().get(mData.getExpmaData30().size() - index), 3);
            }
            //            if (lineDataSetUB != null) {
            //                //mData.getMa30DataL().add(new Entry(KMAEntity.getLastMA(kLineDatas, 30), count));
            //                lineData.addEntry(mData.getBollDataUP().get(mData.getBollDataUP().size() - index), 3);
            //            }
            //            if (lineDataSetBOLL != null) {
            //                //mData.getMa30DataL().add(new Entry(KMAEntity.getLastMA(kLineDatas, 30), count));
            //                lineData.addEntry(mData.getBollDataMB().get(mData.getBollDataMB().size() - index), 3);
            //            }
            //            if (lineDataSetLB != null) {
            //                //mData.getMa30DataL().add(new Entry(KMAEntity.getLastMA(kLineDatas, 30), count));
            //                lineData.addEntry(mData.getBollDataDN().get(mData.getBollDataDN().size() - index), 3);
            //            }
        }
    }

    private void addVolumeData(int index) {
        BarData barData = mChartVolume.getBarData();
        LineData lineData = mChartVolume.getLineData();

        int count = 0;

        int i = kLineDatas.size() - index;
        KLineBean kLineBean = kLineDatas.get(i);

        BarEntry barEntry = new BarEntry(kLineBean.volume.floatValue(), barData.getYValCount());

        String xVals = kLineDatas.get(kLineDatas.size() - index).date;//mData.getXVals().get(mData.getXVals().size() - index);
        if (barData != null) {
            int indexLast = getLastDataSetIndex(barData);
            BarDataSet lastSet = (BarDataSet) barData.getDataSetByIndex(indexLast);

            if (lastSet == null) {
                lastSet = createBarDataSet();
                barData.addDataSet(lastSet);
            }
            //count = lastSet.getEntryCount();
            count = i;
            //barData.addEntry(new BarEntry(count, kLineDatas.get(i).high, kLineDatas.get(i).low, kLineDatas.get(i).open, kLineDatas.get(i).close, kLineDatas.get(i).volume), indexLast);
            barData.addEntry(barEntry, indexLast);
            //barData.addXValue(kLineBean.getDate());

            //barData.addEntry(mData.getBarEntries().get(mData.getBarEntries().size() - index), indexLast);
            // barData.addXValue(xVals);
        }

        if (lineData != null) {
            LineDataSet lineDataSet7 = (LineDataSet) lineData.getDataSetByIndex(0);//五日均线;
            LineDataSet lineDataSet10 = (LineDataSet) lineData.getDataSetByIndex(1);//十日均线;

            //lineData.addXValue(kLineBean.getDate());

            if (lineDataSet7 != null) {
                //mData.getMa5DataV().add(new Entry(VMAEntity.getLastMA(kLineDatas, 5), count));
                lineData.addEntry(mData.getMa7DataV().get(mData.getMa7DataV().size() - index), 0);
            }

            if (lineDataSet10 != null) {
                //mData.getMa10DataV().add(new Entry(VMAEntity.getLastMA(kLineDatas, 10), count));
                lineData.addEntry(mData.getMa10DataV().get(mData.getMa10DataV().size() - index), 1);
            }


        }


    }

    /**
     * 获取最后一个CandleDataSet的索引
     */
    private int getLastDataSetIndex(BarData barData) {
        int dataSetCount = barData.getDataSetCount();
        return dataSetCount > 0 ? (dataSetCount - 1) : 0;
    }

    /**
     * 获取最后一个CandleDataSet的索引
     */
    private int getLastDataSetIndex(CandleData candleData) {
        int dataSetCount = candleData.getDataSetCount();
        return dataSetCount > 0 ? (dataSetCount - 1) : 0;
    }

    private CandleDataSet createCandleDataSet() {
        CandleDataSet dataSet = new CandleDataSet(null, "DataSet 1");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setValueTextSize(12f);

        return dataSet;
    }

    private BarDataSet createBarDataSet() {
        BarDataSet dataSet = new BarDataSet(null, "DataSet 1");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setValueTextSize(12f);

        return dataSet;
    }

    //右上角添加初始化
    private void initCollection() {
        String string = CacheUtils.getInstance().getString(CACHE_CHOOSE);
        userOnlyKeys = GsonUtil.getInstance().toList(string, String.class);
        if (userOnlyKeys != null) {
            if (userOnlyKeys.contains(exchangeData.getOnlyKey())) {
                isAddView(true);
            } else {
                isAddView(false);
            }
        } else {
            userOnlyKeys = new ArrayList<>();
            isAddView(false);
        }
    }

    private void isAddView(boolean isAdd) {
        if (isAdd) {
            viewDelegate.viewHolder.tv_add.setTextColor(CommonUtils.getColor(R.color.color_F5A623));
            viewDelegate.viewHolder.tv_icon_add.setTextColor(CommonUtils.getColor(R.color.color_F5A623));
            viewDelegate.viewHolder.tv_icon_add.setText(CommonUtils.getString(R.string.ic_star));
        } else {
            viewDelegate.viewHolder.tv_add.setTextColor(CommonUtils.getColor(R.color.color_font2));
            viewDelegate.viewHolder.tv_icon_add.setTextColor(CommonUtils.getColor(R.color.color_font2));
            viewDelegate.viewHolder.tv_icon_add.setText(CommonUtils.getString(R.string.ic_Star));
        }
    }

    private void clickAdd() {
        //点击添加自选
        binder.singlesubs(exchangeData.getOnlyKey(), userOnlyKeys.contains(exchangeData.getOnlyKey()) ? "0" : "1", null);
        if (userOnlyKeys.contains(exchangeData.getOnlyKey())) {
            userOnlyKeys.remove(userOnlyKeys.indexOf(exchangeData.getOnlyKey()));
            isAddView(false);
        } else {
            userOnlyKeys.add(exchangeData.getOnlyKey());
            isAddView(true);
        }
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
                if (newkLineBeans != null) {
                    if (mData != null) {
                        klineValue = timeData.get(position);
                        UserSet.getinstance().setKTime(klineValue);
                        isChange = true;
                        timeIndex = 0;
                        //清空数据
                        cleanData();
                        //请求新数据
                        loadDao();
                        //addRequest(binder.getKlineByOnlyKey(exchangeData.getOnlyKey(), klineValue, "", 0x123, MarketDetailsXActivity.this));
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
                        if (mData != null) {
                            viewDelegate.setDetailsData(viewDelegate.selectPosition, mData);
                            selectType();
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
        switch (v.getId()) {
            case R.id.lin_global_market:
                //全球行情
                CoinMarketActivity.startAct(this, exchangeData.getSymbol());
                break;
            case R.id.lin_currency_data:
                //币种资料
                CoinDetailsActivity.startAct(this, exchangeData);
                break;
            case R.id.lin_simulation:
                //模拟交易
                ResultDialogEntity resultDialogEntity = new ResultDialogEntity();
                resultDialogEntity.setCode("0");
                EventBus.getDefault().post(resultDialogEntity);
                break;
            case R.id.lin_add:
                //添加自选
                clickAdd();
                break;
        }
    }

    public void selectType() {
        LineData lineData = mChartKline.getLineData();
        LineDataSet lineDataSetMA7 = (LineDataSet) lineData.getDataSetByIndex(0);//五日均线;
        LineDataSet lineDataSetMA30 = (LineDataSet) lineData.getDataSetByIndex(1);//十日均线;
        LineDataSet lineDataSetEMA7 = (LineDataSet) lineData.getDataSetByIndex(2);
        LineDataSet lineDataSetEMA30 = (LineDataSet) lineData.getDataSetByIndex(3);
        //        LineDataSet lineDataSetUB = (LineDataSet) lineData.getDataSetByIndex(4);//UP 上轨 UB
        //        LineDataSet lineDataSetBOLL = (LineDataSet) lineData.getDataSetByIndex(5);//MB 中轨 BOLL
        //        LineDataSet lineDataSetLB = (LineDataSet) lineData.getDataSetByIndex(6);//DN 下轨 LB
        boolean isMA = false;
        boolean isEMA = false;
        boolean isBOLL = false;
        if (klineTypeData.indexOf(UserSet.getinstance().getKType()) == 0) {
            //MA
            isMA = true;
        } else if (klineTypeData.indexOf(UserSet.getinstance().getKType()) == 1) {
            //EMA
            isEMA = true;
        }
        //        else if (klineTypeData.indexOf(UserSet.getinstance().getKType()) == 2) {
        //            //BOLL
        //            isBOLL = true;
        //        }
        else if (klineTypeData.indexOf(UserSet.getinstance().getKType()) == 2) {
            //均线
        }
        lineDataSetMA7.setVisible(isMA);
        lineDataSetMA30.setVisible(isMA);
        lineDataSetEMA7.setVisible(isEMA);
        lineDataSetEMA30.setVisible(isEMA);
        //        lineDataSetUB.setVisible(isBOLL);
        //        lineDataSetBOLL.setVisible(isBOLL);
        //        lineDataSetLB.setVisible(isBOLL);
        setOffset();

        mChartKline.setAutoScaleMinMaxEnabled(true);
        mChartVolume.setAutoScaleMinMaxEnabled(true);

        mChartKline.notifyDataSetChanged();
        mChartVolume.notifyDataSetChanged();

        mChartKline.invalidate();
        mChartVolume.invalidate();


    }
}
