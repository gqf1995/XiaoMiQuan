package com.xiaomiquan.mvp.delegate.group;

import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.circledialog.res.drawable.RadiuBg;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.fivefivelike.mybaselibrary.view.NoParentsTouchFramelayout;
import com.tablayout.CommonTabLayout;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.CoinDetail;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.utils.UserSet;
import com.xiaomiquan.widget.DropDownView;
import com.xiaomiquan.widget.JudgeNestedScrollView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import skin.support.widget.SkinCompatEditText;

/**
 * Created by Andy on 2018/1/25.
 */

public class GroupDealDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    private ArrayList<CustomTabEntity> mTabEntitiesTop = new ArrayList<>();

    String buyBalance = "";
    String sellBalance = "";
    public int selectType = 0;

    public void initTop(DefaultClickLinsener defaultClickLinsener) {
        viewHolder.tl_1.setIconVisible(false);
        mTabEntitiesTop.add(new TabEntity(CommonUtils.getString(R.string.str_buy), R.string.ic_Download, 0));
        mTabEntitiesTop.add(new TabEntity(CommonUtils.getString(R.string.str_sell), R.string.ic_Upload, 0));
        viewHolder.tl_1.setTabData(mTabEntitiesTop);
        viewHolder.nestedScrollView.setNoScollView(viewHolder.fl_currency);
        changeType(true);
        viewHolder.et_coin_search.setBackground(new RadiuBg(CommonUtils.getColor(R.color.base_mask), 1000, 1000, 1000, 1000));

        List<String> dataset2 = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_price_type));
        viewHolder.lin_choose.setDefaultClickLinsener(defaultClickLinsener).setDatas(dataset2, null);
    }

    public void onSelectLinsener(CoinDetail coinDetail, GroupItem groupItem) {
        if (coinDetail == null) {
            viewHolder.tv_buy_price.setText(CommonUtils.getString(R.string.str_now_no_data));
            viewHolder.tv_balance.setText(CommonUtils.getString(R.string.str_now_no_data));
            viewHolder.tv_sell_price.setText(CommonUtils.getString(R.string.str_now_no_data));
            viewHolder.tv_coin_type.setText(CommonUtils.getString(R.string.str_now_no_data));
            viewHolder.tv_unit.setText(CommonUtils.getString(R.string.str_now_no_data));
            return;
        }
        viewHolder.tv_unit.setText(coinDetail.getSymbol());
        List<String> strings = BigUIUtil.getinstance().rateUSDAndCNY(coinDetail.getPriceUsd(), coinDetail.getSymbol(), UserSet.getinstance().getUSDUnit());

        viewHolder.tv_sell_price.setText(strings.get(0));
        viewHolder.tv_buy_price.setText(strings.get(1));


        if (viewHolder.tl_1.getCurrentTab() == 0) {
            //买
            buyBalance = CommonUtils.getString(R.string.str_tv_balance) + groupItem.getBalance() + "USD";
            viewHolder.tv_balance.setText(buyBalance);
        } else {
            //卖
            sellBalance = CommonUtils.getString(R.string.str_tv_balance_amount) + coinDetail.getCount();
            viewHolder.tv_balance.setText(sellBalance);
        }
        viewHolder.tv_coin_type.setText(coinDetail.getSymbol());
    }

    public void changeType(boolean isBuy) {
        viewHolder.tv_price_label.setText(CommonUtils.getString(isBuy ? R.string.str_tv_buy_price : R.string.str_tv_sell_price));
        viewHolder.tv_num_label.setText(CommonUtils.getString(isBuy ? R.string.str_tv_buy_num : R.string.str_tv_sell_num));
        viewHolder.et_coin_search.setVisibility(isBuy ? View.VISIBLE : View.INVISIBLE);
        if (isBuy) {
            if (!TextUtils.isEmpty(buyBalance)) {
                viewHolder.tv_balance.setText(buyBalance);
            }
        } else {
            if (!TextUtils.isEmpty(sellBalance)) {
                viewHolder.tv_balance.setText(sellBalance);
            }
        }
    }

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.nestedScrollView.setTabAndPager(viewHolder.lin_table, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_80px), viewHolder.vp_sliding, false);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group_deal;
    }


    public static class ViewHolder {
        public View rootView;
        public IconFontTextview tv_left;
        public TextView tv_title;
        public IconFontTextview tv_right;
        public CommonTabLayout tl_1;
        public TextView tv_input_label1;
        public SkinCompatEditText et_coin_search;
        public NoParentsTouchFramelayout fl_currency;
        public TextView tv_coin_type;
        public TextView tv_sell_price;
        public LinearLayout lin_sell;
        public TextView tv_buy_price;
        public TextView tv_balance;
        public TextView tv_price_label;
        public EditText et_sell_price;
        public DropDownView lin_choose;
        public TextView tv_num_label;
        public EditText et_sell_num;
        public TextView tv_unit;
        public TextView tv_commit;
        public CommonTabLayout tl_2;
        public LinearLayout lin_table;
        public ViewPager vp_sliding;
        public JudgeNestedScrollView nestedScrollView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_left = (IconFontTextview) rootView.findViewById(R.id.tv_left);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_right = (IconFontTextview) rootView.findViewById(R.id.tv_right);
            this.tl_1 = (CommonTabLayout) rootView.findViewById(R.id.tl_1);
            this.tv_input_label1 = (TextView) rootView.findViewById(R.id.tv_input_label1);
            this.et_coin_search = (SkinCompatEditText) rootView.findViewById(R.id.et_coin_search);
            this.fl_currency = (NoParentsTouchFramelayout) rootView.findViewById(R.id.fl_currency);
            this.tv_coin_type = (TextView) rootView.findViewById(R.id.tv_coin_type);
            this.tv_sell_price = (TextView) rootView.findViewById(R.id.tv_sell_price);
            this.lin_sell = (LinearLayout) rootView.findViewById(R.id.lin_sell);
            this.tv_buy_price = (TextView) rootView.findViewById(R.id.tv_buy_price);
            this.tv_balance = (TextView) rootView.findViewById(R.id.tv_balance);
            this.tv_price_label = (TextView) rootView.findViewById(R.id.tv_price_label);
            this.et_sell_price = (EditText) rootView.findViewById(R.id.et_sell_price);
            this.lin_choose = (DropDownView) rootView.findViewById(R.id.lin_choose);
            this.tv_num_label = (TextView) rootView.findViewById(R.id.tv_num_label);
            this.et_sell_num = (EditText) rootView.findViewById(R.id.et_sell_num);
            this.tv_unit = (TextView) rootView.findViewById(R.id.tv_unit);
            this.tv_commit = (TextView) rootView.findViewById(R.id.tv_commit);
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.lin_table = (LinearLayout) rootView.findViewById(R.id.lin_table);
            this.vp_sliding = (ViewPager) rootView.findViewById(R.id.vp_sliding);
            this.nestedScrollView = (JudgeNestedScrollView) rootView.findViewById(R.id.nestedScrollView);
        }

    }
}



