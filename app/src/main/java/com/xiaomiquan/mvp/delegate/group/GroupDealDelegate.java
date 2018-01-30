package com.xiaomiquan.mvp.delegate.group;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.tablayout.CommonTabLayout;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;
import com.xiaomiquan.widget.DropDownView;
import com.xiaomiquan.widget.JudgeNestedScrollView;

import java.util.ArrayList;

/**
 * Created by Andy on 2018/1/25.
 */

public class GroupDealDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    private ArrayList<CustomTabEntity> mTabEntitiesTop = new ArrayList<>();

    private void initTop() {
        viewHolder.tl_1.setIconVisible(false);
        mTabEntitiesTop.add(new TabEntity(CommonUtils.getString(R.string.str_buy), R.string.ic_Download, 0));
        mTabEntitiesTop.add(new TabEntity(CommonUtils.getString(R.string.str_sell), R.string.ic_Upload, 0));
        viewHolder.tl_1.setTabData(mTabEntitiesTop);
        viewHolder.tl_1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                changeType(position == 0);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewHolder.nestedScrollView.setNoScollView(viewHolder.rv_currency);
        changeType(true);
    }

    private void changeType(boolean isBuy) {
        viewHolder.tv_price_label.setText(CommonUtils.getString(isBuy ? R.string.str_tv_buy_price : R.string.str_tv_sell_price));
        viewHolder.tv_num_label.setText(CommonUtils.getString(isBuy ? R.string.str_tv_buy_num : R.string.str_tv_sell_num));
    }

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.nestedScrollView.setTabAndPager(viewHolder.lin_table, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_80px), viewHolder.vp_sliding, true);
        initTop();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group_deal;
    }


    public static class ViewHolder {
        public View rootView;
        public CommonTabLayout tl_1;
        public TextView tv_input_label1;
        public RecyclerView rv_currency;
        public TextView tv_sell_price;
        public LinearLayout lin_sell;
        public TextView tv_buy_price;
        public TextView tv_balance;
        public TextView tv_price_label;
        public EditText et_sell_price;
        public DropDownView lin_choose;
        public TextView tv_num_label;
        public EditText et_sell_num;
        public TextView tv_commit;
        public CommonTabLayout tl_2;
        public LinearLayout lin_table;
        public ViewPager vp_sliding;
        public JudgeNestedScrollView nestedScrollView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tl_1 = (CommonTabLayout) rootView.findViewById(R.id.tl_1);
            this.tv_input_label1 = (TextView) rootView.findViewById(R.id.tv_input_label1);
            this.rv_currency = (RecyclerView) rootView.findViewById(R.id.rv_currency);
            this.tv_sell_price = (TextView) rootView.findViewById(R.id.tv_sell_price);
            this.lin_sell = (LinearLayout) rootView.findViewById(R.id.lin_sell);
            this.tv_buy_price = (TextView) rootView.findViewById(R.id.tv_buy_price);
            this.tv_balance = (TextView) rootView.findViewById(R.id.tv_balance);
            this.tv_price_label = (TextView) rootView.findViewById(R.id.tv_price_label);
            this.et_sell_price = (EditText) rootView.findViewById(R.id.et_sell_price);
            this.lin_choose = (DropDownView) rootView.findViewById(R.id.lin_choose);
            this.tv_num_label = (TextView) rootView.findViewById(R.id.tv_num_label);
            this.et_sell_num = (EditText) rootView.findViewById(R.id.et_sell_num);
            this.tv_commit = (TextView) rootView.findViewById(R.id.tv_commit);
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.lin_table = (LinearLayout) rootView.findViewById(R.id.lin_table);
            this.vp_sliding = (ViewPager) rootView.findViewById(R.id.vp_sliding);
            this.nestedScrollView = (JudgeNestedScrollView) rootView.findViewById(R.id.nestedScrollView);
        }

    }
}


