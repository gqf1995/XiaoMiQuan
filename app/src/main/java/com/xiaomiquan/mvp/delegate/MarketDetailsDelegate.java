package com.xiaomiquan.mvp.delegate;

import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.tablayout.CommonTabLayout;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;
import com.xiaomiquan.widget.MinutesTabView;
import com.xiaomiquan.widget.chart.KCombinedChart;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MarketDetailsDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    ArrayList<Fragment> fragments;
    String[] mTitles;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    LinearLayout linearLayout;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        initCommonTabLayout();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_market_details;
    }

    private void initCommonTabLayout() {
        if (mTitles == null && fragments == null) {
            fragments = new ArrayList<>();
            mTitles = CommonUtils.getStringArray(R.array.sa_select_time);
        }

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }

        viewHolder.tl_2.setmIndicatorId(R.drawable.shape_blue_maxradiu);
        viewHolder.tl_2.setTabData(mTabEntities);
        viewHolder.tl_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewHolder.view_time_tab.setDefault(linearLayout.getChildAt(4));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        linearLayout = viewHolder.tl_2.getmTabsContainer();
        linearLayout.getChildAt(4).setEnabled(false);
        linearLayout.getChildAt(4).setVisibility(View.INVISIBLE);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setPadding(0, 0, 0, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_5px));
        viewHolder.view_time_tab.setDefault(linearLayout.getChildAt(4));
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            TextView title = linearLayout.getChildAt(i).findViewById(R.id.tv_tab_title);
            title.setGravity(Gravity.CENTER);
            title.setIncludeFontPadding(false);
            title.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) title.getLayoutParams();
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.setMargins(0, 0, 0, 0);
            title.setLayoutParams(layoutParams);
        }
        viewHolder.view_time_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.tl_2.setCurrentTab(4);
                viewHolder.view_time_tab.setDefault(linearLayout.getChildAt(4));
            }
        });

    }


    public static class ViewHolder {
        public View rootView;
        public IconFontTextview tv_left;
        public TextView tv_title;
        public IconFontTextview tv_right;
        public CircleImageView iv_pic;
        public CommonTabLayout tl_2;
        public MinutesTabView view_time_tab;
        public KCombinedChart combinedchart;
        public KCombinedChart barchart;
        public LinearLayout lin_information;
        public LinearLayout lin_advance_warning;
        public LinearLayout lin_currency_data;
        public LinearLayout lin_global_market;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_left = (IconFontTextview) rootView.findViewById(R.id.tv_left);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_right = (IconFontTextview) rootView.findViewById(R.id.tv_right);
            this.iv_pic = (CircleImageView) rootView.findViewById(R.id.iv_pic);
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.view_time_tab = (MinutesTabView) rootView.findViewById(R.id.view_time_tab);
            this.combinedchart = (KCombinedChart) rootView.findViewById(R.id.combinedchart);
            this.barchart = (KCombinedChart) rootView.findViewById(R.id.barchart);
            this.lin_information = (LinearLayout) rootView.findViewById(R.id.lin_information);
            this.lin_advance_warning = (LinearLayout) rootView.findViewById(R.id.lin_advance_warning);
            this.lin_currency_data = (LinearLayout) rootView.findViewById(R.id.lin_currency_data);
            this.lin_global_market = (LinearLayout) rootView.findViewById(R.id.lin_global_market);
        }

    }
}