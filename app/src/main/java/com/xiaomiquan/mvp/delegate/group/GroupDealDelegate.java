package com.xiaomiquan.mvp.delegate.group;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.materialedittext.MaterialEditText;
import com.tablayout.CommonTabLayout;
import com.xiaomiquan.R;

/**
 * Created by Andy on 2018/1/25.
 */

public class GroupDealDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group_deal;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView tv_input_label1;
        public RecyclerView rv_currency;
        public TextView tv_sell_price;
        public LinearLayout lin_sell;
        public TextView tv_buy_price;
        public TextView tv_balance;
        public MaterialEditText et_sell_price;
        public MaterialEditText et_sell_num;
        public TextView tv_commit;
        public CommonTabLayout tl_2;
        public ViewPager vp_sliding;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_input_label1 = (TextView) rootView.findViewById(R.id.tv_input_label1);
            this.rv_currency = (RecyclerView) rootView.findViewById(R.id.rv_currency);
            this.tv_sell_price = (TextView) rootView.findViewById(R.id.tv_sell_price);
            this.lin_sell = (LinearLayout) rootView.findViewById(R.id.lin_sell);
            this.tv_buy_price = (TextView) rootView.findViewById(R.id.tv_buy_price);
            this.tv_balance = (TextView) rootView.findViewById(R.id.tv_balance);
            this.et_sell_price = (MaterialEditText) rootView.findViewById(R.id.et_sell_price);
            this.et_sell_num = (MaterialEditText) rootView.findViewById(R.id.et_sell_num);
            this.tv_commit = (TextView) rootView.findViewById(R.id.tv_commit);
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.vp_sliding = (ViewPager) rootView.findViewById(R.id.vp_sliding);
        }

    }
}
