package com.xiaomiquan.mvp.delegate;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.fivefivelike.mybaselibrary.base.BaseApp;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.entity.ResultDialogEntity;
import com.fivefivelike.mybaselibrary.utils.ActUtil;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.tablayout.CommonTabLayout;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.EarningsMovements;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.entity.bean.group.TeamInfo;
import com.xiaomiquan.mvp.activity.group.CreatGroupActivity;
import com.xiaomiquan.mvp.activity.group.CreatTeamActivity;
import com.xiaomiquan.mvp.activity.main.WebActivityActivity;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.widget.JudgeNestedScrollView;
import com.xiaomiquan.widget.chart.MyLeftRateMarkerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.xiaomiquan.base.AppConst.rulesUrl;
import static com.xiaomiquan.utils.TimeUtils.DEFAULT_FORMAT;

public class CombinationDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.nestedScrollView.setTabAndPager(viewHolder.lin_table, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_110px), viewHolder.viewpager, false);
        viewHolder.linechart.setNoDataText(CommonUtils.getString(R.string.str_chart_nodata));
    }

    public void initRate(String data) {
        String monthRate1 = GsonUtil.getInstance().getValue(data, "monthRate");
        String weekRate1 = GsonUtil.getInstance().getValue(data, "weekRate");
        String yesterdayRate1 = GsonUtil.getInstance().getValue(data, "yesterdayRate");
        String totalRate1 = GsonUtil.getInstance().getValue(data, "totalRate");
        double monthRate, totalRate, weekRate, yesterdayRate;
        if (!TextUtils.isEmpty(monthRate1)) {
            monthRate = Double.parseDouble(monthRate1);
        } else {
            monthRate = 0;
        }
        if (!TextUtils.isEmpty(totalRate1)) {
            totalRate = Double.parseDouble(totalRate1);
        } else {
            totalRate = 0;
        }
        if (!TextUtils.isEmpty(weekRate1)) {
            weekRate = Double.parseDouble(weekRate1);
        } else {
            weekRate = 0;
        }
        if (!TextUtils.isEmpty(yesterdayRate1)) {
            yesterdayRate = Double.parseDouble(yesterdayRate1);
        } else {
            yesterdayRate = 0;
        }
        BigUIUtil.getinstance().rateTextView(monthRate, viewHolder.tv_month_earnings);
        BigUIUtil.getinstance().rateTextView(totalRate, viewHolder.tv_cumulative_earnings);
        BigUIUtil.getinstance().rateTextView(weekRate, viewHolder.tv_week_earnings);
        BigUIUtil.getinstance().rateTextView(yesterdayRate, viewHolder.tv_yesterday_earnings);

    }

    public void initData(GroupItem groupItem) {
        GlideUtils.loadImage(groupItem.getAvatar(), viewHolder.ic_pic);
        viewHolder.tv_name.setText(groupItem.getName());
        viewHolder.tv_focus_on_num.setText(groupItem.getAttentionCount() + CommonUtils.getString(R.string.str_people) + CommonUtils.getString(R.string.str_focuse));

        viewHolder.tv_create_time.setText(TimeUtils.millis2String(groupItem.getCreateTime(), DEFAULT_FORMAT));
        if (TextUtils.isEmpty(groupItem.getBrief())) {
            viewHolder.tv_introduce.setText(CommonUtils.getString(R.string.str_now_no_data));
        } else {
            viewHolder.tv_introduce.setText(groupItem.getBrief());
        }
        viewHolder.tv_toast.setText("该账户为永久账户\n如需参加大赛请点击创建大赛账户");
        viewHolder.tv_create.setText("创建大赛账户");
        viewHolder.tv_game.setText("炒币大赛规则");
        viewHolder.tv_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatTeamActivity.startAct((FragmentActivity) viewHolder.rootView.getContext(), 0x123);
            }
        });
        viewHolder.tv_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivityActivity.startAct((FragmentActivity) viewHolder.rootView.getContext(), rulesUrl);
            }
        });
    }

    protected XAxis xAxisKline;
    //Y轴左侧的线
    protected YAxis axisLeftKline;
    //Y轴右侧的线
    protected YAxis axisRightKline;

    public void initEarningsMovements(EarningsMovements earningsMovements) {
        String startTime;
        String endTime;
        startTime = TimeUtils.millis2String(earningsMovements.getStartTime(), com.xiaomiquan.utils.TimeUtils.DEFAULT_FORMAT);
        endTime = TimeUtils.millis2String(earningsMovements.getEndTime(), com.xiaomiquan.utils.TimeUtils.DEFAULT_FORMAT);
        viewHolder.tv_chart_time.setText(startTime + "-" + endTime);
        viewHolder.tv_start_time.setText(startTime);
        viewHolder.tv_end_time.setText(endTime);
        LineChart mChartKline = viewHolder.linechart;
        mChartKline.setScaleEnabled(false);//启用图表缩放事件
        mChartKline.setDrawBorders(false);//是否绘制边线
        mChartKline.setBorderWidth(1);//边线宽度，单位dp
        mChartKline.setDragEnabled(false);//启用图表拖拽事件
        mChartKline.setScaleYEnabled(false);//启用Y轴上的缩放
        mChartKline.setBorderColor(CommonUtils.getColor(R.color.border_color));//边线颜色
        mChartKline.setDescription("");//右下角对图表的描述信息
        mChartKline.setMinOffset(0f);
        mChartKline.setExtraOffsets(20f, 10f, 30f, 0f);

        Legend lineChartLegend = mChartKline.getLegend();
        lineChartLegend.setEnabled(false);//是否绘制 Legend 图例
        lineChartLegend.setForm(Legend.LegendForm.CIRCLE);

        xAxisKline = mChartKline.getXAxis();
        xAxisKline.setDrawLabels(true); //是否显示X坐标轴上的刻度，默认是true
        xAxisKline.setDrawGridLines(false);//是否显示X坐标轴上的刻度竖线，默认是true
        xAxisKline.setDrawAxisLine(false); //是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        xAxisKline.enableGridDashedLine(10f, 10f, 0f);//虚线表示X轴上的刻度竖线(float lineLength, float spaceLength, float phase)三个参数，1.线长，2.虚线间距，3.虚线开始坐标
        xAxisKline.setTextColor(CommonUtils.getColor(R.color.transparent));//设置字的颜色
        xAxisKline.setPosition(XAxis.XAxisPosition.BOTTOM);//设置值显示在什么位置
        xAxisKline.setAvoidFirstLastClipping(false);//设置首尾的值是否自动调整，避免被遮挡

        axisLeftKline = mChartKline.getAxisLeft();
        axisLeftKline.setDrawGridLines(false);
        axisLeftKline.setDrawAxisLine(false);
        axisLeftKline.setDrawZeroLine(false);
        axisLeftKline.setDrawLabels(true);
        axisLeftKline.enableGridDashedLine(10f, 10f, 0f);
        axisLeftKline.setTextColor(CommonUtils.getColor(R.color.color_font4));
        axisLeftKline.setTextSize(10);
        //        axisLeftKline.setGridColor(CommonUtils.getColor(R.color.minute_grayLine));
        axisLeftKline.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisLeftKline.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布


        axisRightKline = mChartKline.getAxisRight();
        axisRightKline.setDrawLabels(false);
        axisRightKline.setDrawGridLines(false);
        axisRightKline.setDrawAxisLine(false);
        axisRightKline.setLabelCount(4, false); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布


        mChartKline.setDragDecelerationEnabled(true);
        mChartKline.setDragDecelerationFrictionCoef(0.2f);
        ArrayList<Entry> lineEntries = new ArrayList<>();
        List<String> time = new ArrayList<>();
        for (int i = 0; i < earningsMovements.getRates().size(); i++) {
            lineEntries.add(new Entry(earningsMovements.getRates().get(i), i));
            time.add("");
        }
        LineDataSet lineDataSetMA7 = new LineDataSet(lineEntries, "ma");
        lineDataSetMA7.setDrawValues(false);
        lineDataSetMA7.setDrawCircles(false);
        lineDataSetMA7.setDrawCircleHole(false);
        lineDataSetMA7.setColor(CommonUtils.getColor(R.color.color_blue));
        LineData lineData = new LineData(time, lineDataSetMA7);
        MyLeftRateMarkerView leftMarkerView = new MyLeftRateMarkerView(viewHolder.rootView.getContext(), R.layout.mymarkerview);
        mChartKline.setMarkerView(leftMarkerView);
        mChartKline.setData(lineData);
        //setHandler(mChartKline);

        mChartKline.moveViewToX(lineEntries.size() - 1);
        mChartKline.setMaxVisibleValueCount(earningsMovements.getRates().size());
        mChartKline.invalidate();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_combination;
    }


    public void initTeaminfo(TeamInfo teamInfo){
        double monthRate, totalRate, weekRate, yesterdayRate;
        if (!TextUtils.isEmpty(teamInfo.getMonthProfit())) {
            monthRate = Double.parseDouble(teamInfo.getMonthProfit());
        } else {
            monthRate = 0;
        }
        if (!TextUtils.isEmpty(teamInfo.getTotalProfit())) {
            totalRate = Double.parseDouble(teamInfo.getTotalProfit());
        } else {
            totalRate = 0;
        }
        if (!TextUtils.isEmpty(teamInfo.getWeekProfit())) {
            weekRate = Double.parseDouble(teamInfo.getWeekProfit());
        } else {
            weekRate = 0;
        }
        if (!TextUtils.isEmpty(teamInfo.getYesterdayProfit())) {
            yesterdayRate = Double.parseDouble(teamInfo.getYesterdayProfit());
        } else {
            yesterdayRate = 0;
        }
        BigUIUtil.getinstance().rateTextView(monthRate, viewHolder.tv_month_earnings);
        BigUIUtil.getinstance().rateTextView(totalRate, viewHolder.tv_cumulative_earnings);
        BigUIUtil.getinstance().rateTextView(weekRate, viewHolder.tv_week_earnings);
        BigUIUtil.getinstance().rateTextView(yesterdayRate, viewHolder.tv_yesterday_earnings);


        EarningsMovements earningsMovements=new EarningsMovements();
        earningsMovements.setEndTime(teamInfo.getEndTime());
        earningsMovements.setStartTime(teamInfo.getStartTime());
        earningsMovements.setRates(teamInfo.getRates());
        initEarningsMovements(earningsMovements);


        GlideUtils.loadImage(teamInfo.getAvatar(), viewHolder.ic_pic);
        viewHolder.tv_name.setText(teamInfo.getName());
        viewHolder.tv_focus_on_num.setText(teamInfo.getAttentionCount() + CommonUtils.getString(R.string.str_people) + CommonUtils.getString(R.string.str_focuse));

        viewHolder.tv_create_time.setText(TimeUtils.millis2String(teamInfo.getCreateTime(), DEFAULT_FORMAT));
        if (TextUtils.isEmpty(teamInfo.getBrief())) {
            viewHolder.tv_introduce.setText(CommonUtils.getString(R.string.str_now_no_data));
        } else {
            viewHolder.tv_introduce.setText(teamInfo.getBrief());
        }
        viewHolder.tv_toast.setText("该账户为大赛账户,将在比赛结束后关闭\n比赛结束时间为: " + TimeUtils.millis2String(teamInfo.getGameEndTime(), com.xiaomiquan.utils.TimeUtils.DEFAULT_FORMAT));
        viewHolder.tv_create.setText("创建永久账户");
        viewHolder.tv_game.setText("炒币大赛规则");
        viewHolder.tv_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatGroupActivity.startAct((FragmentActivity) viewHolder.rootView.getContext(), 0x123);
            }
        });
        viewHolder.tv_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((FragmentActivity) viewHolder.rootView.getContext(), BaseApp.getInstance().getLoginActivityClass());
                ActUtil.getInstance().killAllActivity((FragmentActivity) viewHolder.rootView.getContext());
                ((FragmentActivity) viewHolder.rootView.getContext()).startActivity(intent);
                ((FragmentActivity) viewHolder.rootView.getContext()).finish();
                ResultDialogEntity resultDialogEntity = new ResultDialogEntity();
                resultDialogEntity.setCode("1");
                EventBus.getDefault().post(resultDialogEntity);
            }
        });
    }


    public static class ViewHolder {
        public View rootView;
        public CircleImageView ic_pic;
        public TextView tv_name;
        public TextView tv_focus_on_num;
        public TextView tv_label;
        public TextView tv_create_time;
        public TextView tv_introduce;
        public TextView tv_toast;
        public TextView tv_create;
        public TextView tv_game;
        public TextView tv_today_earnings;
        public TextView tv_daily_operation;
        public TextView tv_cumulative_earnings;
        public TextView tv_yesterday_earnings;
        public TextView tv_week_earnings;
        public TextView tv_month_earnings;
        public TextView tv_chart_time;
        public LineChart linechart;
        public TextView tv_start_time;
        public TextView tv_end_time;
        public LinearLayout lin_top;
        public CommonTabLayout tl_2;
        public LinearLayout lin_table;
        public ViewPager viewpager;
        public JudgeNestedScrollView nestedScrollView;
        public SwipeRefreshLayout swipeRefreshLayout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ic_pic = (CircleImageView) rootView.findViewById(R.id.ic_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_focus_on_num = (TextView) rootView.findViewById(R.id.tv_focus_on_num);
            this.tv_label = (TextView) rootView.findViewById(R.id.tv_label);
            this.tv_create_time = (TextView) rootView.findViewById(R.id.tv_create_time);
            this.tv_introduce = (TextView) rootView.findViewById(R.id.tv_introduce);
            this.tv_toast = (TextView) rootView.findViewById(R.id.tv_toast);
            this.tv_create = (TextView) rootView.findViewById(R.id.tv_create);
            this.tv_game = (TextView) rootView.findViewById(R.id.tv_game);
            this.tv_today_earnings = (TextView) rootView.findViewById(R.id.tv_today_earnings);
            this.tv_daily_operation = (TextView) rootView.findViewById(R.id.tv_daily_operation);
            this.tv_cumulative_earnings = (TextView) rootView.findViewById(R.id.tv_cumulative_earnings);
            this.tv_yesterday_earnings = (TextView) rootView.findViewById(R.id.tv_yesterday_earnings);
            this.tv_week_earnings = (TextView) rootView.findViewById(R.id.tv_week_earnings);
            this.tv_month_earnings = (TextView) rootView.findViewById(R.id.tv_month_earnings);
            this.tv_chart_time = (TextView) rootView.findViewById(R.id.tv_chart_time);
            this.linechart = (LineChart) rootView.findViewById(R.id.linechart);
            this.tv_start_time = (TextView) rootView.findViewById(R.id.tv_start_time);
            this.tv_end_time = (TextView) rootView.findViewById(R.id.tv_end_time);
            this.lin_top = (LinearLayout) rootView.findViewById(R.id.lin_top);
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.lin_table = (LinearLayout) rootView.findViewById(R.id.lin_table);
            this.viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
            this.nestedScrollView = (JudgeNestedScrollView) rootView.findViewById(R.id.nestedScrollView);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        }

    }
}