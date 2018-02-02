package com.xiaomiquan.mvp.delegate;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.tablayout.CommonTabLayout;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.xiaomiquan.widget.JudgeNestedScrollView;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.xiaomiquan.utils.TimeUtils.DEFAULT_FORMAT;

public class CombinationDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.nestedScrollView.setTabAndPager(viewHolder.lin_table, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_110px), viewHolder.viewpager,false);
    }


    public void initData(GroupItem groupItem) {
        GlideUtils.loadImage(groupItem.getAvatar(), viewHolder.ic_pic);
        viewHolder.tv_name.setText(groupItem.getName());
        viewHolder.tv_focus_on_num.setText(groupItem.getAttentionCount());
        viewHolder.tv_label.setText(groupItem.getType());
        viewHolder.tv_create_time.setText(com.blankj.utilcode.util.TimeUtils.millis2String(groupItem.getCreateTime(), DEFAULT_FORMAT));
        viewHolder.tv_introduce.setText(groupItem.getBrief());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_combination;
    }


    public static class ViewHolder {
        public View rootView;
        public CircleImageView ic_pic;
        public TextView tv_name;
        public TextView tv_focus_on_num;
        public TextView tv_label;
        public TextView tv_create_time;
        public TextView tv_introduce;
        public TextView tv_today_earnings;
        public TextView tv_daily_operation;
        public TextView tv_cumulative_earnings;
        public TextView tv_yesterday_earnings;
        public TextView tv_week_earnings;
        public TextView tv_month_earnings;
        public TextView tv_chart_time;
        public LineChart linechart;
        public LinearLayout lin_top;
        public CommonTabLayout tl_2;
        public LinearLayout lin_table;
        public ViewPager viewpager;
        public JudgeNestedScrollView nestedScrollView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ic_pic = (CircleImageView) rootView.findViewById(R.id.ic_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_focus_on_num = (TextView) rootView.findViewById(R.id.tv_focus_on_num);
            this.tv_label = (TextView) rootView.findViewById(R.id.tv_label);
            this.tv_create_time = (TextView) rootView.findViewById(R.id.tv_create_time);
            this.tv_introduce = (TextView) rootView.findViewById(R.id.tv_introduce);
            this.tv_today_earnings = (TextView) rootView.findViewById(R.id.tv_today_earnings);
            this.tv_daily_operation = (TextView) rootView.findViewById(R.id.tv_daily_operation);
            this.tv_cumulative_earnings = (TextView) rootView.findViewById(R.id.tv_cumulative_earnings);
            this.tv_yesterday_earnings = (TextView) rootView.findViewById(R.id.tv_yesterday_earnings);
            this.tv_week_earnings = (TextView) rootView.findViewById(R.id.tv_week_earnings);
            this.tv_month_earnings = (TextView) rootView.findViewById(R.id.tv_month_earnings);
            this.tv_chart_time = (TextView) rootView.findViewById(R.id.tv_chart_time);
            this.linechart = (LineChart) rootView.findViewById(R.id.linechart);
            this.lin_top = (LinearLayout) rootView.findViewById(R.id.lin_top);
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.lin_table = (LinearLayout) rootView.findViewById(R.id.lin_table);
            this.viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
            this.nestedScrollView = (JudgeNestedScrollView) rootView.findViewById(R.id.nestedScrollView);
        }

    }
}